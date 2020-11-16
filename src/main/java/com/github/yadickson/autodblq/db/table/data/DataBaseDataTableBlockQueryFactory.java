/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.data;

import java.util.function.Function;

import javax.inject.Named;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.table.data.support.Db2DataBaseDataTableBlockQuery;
import com.github.yadickson.autodblq.db.table.data.support.MsSqlDataBaseDataTableBlockQuery;
import com.github.yadickson.autodblq.db.table.data.support.OracleDataBaseDataTableBlockQuery;
import com.github.yadickson.autodblq.db.table.data.support.PostgreSqlDataBaseDataTableBlockQuery;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseDataTableBlockQueryFactory implements Function<Driver, DataBaseDataTableBlockQuery> {

    @Override
    public DataBaseDataTableBlockQuery apply(final Driver driver) {

        switch (driver) {
            case DB2:
                return new Db2DataBaseDataTableBlockQuery();
            case ORACLE:
                return new OracleDataBaseDataTableBlockQuery();
            case POSTGRESQL:
                return new PostgreSqlDataBaseDataTableBlockQuery();
            case MSSQL:
                return new MsSqlDataBaseDataTableBlockQuery();
        }

        throw new DataBaseDataTableBlockQueryNotSupportException("Data base data table block query [" + driver + "] not supported");
    }

}
