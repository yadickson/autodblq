/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.definitions;
import java.util.function.Function;

import javax.inject.Named;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.table.definitions.support.Db2DataBaseTableDefinitionQuery;
import com.github.yadickson.autodblq.db.table.definitions.support.MsSqlDataBaseTableDefinitionQuery;
import com.github.yadickson.autodblq.db.table.definitions.support.OracleDataBaseTableDefinitionQuery;
import com.github.yadickson.autodblq.db.table.definitions.support.PostgreSqlDataBaseTableDefinitionQuery;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableDefinitionQueryFactory implements Function<Driver, DataBaseTableDefinitionQuery> {
    
    @Override
    public DataBaseTableDefinitionQuery apply(final Driver driver) {

        switch (driver) {
            case DB2:
                return new Db2DataBaseTableDefinitionQuery();
            case ORACLE:
                return new OracleDataBaseTableDefinitionQuery();
            case POSTGRESQL:
                return new PostgreSqlDataBaseTableDefinitionQuery();
            case MSSQL:
                return new MsSqlDataBaseTableDefinitionQuery();
        }

        throw new DataBaseTableDefinitionQueryNotSupportException("Data base table column query [" + driver + "] not supported");
    }

}
