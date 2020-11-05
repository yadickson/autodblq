/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.primarykeys;
import java.util.function.Function;

import javax.inject.Named;
import javax.inject.Singleton;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.table.primarykeys.support.Db2DataBaseTablePrimaryKeyQuery;
import com.github.yadickson.autodblq.db.table.primarykeys.support.MsSqlDataBaseTablePrimaryKeyQuery;
import com.github.yadickson.autodblq.db.table.primarykeys.support.OracleDataBaseTablePrimaryKeyQuery;
import com.github.yadickson.autodblq.db.table.primarykeys.support.PostgreSqlDataBaseTablePrimaryKeyQuery;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTablePrimaryKeyQueryFactory implements Function<Driver, DataBaseTablePrimaryKeyQuery> {

    @Override
    public DataBaseTablePrimaryKeyQuery apply(final Driver driver) {

        switch (driver) {
            case DB2:
                return new Db2DataBaseTablePrimaryKeyQuery();
            case ORACLE:
                return new OracleDataBaseTablePrimaryKeyQuery();
            case POSTGRESQL:
                return new PostgreSqlDataBaseTablePrimaryKeyQuery();
            case MSSQL:
                return new MsSqlDataBaseTablePrimaryKeyQuery();
        }

        throw new DataBaseTablePrimaryKeyQueryNotSupportException("Data base table primary key query [" + driver + "] not supported");
    }

}
