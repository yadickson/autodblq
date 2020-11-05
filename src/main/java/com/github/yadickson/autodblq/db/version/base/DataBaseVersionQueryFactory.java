/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.version.base;

import java.util.function.Function;

import javax.inject.Named;
import javax.inject.Singleton;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.version.base.support.Db2DataBaseVersionQuery;
import com.github.yadickson.autodblq.db.version.base.support.MsSqlDataBaseVersionQuery;
import com.github.yadickson.autodblq.db.version.base.support.OracleDataBaseVersionQuery;
import com.github.yadickson.autodblq.db.version.base.support.PostgreSqlDataBaseVersionQuery;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseVersionQueryFactory implements Function<Driver, DataBaseVersionQuery> {

    @Override
    public DataBaseVersionQuery apply(final Driver driver) {

        switch (driver) {
            case DB2:
                return new Db2DataBaseVersionQuery();
            case ORACLE:
                return new OracleDataBaseVersionQuery();
            case POSTGRESQL:
                return new PostgreSqlDataBaseVersionQuery();
            case MSSQL:
                return new MsSqlDataBaseVersionQuery();
        }

        throw new DataBaseVersionQueryNotSupportException("Data base version query [" + driver + "] not supported");
    }

}
