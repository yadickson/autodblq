/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.checks;

import com.github.yadickson.autodblq.db.DataBaseGeneratorType;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetListFactory;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintReader;

import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableCheckReader extends DataBaseTableConstraintReader {

    @Inject
    public DataBaseTableCheckReader(
            final DataBaseTableCheckQueryFactory dataBaseTableCheckQueryFactory,
            final SqlExecuteToGetListFactory sqlExecuteToGetListFactory,
            final DataBaseTableCheckMapper dataBaseTableCheckMapper
    ) {
        super(DataBaseGeneratorType.TABLE_CHECKS, dataBaseTableCheckQueryFactory, sqlExecuteToGetListFactory, dataBaseTableCheckMapper);
    }

}
