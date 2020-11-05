/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.defaults;

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
import com.github.yadickson.autodblq.db.table.defaults.model.TableDefaultBean;
import com.github.yadickson.autodblq.db.util.SqlExecuteToGetList;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableDefaultReader extends DataBaseTableReader {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableDefaultReader.class);

    private final DataBaseTableDefaultQueryFactory dataBaseTableDefaultQueryFactory;
    private final SqlExecuteToGetList sqlExecuteToGetList;
    private final DataBaseTableDefaultMapper dataBaseTableDefaultMapper;

    @Inject
    public DataBaseTableDefaultReader(
            final DataBaseTableDefaultQueryFactory dataBaseTableDefaultQueryFactory,
            final SqlExecuteToGetList sqlExecuteToGetList,
            final DataBaseTableDefaultMapper dataBaseTableDefaultMapper
    ) {
        super(DataBaseGeneratorType.TABLE_DEFAULTS);
        this.dataBaseTableDefaultQueryFactory = dataBaseTableDefaultQueryFactory;
        this.sqlExecuteToGetList = sqlExecuteToGetList;
        this.dataBaseTableDefaultMapper = dataBaseTableDefaultMapper;
    }

    @Override
    protected String findSqlQuery(
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        final Driver driver = driverConnection.getDriver();
        final DataBaseTableDefaultQuery query = dataBaseTableDefaultQueryFactory.apply(driver);
        final String sqlQuery = query.get(table);
        LOGGER.debug("[DataBaseTableDefaultReader] SQL: " + sqlQuery);
        return sqlQuery;
    }

    @Override
    protected List<TableBase> findElements(
            final DriverConnection driverConnection,
            final TableBase table,
            final String sqlQuery
    ) {
        LOGGER.info("[DataBaseTableDefaultReader] Starting");
        List<TableDefaultBean> defaults = sqlExecuteToGetList.execute(driverConnection, sqlQuery, TableDefaultBean.class);
        LOGGER.info("[DataBaseTableDefaultReader] Total: " + defaults.size());
        return dataBaseTableDefaultMapper.apply(table, defaults);
    }

}
