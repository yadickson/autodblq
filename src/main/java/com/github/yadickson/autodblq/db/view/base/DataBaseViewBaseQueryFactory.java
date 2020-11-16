/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.view.base;

import java.util.function.Function;

import javax.inject.Named;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.view.base.support.Db2DataBaseViewBaseQuery;
import com.github.yadickson.autodblq.db.view.base.support.MsSqlDataBaseViewBaseQuery;
import com.github.yadickson.autodblq.db.view.base.support.OracleDataBaseViewBaseQuery;
import com.github.yadickson.autodblq.db.view.base.support.PostgreSqlDataBaseViewBaseQuery;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseViewBaseQueryFactory implements Function<Driver, DataBaseViewBaseQuery> {

    @Override
    public DataBaseViewBaseQuery apply(final Driver driver) {

        switch (driver) {
            case DB2:
                return new Db2DataBaseViewBaseQuery();
            case ORACLE:
                return new OracleDataBaseViewBaseQuery();
            case POSTGRESQL:
                return new PostgreSqlDataBaseViewBaseQuery();
            case MSSQL:
                return new MsSqlDataBaseViewBaseQuery();
        }

        throw new DataBaseViewBaseQueryNotSupportException("Data base view query [" + driver + "] not supported");
    }

}
