/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.defaults;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.DataBaseGeneratorType;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetList;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintReader;
import com.github.yadickson.autodblq.db.table.constraint.defaults.model.TableDefaultBean;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableDefaultReader extends DataBaseTableConstraintReader<TableDefaultBean> {

    @Inject
    public DataBaseTableDefaultReader(
            final DataBaseTableDefaultQueryFactory dataBaseTableDefaultQueryFactory,
            final SqlExecuteToGetList sqlExecuteToGetList,
            final DataBaseTableDefaultMapper dataBaseTableDefaultMapper
    ) {
        super(DataBaseGeneratorType.TABLE_DEFAULTS, dataBaseTableDefaultQueryFactory, sqlExecuteToGetList, dataBaseTableDefaultMapper);
    }

    @Override
    protected Class<TableDefaultBean> getTypeClass() {
        return TableDefaultBean.class;
    }
}
