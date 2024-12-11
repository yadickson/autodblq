/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.base;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;

import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetList;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.base.model.TableBaseBean;
import com.github.yadickson.autodblq.db.table.columns.DataBaseTableColumnsReader;
import com.github.yadickson.autodblq.logger.LoggerManager;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableBaseReader {

    private final LoggerManager loggerManager;
    private final DataBaseTableBaseQueryFactory dataBaseTableQueryFactory;
    private final SqlExecuteToGetList sqlExecuteToGetList;
    private final DataBaseTableBaseMapper dataBaseTableMapper;
    private final DataBaseTableColumnsReader dataBaseTableColumnsReader;

    private String sqlQuery;
    private List<TableBaseBean> allTables;

    @Inject
    public DataBaseTableBaseReader(
            LoggerManager loggerManager, final DataBaseTableBaseQueryFactory dataBaseTableQueryFactory,
            final SqlExecuteToGetList sqlExecuteToGetList,
            final DataBaseTableBaseMapper dataBaseTableMapper,
            final DataBaseTableColumnsReader dataBaseTableColumnsReader
    ) {
        this.loggerManager = loggerManager;
        this.dataBaseTableQueryFactory = dataBaseTableQueryFactory;
        this.sqlExecuteToGetList = sqlExecuteToGetList;
        this.dataBaseTableMapper = dataBaseTableMapper;
        this.dataBaseTableColumnsReader = dataBaseTableColumnsReader;
    }

    public List<TableBase> execute(
            final List<String> filter,
            final DriverConnection driverConnection
    ) {

        try {

            if (CollectionUtils.isEmpty(filter)) {
                return new ArrayList<>();
            }

            findSqlQuery(filter, driverConnection);
            findTables(driverConnection);
            return processTables(driverConnection, filter);

        } catch (RuntimeException ex) {
            loggerManager.error(ex.getMessage(), ex);
            throw new DataBaseTableBaseReaderException(ex);
        }
    }

    private void findSqlQuery(
            final List<String> filter,
            final DriverConnection driverConnection
    ) {
        final Driver driver = driverConnection.getDriver();
        final DataBaseTableBaseQuery query = dataBaseTableQueryFactory.apply(driver);
        sqlQuery = query.get(filter);
        loggerManager.debug("[DataBaseTableBaseReader] SQL: " + sqlQuery);
    }

    private void findTables(final DriverConnection driverConnection) {
        loggerManager.info("[DataBaseTableBaseReader] Starting");
        allTables = sqlExecuteToGetList.execute(driverConnection, sqlQuery, TableBaseBean.class);
        loggerManager.info("[DataBaseTableBaseReader] Total: " + allTables.size());
    }

    private List<TableBase> processTables(final DriverConnection driverConnection, final List<String> filter) {
        List<TableBase> result = dataBaseTableMapper.apply(allTables);
        List<TableBase> tables = dataBaseTableColumnsReader.execute(driverConnection, result);
        tables.sort(new DataBaseTableBaseSort(filter));
        return tables;
    }

}
