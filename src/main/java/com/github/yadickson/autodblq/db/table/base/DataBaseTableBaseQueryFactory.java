/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.base;

import java.util.function.Function;

import javax.inject.Named;
import javax.inject.Singleton;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.table.base.support.Db2DataBaseTableBaseQuery;
import com.github.yadickson.autodblq.db.table.base.support.MsSqlDataBaseTableBaseQuery;
import com.github.yadickson.autodblq.db.table.base.support.OracleDataBaseTableBaseQuery;
import com.github.yadickson.autodblq.db.table.base.support.PostgreSqlDataBaseTableBaseQuery;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableBaseQueryFactory implements Function<Driver, DataBaseTableBaseQuery> {

    @Override
    public DataBaseTableBaseQuery apply(final Driver driver) {

        switch (driver) {
            case DB2:
                return new Db2DataBaseTableBaseQuery();
            case ORACLE:
                return new OracleDataBaseTableBaseQuery();
            case POSTGRESQL:
                return new PostgreSqlDataBaseTableBaseQuery();
            case MSSQL:
                return new MsSqlDataBaseTableBaseQuery();
        }

        throw new DataBaseTableBaseQueryNotSupportException("Data base table query [" + driver + "] not supported");
    }

}
