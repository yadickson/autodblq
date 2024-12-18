/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.primarykeys;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.ParametersPlugin;
import com.github.yadickson.autodblq.db.DataBaseGeneratorType;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetList;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintReader;
import com.github.yadickson.autodblq.db.table.constraint.primarykeys.model.TablePrimaryKeyBean;
import com.github.yadickson.autodblq.logger.LoggerManager;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTablePrimaryKeyReader extends DataBaseTableConstraintReader<TablePrimaryKeyBean> {

    @Inject
    public DataBaseTablePrimaryKeyReader(
            final LoggerManager loggerManager,
            final ParametersPlugin parametersPlugin,
            final DataBaseTablePrimaryKeyQueryFactory dataBaseTablePrimaryKeyQueryFactory,
            final SqlExecuteToGetList sqlExecuteToGetList,
            final DataBaseTablePrimaryKeyMapper dataBaseTablePrimaryKeyMapper
    ) {
        super(DataBaseGeneratorType.TABLE_PRIMARY_KEYS, loggerManager, parametersPlugin, dataBaseTablePrimaryKeyQueryFactory, sqlExecuteToGetList, dataBaseTablePrimaryKeyMapper);
    }

    @Override
    protected Class<TablePrimaryKeyBean> getTypeClass() {
        return TablePrimaryKeyBean.class;
    }
}
