/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.definitions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.table.DataBaseTableReaderException;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.definitions.model.TableColumnBean;
import com.github.yadickson.autodblq.db.table.definitions.model.TableDefinitionWrapper;
import com.github.yadickson.autodblq.db.util.SqlExecuteToGetList;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableDefinitionReader {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableDefinitionReader.class);

    private final DataBaseTableDefinitionQueryFactory dataBaseTableDefinitionQueryFactory;
    private final SqlExecuteToGetList sqlExecuteToGetList;
    private final DataBaseTableDefinitionMapper dataBaseTableDefinitionMapper;

    private String sqlQuery;
    private List<TableColumnBean> columns;

    @Inject
    public DataBaseTableDefinitionReader(
            final DataBaseTableDefinitionQueryFactory dataBaseTableDefinitionQueryFactory,
            final SqlExecuteToGetList sqlExecuteToGetList,
            final DataBaseTableDefinitionMapper dataBaseTableDefinitionMapper
    ) {
        this.dataBaseTableDefinitionQueryFactory = dataBaseTableDefinitionQueryFactory;
        this.sqlExecuteToGetList = sqlExecuteToGetList;
        this.dataBaseTableDefinitionMapper = dataBaseTableDefinitionMapper;
    }

    public List<TableBase> execute(final DriverConnection driverConnection, final List<TableBase> tables) {

        try {

            return processTables(driverConnection, tables);

        } catch (RuntimeException ex) {
            throw new DataBaseTableReaderException(ex);
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
        final DataBaseTableDefinitionQuery query = dataBaseTableDefinitionQueryFactory.apply(driver);
        sqlQuery = query.get(table);
        LOGGER.debug("[DataBaseTableDefinitionReader] SQL: " + sqlQuery);
    }

    private void findDefinitions(final DriverConnection driverConnection) {
        LOGGER.info("[DataBaseTableDefinitionReader] Starting");
        columns = sqlExecuteToGetList.execute(driverConnection, sqlQuery, TableColumnBean.class);
        LOGGER.info("[DataBaseTableDefinitionReader] Total: " + columns.size());
    }

    private TableBase fillDefinitions(final TableBase table) {
        return new TableDefinitionWrapper(table, dataBaseTableDefinitionMapper.apply(columns));
    }

}
