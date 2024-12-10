/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.columns;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetList;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.columns.model.TableColumnBean;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintReaderException;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableColumnsReader {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableColumnsReader.class);

    private final DataBaseTableColumnsQueryFactory dataBaseTableColumnsQueryFactory;
    private final SqlExecuteToGetList sqlExecuteToGetList;
    private final DataBaseTableColumnsMapper dataBaseTableColumnsMapper;

    private String sqlQuery;
    private List<TableColumnBean> columns;

    @Inject
    public DataBaseTableColumnsReader(
            final DataBaseTableColumnsQueryFactory dataBaseTableColumnsQueryFactory,
            final SqlExecuteToGetList sqlExecuteToGetList,
            final DataBaseTableColumnsMapper dataBaseTableColumnsMapper
    ) {
        this.dataBaseTableColumnsQueryFactory = dataBaseTableColumnsQueryFactory;
        this.sqlExecuteToGetList = sqlExecuteToGetList;
        this.dataBaseTableColumnsMapper = dataBaseTableColumnsMapper;
    }

    public List<TableBase> execute(final DriverConnection driverConnection, final List<TableBase> tables) {

        try {

            return processTables(driverConnection, tables);

        } catch (RuntimeException ex) {
            throw new DataBaseTableConstraintReaderException(ex);
        }

    }

    private List<TableBase> processTables(
            final DriverConnection driverConnection,
            final List<TableBase> tables
    ) {
        List<TableBase> tableElements = new ArrayList<>();

        for (TableBase table : tables) {
            TableBase tableBase = processTable(driverConnection, table);
            tableElements.add(tableBase);
        }

        return tableElements;
    }

    protected TableBase processTable(
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        findSqlQuery(driverConnection, table);
        findDefinitions(driverConnection);
        return fillDefinitions(table);
    }

    private void findSqlQuery(
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        final Driver driver = driverConnection.getDriver();
        final DataBaseTableColumnsQuery query = dataBaseTableColumnsQueryFactory.apply(driver);
        sqlQuery = query.get(table);
        LOGGER.debug("[DataBaseTableDefinitionReader] SQL: " + sqlQuery);
    }

    private void findDefinitions(final DriverConnection driverConnection) {
        LOGGER.info("[DataBaseTableDefinitionReader] Starting");
        columns = sqlExecuteToGetList.execute(driverConnection, sqlQuery, TableColumnBean.class);
        LOGGER.info("[DataBaseTableDefinitionReader] Total: " + columns.size());
    }

    private TableBase fillDefinitions(final TableBase table) {
        return new DataBaseTableColumnsWrapper(table, dataBaseTableColumnsMapper.apply(columns));
    }

}
