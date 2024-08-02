/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.increments;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.table.constraint.defaults.model.TableDefaultBean;
import com.github.yadickson.autodblq.db.table.constraint.foreignkeys.model.TableForeignKeyBean;
import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintMapper;
import com.github.yadickson.autodblq.db.table.constraint.increments.model.TableIncrementBean;
import com.github.yadickson.autodblq.db.table.constraint.increments.model.TableIncrementWrapper;
import com.github.yadickson.autodblq.util.StringTrimUtil;

import java.util.List;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableIncrementMapper extends DataBaseTableConstraintMapper<TableIncrementBean> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableIncrementMapper.class);

    private final StringTrimUtil stringTrimUtil;

    @Inject
    public DataBaseTableIncrementMapper(final StringTrimUtil stringTrimUtil) {
        this.stringTrimUtil = stringTrimUtil;
    }

    @Override
    public TableBase apply(final TableBase tableBase, final List<TableIncrementBean> constraints) {
//        final String constraintColumn = stringTrimUtil.apply(tableBean.getColumn());
//        final String constraintType = stringTrimUtil.apply(tableBean.getType());
//        final String incrementBy = stringTrimUtil.apply(tableBean.getIncrementby());
//        final String startWith = stringTrimUtil.apply(tableBean.getStartwith());
//
//        LOGGER.debug("[DataBaseTableIncrementMapper] Table Schema: " + tableBase.getSchema());
//        LOGGER.debug("[DataBaseTableIncrementMapper] Table Name: " + tableBase.getName());
//        LOGGER.debug("[DataBaseTableIncrementMapper] Table Constraint Column: " + constraintColumn);
//        LOGGER.debug("[DataBaseTableIncrementMapper] Table Constraint Type: " + constraintType);
//        LOGGER.debug("[DataBaseTableIncrementMapper] Table Constraint Increment By: " + incrementBy);
//        LOGGER.debug("[DataBaseTableIncrementMapper] Table Constraint Start With: " + startWith);
//
//        return new TableIncrementWrapper(tableBase, constraintColumn, constraintType, incrementBy, startWith);

        return tableBase;
    }

}
