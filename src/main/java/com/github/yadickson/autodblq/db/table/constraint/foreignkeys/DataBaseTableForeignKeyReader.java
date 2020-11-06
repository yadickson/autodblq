/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.foreignkeys;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.github.yadickson.autodblq.db.DataBaseGeneratorType;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetListFactory;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintReader;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableForeignKeyReader extends DataBaseTableConstraintReader {

    @Inject
    public DataBaseTableForeignKeyReader(
            final DataBaseTableForeignKeyQueryFactory dataBaseTableForeignKeyQueryFactory,
            final SqlExecuteToGetListFactory sqlExecuteToGetListFactory,
            final DataBaseTableForeignKeyMapper dataBaseTableForeignKeyMapper
    ) {
        super(DataBaseGeneratorType.TABLE_FOREIGN_KEYS, dataBaseTableForeignKeyQueryFactory, sqlExecuteToGetListFactory, dataBaseTableForeignKeyMapper);
    }

}
