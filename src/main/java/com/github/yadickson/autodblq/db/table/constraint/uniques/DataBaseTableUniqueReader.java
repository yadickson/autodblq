/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.uniques;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.ParametersPlugin;
import com.github.yadickson.autodblq.db.DataBaseGeneratorType;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetList;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintReader;
import com.github.yadickson.autodblq.db.table.constraint.uniques.model.TableUniqueBean;
import com.github.yadickson.autodblq.logger.LoggerManager;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableUniqueReader extends DataBaseTableConstraintReader<TableUniqueBean> {

    @Inject
    public DataBaseTableUniqueReader(
            final LoggerManager loggerManager,
            final ParametersPlugin parametersPlugin,
            final DataBaseTableUniqueQueryFactory dataBaseTableUniqueQueryFactory,
            final SqlExecuteToGetList sqlExecuteToGetList,
            final DataBaseTableUniqueMapper dataBaseTableUniqueMapper
    ) {
        super(DataBaseGeneratorType.TABLE_UNIQUES, loggerManager, parametersPlugin, dataBaseTableUniqueQueryFactory, sqlExecuteToGetList, dataBaseTableUniqueMapper);
    }

    @Override
    protected Class<TableUniqueBean> getTypeClass() {
        return TableUniqueBean.class;
    }
}
