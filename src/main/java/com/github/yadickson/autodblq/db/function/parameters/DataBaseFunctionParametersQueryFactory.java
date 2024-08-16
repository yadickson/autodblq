/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.function.parameters;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.function.parameters.support.Db2DataBaseFunctionParametersQuery;
import com.github.yadickson.autodblq.db.function.parameters.support.MsSqlDataBaseFunctionParametersQuery;
import com.github.yadickson.autodblq.db.function.parameters.support.OracleDataBaseFunctionParametersQuery;
import com.github.yadickson.autodblq.db.function.parameters.support.PostgreSqlDataBaseFunctionParametersQuery;

import javax.inject.Named;
import java.util.function.Function;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseFunctionParametersQueryFactory implements Function<Driver, DataBaseFunctionParametersQuery> {
    
    @Override
    public DataBaseFunctionParametersQuery apply(final Driver driver) {

        switch (driver) {
            case DB2:
                return new Db2DataBaseFunctionParametersQuery();
            case ORACLE:
                return new OracleDataBaseFunctionParametersQuery();
            case POSTGRESQL:
                return new PostgreSqlDataBaseFunctionParametersQuery();
            case MSSQL:
                return new MsSqlDataBaseFunctionParametersQuery();
        }

        throw new DataBaseFunctionParametersQueryNotSupportException("Data base function parameters query [" + driver + "] not supported");
    }

}
