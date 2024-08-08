/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.yadickson.autodblq.db.table.property.DataBaseTableProperty;
import com.github.yadickson.autodblq.db.table.property.DataTablePropertyManager;
import com.github.yadickson.autodblq.db.table.property.model.TablePropertyType;
import com.github.yadickson.autodblq.util.StringToLowerCaseUtil;
import com.github.yadickson.autodblq.util.StringToSnakeCaseUtil;
import com.github.yadickson.autodblq.util.StringTrimUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.columns.DataBaseTableColumnsWrapper;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseDataTableReaderIterator {

    private static final Logger LOGGER = Logger.getLogger(DataBaseDataTableReaderIterator.class);

    private final DataTablePropertyManager dataTablePropertyManager;
    private final DataBaseDataTableBlockQueryFactory dataBaseDataTableBlockQueryFactory;
    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringToLowerCaseUtil stringToLowerCaseUtil;
    private final StringTrimUtil stringTrimUtil;
    private final DriverConnection driverConnection;
    private final TableBase table;

    private Long pageCount;
    private String sqlQuery;
    private List<TableBase> current;

    private static final Long BLOCK = 20L;

    public DataBaseDataTableReaderIterator(
            final DataTablePropertyManager dataTablePropertyManager,
            final DataBaseDataTableBlockQueryFactory dataBaseDataTableBlockQueryFactory,
            final StringToSnakeCaseUtil stringToSnakeCaseUtil,
            final StringToLowerCaseUtil stringToLowerCaseUtil,
            final StringTrimUtil stringTrimUtil,
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        this.dataTablePropertyManager = dataTablePropertyManager;
        this.dataBaseDataTableBlockQueryFactory = dataBaseDataTableBlockQueryFactory;
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringToLowerCaseUtil = stringToLowerCaseUtil;
        this.stringTrimUtil = stringTrimUtil;
        this.driverConnection = driverConnection;
        this.table = table;
        this.pageCount = 0L;
    }

    public boolean nextBlock() {

        try {

            current = Collections.EMPTY_LIST;
            findSqlQuery(driverConnection, table, pageCount++);
            processSqlQuery(driverConnection);

        } catch (RuntimeException ex) {
            throw new DataBaseDataTableReaderIteratorException(ex);
        }

        return !current.isEmpty();
    }

    public List<TableBase> getBlock() {
        return current;
    }

    private void findSqlQuery(final DriverConnection driverConnection, final TableBase table, final Long pageCount) {
        final Driver driver = driverConnection.getDriver();
        final DataBaseDataTableBlockQuery query = dataBaseDataTableBlockQueryFactory.apply(driver);
        sqlQuery = query.get((DataBaseTableColumnsWrapper) table, pageCount, BLOCK);
        LOGGER.debug("[DataBaseDataTableReaderIterator] SQL: " + sqlQuery);
    }

    public void processSqlQuery(final DriverConnection driverConnection) {

        if (StringUtils.isEmpty(sqlQuery)) {
            return;
        }

        preparedStatement(driverConnection);
    }

    private void preparedStatement(final DriverConnection driverConnection) {
        Connection connection = driverConnection.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            executeSqlQuery(statement);
        } catch (SQLException | RuntimeException ex) {
            throw new DataBaseDataTableReaderIteratorException(ex);
        }
    }

    private void executeSqlQuery(final PreparedStatement statement) {

        try (ResultSet resultSet = statement.executeQuery()) {
            processResultSet(resultSet);
        } catch (SQLException | RuntimeException ex) {
            throw new DataBaseDataTableReaderIteratorException(ex);
        }

    }

    private void processResultSet(final ResultSet resultSet) throws SQLException {

        List<TableBase> tables = new ArrayList<>();

        ResultSetMetaData metadata = resultSet.getMetaData();
        int columnCount = metadata.getColumnCount();

        for (int j = 0; j < columnCount; j++) {
            String columnName = metadata.getColumnName(j + 1);
            String columnType = metadata.getColumnTypeName(j + 1);
            LOGGER.debug("[DataBaseDataTableReaderIterator] Column Name: " + columnName);
            LOGGER.debug("[DataBaseDataTableReaderIterator] Column Type: " + columnType);
        }

        while (resultSet.next()) {
            List<DataBaseTableProperty> rows = new ArrayList<>(columnCount);

            for (int j = 0; j < columnCount; j++) {

                String realColumnName = stringTrimUtil.apply(metadata.getColumnName(j + 1));
                String newColumnName = stringToSnakeCaseUtil.apply(realColumnName);
                String columnType = stringToLowerCaseUtil.apply(metadata.getColumnTypeName(j + 1));
                String columnValue = StringUtils.containsIgnoreCase(columnType, "bool") ? Boolean.toString(resultSet.getBoolean(j + 1)) : resultSet.getString(j + 1);

                DataBaseTableProperty column = new DataBaseTableProperty(realColumnName, realColumnName, newColumnName).setDefaultType(columnType).setDefaultValue(columnValue);
                TablePropertyType response = dataTablePropertyManager.process(column);

                if (response != null)
                    column.setPropertyType(response.getName());

                rows.add(column);
            }

            tables.add(new DataBaseTableColumnsWrapper(table, rows));
        }

        current = tables;
    }

}
