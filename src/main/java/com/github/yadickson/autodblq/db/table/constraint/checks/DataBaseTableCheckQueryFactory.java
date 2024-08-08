/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.checks;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintQuery;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintQueryFactory;
import com.github.yadickson.autodblq.db.table.constraint.checks.support.Db2DataBaseTableCheckQuery;
import com.github.yadickson.autodblq.db.table.constraint.checks.support.MsSqlDataBaseTableCheckQuery;
import com.github.yadickson.autodblq.db.table.constraint.checks.support.OracleDataBaseTableCheckQuery;
import com.github.yadickson.autodblq.db.table.constraint.checks.support.PostgreSqlDataBaseTableCheckQuery;

import javax.inject.Named;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableCheckQueryFactory implements DataBaseTableConstraintQueryFactory {

    @Override
    public DataBaseTableConstraintQuery apply(final Driver driver) {

        switch (driver) {
            case DB2:
                return new Db2DataBaseTableCheckQuery();
            case ORACLE:
                return new OracleDataBaseTableCheckQuery();
            case POSTGRESQL:
                return new PostgreSqlDataBaseTableCheckQuery();
            case MSSQL:
                return new MsSqlDataBaseTableCheckQuery();
        }

        throw new DataBaseTableCheckQueryNotSupportException("Data base table default query [" + driver + "] not supported");
    }

}
