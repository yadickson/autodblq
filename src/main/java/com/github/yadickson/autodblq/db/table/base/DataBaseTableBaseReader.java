/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.base;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.DataBaseGeneratorType;
import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetList;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetListFactory;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.base.model.TableBaseBean;
import com.github.yadickson.autodblq.db.table.columns.DataBaseTableColumnsReader;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableBaseReader {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableBaseReader.class);

    private final DataBaseTableBaseQueryFactory dataBaseTableQueryFactory;
    private final SqlExecuteToGetList sqlExecuteToGetList;
    private final DataBaseTableBaseMapper dataBaseTableMapper;
    private final DataBaseTableColumnsReader dataBaseTableColumnsReader;

    private String sqlQuery;
    private List<TableBaseBean> tables;

    @Inject
    public DataBaseTableBaseReader(
            final DataBaseTableBaseQueryFactory dataBaseTableQueryFactory,
            final SqlExecuteToGetListFactory sqlExecuteToGetListFactory,
            final DataBaseTableBaseMapper dataBaseTableMapper,
            final DataBaseTableColumnsReader dataBaseTableColumnsReader
    ) {
        this.dataBaseTableQueryFactory = dataBaseTableQueryFactory;
        this.sqlExecuteToGetList = sqlExecuteToGetListFactory.apply(DataBaseGeneratorType.TABLE_BASE);
        this.dataBaseTableMapper = dataBaseTableMapper;
        this.dataBaseTableColumnsReader = dataBaseTableColumnsReader;
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
            return processTables(driverConnection, filter)
                    .stream()
                    .sorted(Comparator.comparing(TableBase::getName))
                    .collect(Collectors.toList());

        } catch (RuntimeException ex) {
            LOGGER.error(ex);
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
        tables = sqlExecuteToGetList.execute(driverConnection, sqlQuery);
        LOGGER.info("[DataBaseTableBaseReader] Total: " + tables.size());
    }

    private List<TableBase> processTables(final DriverConnection driverConnection, final List<String> filter) {
        List<TableBase> result = dataBaseTableMapper.apply(tables);
        return sortTables(driverConnection, result, filter);
    }

    private List<TableBase> sortTables(final DriverConnection driverConnection, final List<TableBase> result, final List<String> filter) {
        List<TableBase> definitions = dataBaseTableColumnsReader.execute(driverConnection, result);
        Collections.sort(definitions, new DataBaseTableBaseSort(filter));
        return definitions;
    }

}
