/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.increments;

import javax.inject.Named;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintQuery;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintQueryFactory;
import com.github.yadickson.autodblq.db.table.constraint.increments.support.Db2DataBaseTableIncrementQuery;
import com.github.yadickson.autodblq.db.table.constraint.increments.support.MsSqlDataBaseTableIncrementQuery;
import com.github.yadickson.autodblq.db.table.constraint.increments.support.OracleDataBaseTableIncrementQuery;
import com.github.yadickson.autodblq.db.table.constraint.increments.support.PostgreSqlDataBaseTableIncrementQuery;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableIncrementQueryFactory implements DataBaseTableConstraintQueryFactory {

    @Override
    public DataBaseTableConstraintQuery apply(final Driver driver) {

        switch (driver) {
            case DB2:
                return new Db2DataBaseTableIncrementQuery();
            case ORACLE:
                return new OracleDataBaseTableIncrementQuery();
            case POSTGRESQL:
                return new PostgreSqlDataBaseTableIncrementQuery();
            case MSSQL:
                return new MsSqlDataBaseTableIncrementQuery();
        }

        throw new DataBaseTableIncrementQueryNotSupportException("Data base table increment query [" + driver + "] not supported");
    }

}
