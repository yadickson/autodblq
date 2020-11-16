/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.defaults;

import javax.inject.Named;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintQuery;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintQueryFactory;
import com.github.yadickson.autodblq.db.table.constraint.defaults.support.Db2DataBaseTableDefaultQuery;
import com.github.yadickson.autodblq.db.table.constraint.defaults.support.MsSqlDataBaseTableDefaultQuery;
import com.github.yadickson.autodblq.db.table.constraint.defaults.support.OracleDataBaseTableDefaultQuery;
import com.github.yadickson.autodblq.db.table.constraint.defaults.support.PostgreSqlDataBaseTableDefaultQuery;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableDefaultQueryFactory implements DataBaseTableConstraintQueryFactory {

    @Override
    public DataBaseTableConstraintQuery apply(final Driver driver) {

        switch (driver) {
            case DB2:
                return new Db2DataBaseTableDefaultQuery();
            case ORACLE:
                return new OracleDataBaseTableDefaultQuery();
            case POSTGRESQL:
                return new PostgreSqlDataBaseTableDefaultQuery();
            case MSSQL:
                return new MsSqlDataBaseTableDefaultQuery();
        }

        throw new DataBaseTableDefaultQueryNotSupportException("Data base table default query [" + driver + "] not supported");
    }

}
