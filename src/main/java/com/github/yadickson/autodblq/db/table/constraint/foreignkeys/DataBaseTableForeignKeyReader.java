/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.foreignkeys;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.ParametersPlugin;
import com.github.yadickson.autodblq.db.DataBaseGeneratorType;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetList;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintReader;
import com.github.yadickson.autodblq.db.table.constraint.foreignkeys.model.TableForeignKeyBean;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableForeignKeyReader extends DataBaseTableConstraintReader<TableForeignKeyBean> {

    @Inject
    public DataBaseTableForeignKeyReader(
            final ParametersPlugin parametersPlugin,
            final DataBaseTableForeignKeyQueryFactory dataBaseTableForeignKeyQueryFactory,
            final SqlExecuteToGetList sqlExecuteToGetList,
            final DataBaseTableForeignKeyMapper dataBaseTableForeignKeyMapper
    ) {
        super(DataBaseGeneratorType.TABLE_FOREIGN_KEYS, parametersPlugin, dataBaseTableForeignKeyQueryFactory, sqlExecuteToGetList, dataBaseTableForeignKeyMapper);
    }

    @Override
    protected Class<TableForeignKeyBean> getTypeClass() {
        return TableForeignKeyBean.class;
    }
}
