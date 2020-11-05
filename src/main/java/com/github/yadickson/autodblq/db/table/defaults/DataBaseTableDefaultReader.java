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

    private String sqlQuery;
    private List<TableDefaultBean> defaults;

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
    protected List<TableBase> processTable(
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        findSqlQuery(driverConnection, table);
        findDefaults(driverConnection);
        return processDefaultes(table);
    }

    private void findSqlQuery(
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        final Driver driver = driverConnection.getDriver();
        final DataBaseTableDefaultQuery query = dataBaseTableDefaultQueryFactory.apply(driver);
        sqlQuery = query.get(table);
        LOGGER.debug("[DataBaseTableDefaultReader] SQL: " + sqlQuery);
    }

    private void findDefaults(final DriverConnection driverConnection) {
        LOGGER.info("[DataBaseTableDefaultReader] Starting");
        defaults = sqlExecuteToGetList.execute(driverConnection, sqlQuery, TableDefaultBean.class);
        LOGGER.info("[DataBaseTableDefaultReader] Total: " + defaults.size());
    }

    private List<TableBase> processDefaultes(final TableBase tableBase) {
        return dataBaseTableDefaultMapper.apply(tableBase, defaults);
    }

}
