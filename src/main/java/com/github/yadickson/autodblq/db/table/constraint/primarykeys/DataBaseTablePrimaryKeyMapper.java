/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.primarykeys;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintMapper;
import com.github.yadickson.autodblq.db.table.constraint.primarykeys.model.TablePrimaryKeyBean;
import com.github.yadickson.autodblq.db.table.constraint.primarykeys.model.TablePrimaryKeyWrapper;
import com.github.yadickson.autodblq.util.StringJoinUtil;
import com.github.yadickson.autodblq.util.StringTrimUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTablePrimaryKeyMapper extends DataBaseTableConstraintMapper<TablePrimaryKeyBean> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTablePrimaryKeyMapper.class);

    private final StringTrimUtil stringTrimUtil;
    private final StringJoinUtil stringJoinUtil;

    @Inject
    public DataBaseTablePrimaryKeyMapper(
            final StringTrimUtil stringTrimUtil,
            final StringJoinUtil stringJoinUtil
    ) {
        this.stringTrimUtil = stringTrimUtil;
        this.stringJoinUtil = stringJoinUtil;
    }

    @Override
    protected TableBase mapper(final TableBase tableBase, final TablePrimaryKeyBean tableBean) {

        final String constraintName = stringTrimUtil.apply(tableBean.getName());
        final String constraintColumns = stringJoinUtil.apply(tableBean.getColumns());

        LOGGER.debug("[DataBaseTablePrimaryKeyMapper] Table Schema: " + tableBase.getSchema());
        LOGGER.debug("[DataBaseTablePrimaryKeyMapper] Table Name: " + tableBase.getName());
        LOGGER.debug("[DataBaseTablePrimaryKeyMapper] Table Constraint Name: " + constraintName);
        LOGGER.debug("[DataBaseTablePrimaryKeyMapper] Table Constraint Columns: " + constraintColumns);

        return new TablePrimaryKeyWrapper(tableBase, constraintName, constraintColumns);
    }

}
