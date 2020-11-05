/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.uniques;

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
import com.github.yadickson.autodblq.db.table.uniques.model.TableUniqueBean;
import com.github.yadickson.autodblq.db.util.SqlExecuteToGetList;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableUniqueReader extends DataBaseTableReader {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableUniqueReader.class);

    private final DataBaseTableUniqueQueryFactory dataBaseTableUniqueQueryFactory;
    private final SqlExecuteToGetList sqlExecuteToGetList;
    private final DataBaseTableUniqueMapper dataBaseTableUniqueMapper;

    @Inject
    public DataBaseTableUniqueReader(
            final DataBaseTableUniqueQueryFactory dataBaseTableUniqueQueryFactory,
            final SqlExecuteToGetList sqlExecuteToGetList,
            final DataBaseTableUniqueMapper dataBaseTableUniqueMapper
    ) {
        super(DataBaseGeneratorType.TABLE_UNIQUES);
        this.dataBaseTableUniqueQueryFactory = dataBaseTableUniqueQueryFactory;
        this.sqlExecuteToGetList = sqlExecuteToGetList;
        this.dataBaseTableUniqueMapper = dataBaseTableUniqueMapper;
    }

    @Override
    protected String findSqlQuery(
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        final Driver driver = driverConnection.getDriver();
        final DataBaseTableUniqueQuery query = dataBaseTableUniqueQueryFactory.apply(driver);
        final String sqlQuery = query.get(table);
        LOGGER.debug("[DataBaseTableUniqueReader] SQL: " + sqlQuery);
        return sqlQuery;
    }

    @Override
    protected List<TableBase> findElements(
            final DriverConnection driverConnection,
            final TableBase table,
            final String sqlQuery
    ) {
        LOGGER.info("[DataBaseTableUniqueReader] Starting");
        List<TableUniqueBean> uniques = sqlExecuteToGetList.execute(driverConnection, sqlQuery, TableUniqueBean.class);
        LOGGER.info("[DataBaseTableUniqueReader] Total: " + uniques.size());
        return dataBaseTableUniqueMapper.apply(table, uniques);
    }

}
