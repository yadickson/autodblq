/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.sequence.base;

import java.util.function.Function;

import javax.inject.Named;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.sequence.base.support.Db2DataBaseSequenceBaseQuery;
import com.github.yadickson.autodblq.db.sequence.base.support.MsSqlDataBaseSequenceBaseQuery;
import com.github.yadickson.autodblq.db.sequence.base.support.OracleDataBaseSequenceBaseQuery;
import com.github.yadickson.autodblq.db.sequence.base.support.PostgreSqlDataBaseSequenceBaseQuery;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseSequenceBaseQueryFactory implements Function<Driver, DataBaseSequenceBaseQuery> {

    @Override
    public DataBaseSequenceBaseQuery apply(final Driver driver) {

        switch (driver) {
            case DB2:
                return new Db2DataBaseSequenceBaseQuery();
            case ORACLE:
                return new OracleDataBaseSequenceBaseQuery();
            case POSTGRESQL:
                return new PostgreSqlDataBaseSequenceBaseQuery();
            case MSSQL:
                return new MsSqlDataBaseSequenceBaseQuery();
        }

        throw new DataBaseSequenceBaseQueryNotSupportException("Data base sequence query [" + driver + "] not supported");
    }

}
