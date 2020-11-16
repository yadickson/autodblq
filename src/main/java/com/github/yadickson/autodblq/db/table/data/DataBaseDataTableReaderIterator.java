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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.Parameters;
import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.definitions.model.TableDefinitionWrapper;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseDataTableReaderIterator {

    private static final Logger LOGGER = Logger.getLogger(DataBaseDataTableReaderIterator.class);

    private final DataBaseDataTableBlockQueryFactory dataBaseDataTableBlockQueryFactory;
    private final DriverConnection driverConnection;
    private final TableBase table;
    private final String separator;

    private Long pageCount;
    private String sqlQuery;
    private List<String> current;

    private static final Long BLOCK = 20L;

    public DataBaseDataTableReaderIterator(
            final DataBaseDataTableBlockQueryFactory dataBaseDataTableBlockQueryFactory,
            final Parameters parameters,
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        this.dataBaseDataTableBlockQueryFactory = dataBaseDataTableBlockQueryFactory;
        this.separator = parameters.getCsvSeparator();
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

    public List<String> getBlock() {
        return current;
    }

    private void findSqlQuery(final DriverConnection driverConnection, final TableBase table, final Long pageCount) {
        final Driver driver = driverConnection.getDriver();
        final DataBaseDataTableBlockQuery query = dataBaseDataTableBlockQueryFactory.apply(driver);
        sqlQuery = query.get((TableDefinitionWrapper) table, pageCount, BLOCK);
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

        List<String> lines = new ArrayList<>();

        ResultSetMetaData metadata = resultSet.getMetaData();
        int columnCount = metadata.getColumnCount();

        for (int j = 0; j < columnCount; j++) {
            String columnName = metadata.getColumnName(j + 1);
            String columnType = metadata.getColumnTypeName(j + 1);
            LOGGER.debug("[DataBaseDataTableReaderIterator] Column Name: " + columnName);
            LOGGER.debug("[DataBaseDataTableReaderIterator] Column Type: " + columnType);
        }

        while (resultSet.next()) {
            List<String> objs = new ArrayList<>(columnCount);

            for (int j = 0; j < columnCount; j++) {
                objs.add(resultSet.getString(j + 1));
            }

            String line = StringUtils.join(objs, separator);
            lines.add(line);
        }

        current = lines;
    }

}
