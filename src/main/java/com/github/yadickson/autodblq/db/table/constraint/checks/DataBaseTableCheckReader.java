/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.checks;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.DataBaseGeneratorType;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetList;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintReader;
import com.github.yadickson.autodblq.db.table.constraint.checks.model.TableCheckBean;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableCheckReader extends DataBaseTableConstraintReader<TableCheckBean> {

    @Inject
    public DataBaseTableCheckReader(
            final DataBaseTableCheckQueryFactory dataBaseTableCheckQueryFactory,
            final SqlExecuteToGetList sqlExecuteToGetList,
            final DataBaseTableCheckMapper dataBaseTableCheckMapper
    ) {
        super(DataBaseGeneratorType.TABLE_CHECKS, dataBaseTableCheckQueryFactory, sqlExecuteToGetList, dataBaseTableCheckMapper);
    }

    @Override
    protected Class<TableCheckBean> getTypeClass() {
        return TableCheckBean.class;
    }
}
