/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.primarykeys;

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
import com.github.yadickson.autodblq.db.table.primarykeys.model.TablePrimaryKeyBean;
import com.github.yadickson.autodblq.db.util.SqlExecuteToGetList;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTablePrimaryKeyReader extends DataBaseTableReader {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTablePrimaryKeyReader.class);

    private final DataBaseTablePrimaryKeyQueryFactory dataBaseTablePrimaryKeyQueryFactory;
    private final SqlExecuteToGetList sqlExecuteToGetList;
    private final DataBaseTablePrimaryKeyMapper dataBaseTablePrimaryKeyMapper;

    @Inject
    public DataBaseTablePrimaryKeyReader(
            final DataBaseTablePrimaryKeyQueryFactory dataBaseTablePrimaryKeyQueryFactory,
            final SqlExecuteToGetList sqlExecuteToGetList,
            final DataBaseTablePrimaryKeyMapper dataBaseTablePrimaryKeyMapper
    ) {
        super(DataBaseGeneratorType.TABLE_PRIMARY_KEYS);
        this.dataBaseTablePrimaryKeyQueryFactory = dataBaseTablePrimaryKeyQueryFactory;
        this.sqlExecuteToGetList = sqlExecuteToGetList;
        this.dataBaseTablePrimaryKeyMapper = dataBaseTablePrimaryKeyMapper;
    }

    @Override
    protected String findSqlQuery(
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        final Driver driver = driverConnection.getDriver();
        final DataBaseTablePrimaryKeyQuery query = dataBaseTablePrimaryKeyQueryFactory.apply(driver);
        final String sqlQuery = query.get(table);
        LOGGER.debug("[DataBaseTablePrimaryKeyReader] SQL: " + sqlQuery);
        return sqlQuery;
    }

    @Override
    protected List<TableBase> findElements(
            final DriverConnection driverConnection,
            final TableBase table,
            final String sqlQuery
    ) {
        LOGGER.info("[DataBaseTablePrimaryKeyReader] Starting");
        List<TablePrimaryKeyBean> primaryKeys = sqlExecuteToGetList.execute(driverConnection, sqlQuery, TablePrimaryKeyBean.class);
        LOGGER.info("[DataBaseTablePrimaryKeyReader] Total: " + primaryKeys.size());
        return dataBaseTablePrimaryKeyMapper.apply(table, primaryKeys);
    }

}
