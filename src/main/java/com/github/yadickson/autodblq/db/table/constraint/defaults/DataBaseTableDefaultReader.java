/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.defaults;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.DataBaseGeneratorType;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetListFactory;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintReader;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableDefaultReader extends DataBaseTableConstraintReader {

    @Inject
    public DataBaseTableDefaultReader(
            final DataBaseTableDefaultQueryFactory dataBaseTableDefaultQueryFactory,
            final SqlExecuteToGetListFactory sqlExecuteToGetListFactory,
            final DataBaseTableDefaultMapper dataBaseTableDefaultMapper
    ) {
        super(DataBaseGeneratorType.TABLE_DEFAULTS, dataBaseTableDefaultQueryFactory, sqlExecuteToGetListFactory, dataBaseTableDefaultMapper);
    }

}
