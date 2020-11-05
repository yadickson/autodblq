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

    private String sqlQuery;
    private List<TableUniqueBean> uniques;

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
    protected List<TableBase> processTable(
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        findSqlQuery(driverConnection, table);
        findUniques(driverConnection);
        return processUniques(table);
    }

    private void findSqlQuery(
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        final Driver driver = driverConnection.getDriver();
        final DataBaseTableUniqueQuery query = dataBaseTableUniqueQueryFactory.apply(driver);
        sqlQuery = query.get(table);
        LOGGER.debug("[DataBaseTableUniqueReader] SQL: " + sqlQuery);
    }

    private void findUniques(final DriverConnection driverConnection) {
        LOGGER.info("[DataBaseTableUniqueReader] Starting");
        uniques = sqlExecuteToGetList.execute(driverConnection, sqlQuery, TableUniqueBean.class);
        LOGGER.info("[DataBaseTableUniqueReader] Total: " + uniques.size());
    }

    private List<TableBase> processUniques(final TableBase tableBase) {
        return dataBaseTableUniqueMapper.apply(tableBase, uniques);
    }

}
