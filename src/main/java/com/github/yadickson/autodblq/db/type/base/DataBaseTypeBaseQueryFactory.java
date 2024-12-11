/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.type.base;

import java.util.function.Function;

import javax.inject.Named;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.type.base.support.Db2DataBaseTypeBaseQuery;
import com.github.yadickson.autodblq.db.type.base.support.MsSqlDataBaseTypeBaseQuery;
import com.github.yadickson.autodblq.db.type.base.support.OracleDataBaseTypeBaseQuery;
import com.github.yadickson.autodblq.db.type.base.support.PostgreSqlDataBaseTypeBaseQuery;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTypeBaseQueryFactory implements Function<Driver, DataBaseTypeBaseQuery> {

    @Override
    public DataBaseTypeBaseQuery apply(final Driver driver) {

        switch (driver) {
            case DB2:
                return new Db2DataBaseTypeBaseQuery();
            case ORACLE:
                return new OracleDataBaseTypeBaseQuery();
            case POSTGRESQL:
                return new PostgreSqlDataBaseTypeBaseQuery();
            case MSSQL:
                return new MsSqlDataBaseTypeBaseQuery();
        }

        throw new DataBaseTypeBaseQueryNotSupportException("Data base type query [" + driver + "] not supported");
    }

}
