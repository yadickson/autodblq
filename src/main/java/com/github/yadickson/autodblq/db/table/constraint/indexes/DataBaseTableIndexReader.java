/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.indexes;

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
public class DataBaseTableIndexReader extends DataBaseTableConstraintReader {

    @Inject
    public DataBaseTableIndexReader(
            final DataBaseTableIndexQueryFactory dataBaseTableIndexQueryFactory,
            final SqlExecuteToGetListFactory sqlExecuteToGetListFactory,
            final DataBaseTableIndexMapper dataBaseTableIndexMapper
    ) {
        super(DataBaseGeneratorType.TABLE_INDEXES, dataBaseTableIndexQueryFactory, sqlExecuteToGetListFactory, dataBaseTableIndexMapper);
    }

}
