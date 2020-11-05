/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.base;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.base.model.TableBaseBean;
import com.github.yadickson.autodblq.db.table.definitions.DataBaseTableDefinitionReader;
import com.github.yadickson.autodblq.db.util.SqlExecuteToGetList;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableBaseReader {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableBaseReader.class);

    private final DataBaseTableBaseQueryFactory dataBaseTableQueryFactory;
    private final SqlExecuteToGetList sqlExecuteToGetList;
    private final DataBaseTableBaseMapper dataBaseTableMapper;
    private final DataBaseTableDefinitionReader dataBaseTableDefinitionReader;

    private String sqlQuery;
    private List<TableBaseBean> tables;

    @Inject
    public DataBaseTableBaseReader(
            final DataBaseTableBaseQueryFactory dataBaseTableQueryFactory,
            final SqlExecuteToGetList sqlExecuteToGetList,
            final DataBaseTableBaseMapper dataBaseTableMapper,
            final DataBaseTableDefinitionReader dataBaseTableDefinitionReader
    ) {
        this.dataBaseTableQueryFactory = dataBaseTableQueryFactory;
        this.sqlExecuteToGetList = sqlExecuteToGetList;
        this.dataBaseTableMapper = dataBaseTableMapper;
        this.dataBaseTableDefinitionReader = dataBaseTableDefinitionReader;
    }

    public List<TableBase> execute(
            final List<String> filter,
            final DriverConnection driverConnection
    ) {

        try {

            if (CollectionUtils.isEmpty(filter)) {
                return Collections.EMPTY_LIST;
            }

            findSqlQuery(filter, driverConnection);
            findTables(driverConnection);
            return processTables(driverConnection);

        } catch (RuntimeException ex) {
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
        LOGGER.debug("[DataBaseTableBaseReader] SQL: " + sqlQuery);
    }

    private void findTables(final DriverConnection driverConnection) {
        LOGGER.info("[DataBaseTableBaseReader] Starting");
        tables = sqlExecuteToGetList.execute(driverConnection, sqlQuery, TableBaseBean.class);
        LOGGER.info("[DataBaseTableBaseReader] Total: " + tables.size());
    }

    private List<TableBase> processTables(final DriverConnection driverConnection) {
        List<TableBase> result = dataBaseTableMapper.apply(tables);
        return dataBaseTableDefinitionReader.execute(driverConnection, result);
    }

}
