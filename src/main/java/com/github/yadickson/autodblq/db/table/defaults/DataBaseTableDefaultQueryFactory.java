/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.defaults;

import java.util.function.Function;

import javax.inject.Named;
import javax.inject.Singleton;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.table.defaults.support.Db2DataBaseTableDefaultQuery;
import com.github.yadickson.autodblq.db.table.defaults.support.MsSqlDataBaseTableDefaultQuery;
import com.github.yadickson.autodblq.db.table.defaults.support.OracleDataBaseTableDefaultQuery;
import com.github.yadickson.autodblq.db.table.defaults.support.PostgreSqlDataBaseTableDefaultQuery;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableDefaultQueryFactory implements Function<Driver, DataBaseTableDefaultQuery> {

    @Override
    public DataBaseTableDefaultQuery apply(final Driver driver) {

        switch (driver) {
            case DB2:
                return new Db2DataBaseTableDefaultQuery();
            case ORACLE:
                return new OracleDataBaseTableDefaultQuery();
            case POSTGRESQL:
                return new PostgreSqlDataBaseTableDefaultQuery();
            case MSSQL:
                return new MsSqlDataBaseTableDefaultQuery();
        }

        throw new DataBaseTableDefaultQueryNotSupportException("Data base table default query [" + driver + "] not supported");
    }

}
