/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.foreignkeys;

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
import com.github.yadickson.autodblq.db.table.foreignkeys.model.TableForeignKeyBean;
import com.github.yadickson.autodblq.db.util.SqlExecuteToGetList;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableForeignKeyReader extends DataBaseTableReader {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableForeignKeyReader.class);

    private final DataBaseTableForeignKeyQueryFactory dataBaseTableForeignKeyQueryFactory;
    private final SqlExecuteToGetList sqlExecuteToGetList;
    private final DataBaseTableForeignKeyMapper dataBaseTableForeignKeyMapper;

    private String sqlQuery;
    private List<TableForeignKeyBean> foreignKeys;

    @Inject
    public DataBaseTableForeignKeyReader(
            final DataBaseTableForeignKeyQueryFactory dataBaseTableForeignKeyQueryFactory,
            final SqlExecuteToGetList sqlExecuteToGetList,
            final DataBaseTableForeignKeyMapper dataBaseTableForeignKeyMapper
    ) {
        super(DataBaseGeneratorType.TABLE_FOREIGN_KEYS);
        this.dataBaseTableForeignKeyQueryFactory = dataBaseTableForeignKeyQueryFactory;
        this.sqlExecuteToGetList = sqlExecuteToGetList;
        this.dataBaseTableForeignKeyMapper = dataBaseTableForeignKeyMapper;
    }

    @Override
    protected List<TableBase> processTable(
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        findSqlQuery(driverConnection, table);
        findForeignKeys(driverConnection);
        return processForeignKeys(table);
    }

    private void findSqlQuery(
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        final Driver driver = driverConnection.getDriver();
        final DataBaseTableForeignKeyQuery query = dataBaseTableForeignKeyQueryFactory.apply(driver);
        sqlQuery = query.get(table);
        LOGGER.debug("[DataBaseTableForeignKeyReader] SQL: " + sqlQuery);
    }

    private void findForeignKeys(final DriverConnection driverConnection) {
        LOGGER.info("[DataBaseTableForeignKeyReader] Starting");
        foreignKeys = sqlExecuteToGetList.execute(driverConnection, sqlQuery, TableForeignKeyBean.class);
        LOGGER.info("[DataBaseTableForeignKeyReader] Total: " + foreignKeys.size());
    }

    private List<TableBase> processForeignKeys(final TableBase table) {
        return dataBaseTableForeignKeyMapper.apply(table, foreignKeys);
    }

}
