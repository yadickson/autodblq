/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.increments;

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
public class DataBaseTableIncrementReader extends DataBaseTableConstraintReader {

    @Inject
    public DataBaseTableIncrementReader(
            final DataBaseTableIncrementQueryFactory dataBaseTableIncrementQueryFactory,
            final SqlExecuteToGetListFactory sqlExecuteToGetListFactory,
            final DataBaseTableIncrementMapper dataBaseTableIncrementMapper
    ) {
        super(DataBaseGeneratorType.TABLE_INCREMENTS, dataBaseTableIncrementQueryFactory, sqlExecuteToGetListFactory, dataBaseTableIncrementMapper);
    }

}
