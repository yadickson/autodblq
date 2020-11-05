/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.foreignkeys;

import java.util.function.Function;

import javax.inject.Named;
import javax.inject.Singleton;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.table.foreignkeys.support.Db2DataBaseTableForeignKeyQuery;
import com.github.yadickson.autodblq.db.table.foreignkeys.support.MsSqlDataBaseTableForeignKeyQuery;
import com.github.yadickson.autodblq.db.table.foreignkeys.support.OracleDataBaseTableForeignKeyQuery;
import com.github.yadickson.autodblq.db.table.foreignkeys.support.PostgreSqlDataBaseTableForeignKeyQuery;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableForeignKeyQueryFactory implements Function<Driver, DataBaseTableForeignKeyQuery> {

    @Override
    public DataBaseTableForeignKeyQuery apply(final Driver driver) {

        switch (driver) {
            case DB2:
                return new Db2DataBaseTableForeignKeyQuery();
            case ORACLE:
                return new OracleDataBaseTableForeignKeyQuery();
            case POSTGRESQL:
                return new PostgreSqlDataBaseTableForeignKeyQuery();
            case MSSQL:
                return new MsSqlDataBaseTableForeignKeyQuery();
        }

        throw new DataBaseTableForeignKeyQueryNotSupportException("Data base table foreign key query [" + driver + "] not supported");
    }

}
