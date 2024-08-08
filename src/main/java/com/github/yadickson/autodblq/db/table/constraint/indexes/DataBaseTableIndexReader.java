/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.indexes;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.DataBaseGeneratorType;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetList;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintReader;
import com.github.yadickson.autodblq.db.table.constraint.indexes.model.TableIndexBean;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableIndexReader extends DataBaseTableConstraintReader<TableIndexBean> {

    @Inject
    public DataBaseTableIndexReader(
            final DataBaseTableIndexQueryFactory dataBaseTableIndexQueryFactory,
            final SqlExecuteToGetList sqlExecuteToGetList,
            final DataBaseTableIndexMapper dataBaseTableIndexMapper
    ) {
        super(DataBaseGeneratorType.TABLE_INDEXES, dataBaseTableIndexQueryFactory, sqlExecuteToGetList, dataBaseTableIndexMapper);
    }

    @Override
    protected Class<TableIndexBean> getTypeClass() {
        return TableIndexBean.class;
    }
}
