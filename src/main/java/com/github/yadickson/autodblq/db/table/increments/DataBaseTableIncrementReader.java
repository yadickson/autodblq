/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.increments;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.DataBaseGeneratorType;
import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.table.DataBaseTableReader;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.increments.model.TableIncrementBean;
import com.github.yadickson.autodblq.db.util.SqlExecuteToGetList;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableIncrementReader extends DataBaseTableReader {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableIncrementReader.class);

    private final DataBaseTableIncrementQueryFactory dataBaseTableIncrementQueryFactory;
    private final SqlExecuteToGetList sqlExecuteToGetList;
    private final DataBaseTableIncrementMapper dataBaseTableIncrementMapper;

    @Inject
    public DataBaseTableIncrementReader(
            final DataBaseTableIncrementQueryFactory dataBaseTableIncrementQueryFactory,
            final SqlExecuteToGetList sqlExecuteToGetList,
            final DataBaseTableIncrementMapper dataBaseTableIncrementMapper
    ) {
        super(DataBaseGeneratorType.TABLE_INCREMENTS);
        this.dataBaseTableIncrementQueryFactory = dataBaseTableIncrementQueryFactory;
        this.sqlExecuteToGetList = sqlExecuteToGetList;
        this.dataBaseTableIncrementMapper = dataBaseTableIncrementMapper;
    }

    @Override
    protected String findSqlQuery(
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        final Driver driver = driverConnection.getDriver();
        final DataBaseTableIncrementQuery query = dataBaseTableIncrementQueryFactory.apply(driver);
        final String sqlQuery = query.get(table);
        LOGGER.debug("[DataBaseTableIncrementReader] SQL: " + sqlQuery);
        return sqlQuery;
    }

    @Override
    protected List<TableBase> findElements(
            final DriverConnection driverConnection,
            final TableBase table,
            final String sqlQuery
    ) {
        LOGGER.info("[DataBaseTableIncrementReader] Starting");
        List<TableIncrementBean> increments = sqlExecuteToGetList.execute(driverConnection, sqlQuery, TableIncrementBean.class);
        LOGGER.info("[DataBaseTableIncrementReader] Total: " + increments.size());
        return dataBaseTableIncrementMapper.apply(table, increments);
    }

}
