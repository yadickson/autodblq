/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.uniques;

import javax.inject.Named;
import javax.inject.Singleton;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintQuery;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintQueryFactory;
import com.github.yadickson.autodblq.db.table.constraint.uniques.support.Db2DataBaseTableUniqueQuery;
import com.github.yadickson.autodblq.db.table.constraint.uniques.support.MsSqlDataBaseTableUniqueQuery;
import com.github.yadickson.autodblq.db.table.constraint.uniques.support.OracleDataBaseTableUniqueQuery;
import com.github.yadickson.autodblq.db.table.constraint.uniques.support.PostgreSqlDataBaseTableUniqueQuery;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableUniqueQueryFactory implements DataBaseTableConstraintQueryFactory {

    @Override
    public DataBaseTableConstraintQuery apply(final Driver driver) {

        switch (driver) {
            case DB2:
                return new Db2DataBaseTableUniqueQuery();
            case ORACLE:
                return new OracleDataBaseTableUniqueQuery();
            case POSTGRESQL:
                return new PostgreSqlDataBaseTableUniqueQuery();
            case MSSQL:
                return new MsSqlDataBaseTableUniqueQuery();
        }

        throw new DataBaseTableUniqueQueryNotSupportException("Data base table unique query [" + driver + "] not supported");
    }

}
