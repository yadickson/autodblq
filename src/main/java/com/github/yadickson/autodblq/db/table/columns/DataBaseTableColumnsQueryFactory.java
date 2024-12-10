/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.columns;

import java.util.function.Function;

import javax.inject.Named;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.table.columns.support.Db2DataBaseTableColumnsQuery;
import com.github.yadickson.autodblq.db.table.columns.support.MsSqlDataBaseTableColumnsQuery;
import com.github.yadickson.autodblq.db.table.columns.support.OracleDataBaseTableColumnsQuery;
import com.github.yadickson.autodblq.db.table.columns.support.PostgreSqlDataBaseTableColumnsQuery;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableColumnsQueryFactory implements Function<Driver, DataBaseTableColumnsQuery> {
    
    @Override
    public DataBaseTableColumnsQuery apply(final Driver driver) {

        switch (driver) {
            case DB2:
                return new Db2DataBaseTableColumnsQuery();
            case ORACLE:
                return new OracleDataBaseTableColumnsQuery();
            case POSTGRESQL:
                return new PostgreSqlDataBaseTableColumnsQuery();
            case MSSQL:
                return new MsSqlDataBaseTableColumnsQuery();
        }

        throw new DataBaseTableColumnsQueryNotSupportException("Data base table column query [" + driver + "] not supported");
    }

}
