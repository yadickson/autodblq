/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.indexes;

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
import com.github.yadickson.autodblq.db.table.indexes.model.TableIndexBean;
import com.github.yadickson.autodblq.db.util.SqlExecuteToGetList;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableIndexReader extends DataBaseTableReader {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableIndexReader.class);

    private final DataBaseTableIndexQueryFactory dataBaseTableIndexQueryFactory;
    private final SqlExecuteToGetList sqlExecuteToGetList;
    private final DataBaseTableIndexMapper dataBaseTableIndexMapper;

    private String sqlQuery;
    private List<TableIndexBean> indexes;

    @Inject
    public DataBaseTableIndexReader(
            final DataBaseTableIndexQueryFactory dataBaseTableIndexQueryFactory,
            final SqlExecuteToGetList sqlExecuteToGetList,
            final DataBaseTableIndexMapper dataBaseTableIndexMapper
    ) {
        super(DataBaseGeneratorType.TABLE_INDEXES);
        this.dataBaseTableIndexQueryFactory = dataBaseTableIndexQueryFactory;
        this.sqlExecuteToGetList = sqlExecuteToGetList;
        this.dataBaseTableIndexMapper = dataBaseTableIndexMapper;
    }

    @Override
    protected List<TableBase> processTable(
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        findSqlQuery(driverConnection, table);
        findIndexes(driverConnection);
        return processIndexes(table);
    }

    private void findSqlQuery(
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        final Driver driver = driverConnection.getDriver();
        final DataBaseTableIndexQuery query = dataBaseTableIndexQueryFactory.apply(driver);
        sqlQuery = query.get(table);
        LOGGER.debug("[DataBaseTableIndexReader] SQL: " + sqlQuery);
    }

    private void findIndexes(final DriverConnection driverConnection) {
        LOGGER.info("[DataBaseTableIndexReader] Starting");
        indexes = sqlExecuteToGetList.execute(driverConnection, sqlQuery, TableIndexBean.class);
        LOGGER.info("[DataBaseTableIndexReader] Total: " + indexes.size());
    }

    private List<TableBase> processIndexes(final TableBase tableBase) {
        return dataBaseTableIndexMapper.apply(tableBase, indexes);
    }

}
