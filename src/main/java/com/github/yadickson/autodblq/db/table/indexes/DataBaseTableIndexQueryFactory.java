/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.indexes;

import java.util.function.Function;

import javax.inject.Named;
import javax.inject.Singleton;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.table.indexes.support.Db2DataBaseTableIndexQuery;
import com.github.yadickson.autodblq.db.table.indexes.support.MsSqlDataBaseTableIndexQuery;
import com.github.yadickson.autodblq.db.table.indexes.support.OracleDataBaseTableIndexQuery;
import com.github.yadickson.autodblq.db.table.indexes.support.PostgreSqlDataBaseTableIndexQuery;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableIndexQueryFactory implements Function<Driver, DataBaseTableIndexQuery> {

    @Override
    public DataBaseTableIndexQuery apply(final Driver driver) {

        switch (driver) {
            case DB2:
                return new Db2DataBaseTableIndexQuery();
            case ORACLE:
                return new OracleDataBaseTableIndexQuery();
            case POSTGRESQL:
                return new PostgreSqlDataBaseTableIndexQuery();
            case MSSQL:
                return new MsSqlDataBaseTableIndexQuery();
        }

        throw new DataBaseTableIndexQueryNotSupportException("Data base table index query [" + driver + "] not supported");
    }

}
