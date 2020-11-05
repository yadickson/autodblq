/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.function.base;

import java.util.function.Function;

import javax.inject.Named;
import javax.inject.Singleton;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.function.base.support.Db2DataBaseFunctionBaseQuery;
import com.github.yadickson.autodblq.db.function.base.support.MsSqlDataBaseFunctionBaseQuery;
import com.github.yadickson.autodblq.db.function.base.support.OracleDataBaseFunctionBaseQuery;
import com.github.yadickson.autodblq.db.function.base.support.PostgreSqlDataBaseFunctionBaseQuery;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseFunctionBaseQueryFactory implements Function<Driver, DataBaseFunctionBaseQuery> {

    @Override
    public DataBaseFunctionBaseQuery apply(final Driver driver) {

        switch (driver) {
            case DB2:
                return new Db2DataBaseFunctionBaseQuery();
            case ORACLE:
                return new OracleDataBaseFunctionBaseQuery();
            case POSTGRESQL:
                return new PostgreSqlDataBaseFunctionBaseQuery();
            case MSSQL:
                return new MsSqlDataBaseFunctionBaseQuery();
        }

        throw new DataBaseFunctionBaseQueryNotSupportException("Data base function query [" + driver + "] not supported");
    }

}
