/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.increments;

import java.util.function.Function;

import javax.inject.Named;
import javax.inject.Singleton;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.table.increments.support.Db2DataBaseTableIncrementQuery;
import com.github.yadickson.autodblq.db.table.increments.support.MsSqlDataBaseTableIncrementQuery;
import com.github.yadickson.autodblq.db.table.increments.support.OracleDataBaseTableIncrementQuery;
import com.github.yadickson.autodblq.db.table.increments.support.PostgreSqlDataBaseTableIncrementQuery;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableIncrementQueryFactory implements Function<Driver, DataBaseTableIncrementQuery> {

    @Override
    public DataBaseTableIncrementQuery apply(final Driver driver) {

        switch (driver) {
            case DB2:
                return new Db2DataBaseTableIncrementQuery();
            case ORACLE:
                return new OracleDataBaseTableIncrementQuery();
            case POSTGRESQL:
                return new PostgreSqlDataBaseTableIncrementQuery();
            case MSSQL:
                return new MsSqlDataBaseTableIncrementQuery();
        }

        throw new DataBaseTableIncrementQueryNotSupportException("Data base table increment query [" + driver + "] not supported");
    }

}
