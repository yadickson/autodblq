/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.uniques;

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
public class DataBaseTableUniqueReader extends DataBaseTableConstraintReader {

    @Inject
    public DataBaseTableUniqueReader(
            final DataBaseTableUniqueQueryFactory dataBaseTableUniqueQueryFactory,
            final SqlExecuteToGetListFactory sqlExecuteToGetListFactory,
            final DataBaseTableUniqueMapper dataBaseTableUniqueMapper
    ) {
        super(DataBaseGeneratorType.TABLE_UNIQUES, dataBaseTableUniqueQueryFactory, sqlExecuteToGetListFactory, dataBaseTableUniqueMapper);
    }

}
