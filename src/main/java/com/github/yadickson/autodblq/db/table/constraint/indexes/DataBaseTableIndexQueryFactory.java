/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.indexes;

import javax.inject.Named;
import javax.inject.Singleton;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintQuery;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintQueryFactory;
import com.github.yadickson.autodblq.db.table.constraint.indexes.support.Db2DataBaseTableIndexQuery;
import com.github.yadickson.autodblq.db.table.constraint.indexes.support.MsSqlDataBaseTableIndexQuery;
import com.github.yadickson.autodblq.db.table.constraint.indexes.support.OracleDataBaseTableIndexQuery;
import com.github.yadickson.autodblq.db.table.constraint.indexes.support.PostgreSqlDataBaseTableIndexQuery;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableIndexQueryFactory implements DataBaseTableConstraintQueryFactory {

    @Override
    public DataBaseTableConstraintQuery apply(final Driver driver) {

        switch (driver) {
            case DB2:
                return new Db2DataBaseTableIndexQuery();
            case ORACLE:
                return new OracleDataBaseTableIndexQuery();
            case POSTGRESQL:
                return new PostgreSqlDataBaseTableIndexQuery();
            case MSSQL:
                return new MsSqlDataBaseTableIndexQuery();
        }

        throw new DataBaseTableIndexQueryNotSupportException("Data base table index query [" + driver + "] not supported");
    }

}
