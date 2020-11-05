/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.increments;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.increments.model.TableIncrementBean;
import com.github.yadickson.autodblq.db.table.increments.model.TableIncrementWrapper;
import com.github.yadickson.autodblq.db.util.StringTrimUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableIncrementMapper implements BiFunction<TableBase, List<TableIncrementBean>, List<TableBase>> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableIncrementMapper.class);

    private final StringTrimUtil stringTrimUtil;

    @Inject
    public DataBaseTableIncrementMapper(final StringTrimUtil stringTrimUtil) {
        this.stringTrimUtil = stringTrimUtil;
    }

    @Override
    public List<TableBase> apply(final TableBase tableBase, final List<TableIncrementBean> tableIncrementBeans) {

        final List<TableBase> increments = new ArrayList<>();

        for (TableIncrementBean tableIncrementBean : tableIncrementBeans) {
            TableBase increment = processIncrement(tableBase, tableIncrementBean);
            increments.add(increment);
        }

        return increments;
    }

    private TableBase processIncrement(final TableBase tableBase, final TableIncrementBean tableIncrementBean) {

        final String constraintColumn = stringTrimUtil.apply(tableIncrementBean.getColumn());
        final String constraintType = stringTrimUtil.apply(tableIncrementBean.getType());
        final String incrementBy = stringTrimUtil.apply(tableIncrementBean.getIncrementby());
        final String startWith = stringTrimUtil.apply(tableIncrementBean.getStartwith());

        LOGGER.debug("[DataBaseTableIncrementMapper] Table Schema: " + tableBase.getSchema());
        LOGGER.debug("[DataBaseTableIncrementMapper] Table Name: " + tableBase.getName());
        LOGGER.debug("[DataBaseTableIncrementMapper] Table Constraint Column: " + constraintColumn);
        LOGGER.debug("[DataBaseTableIncrementMapper] Table Constraint Type: " + constraintType);
        LOGGER.debug("[DataBaseTableIncrementMapper] Table Constraint Increment By: " + incrementBy);
        LOGGER.debug("[DataBaseTableIncrementMapper] Table Constraint Start With: " + startWith);

        return new TableIncrementWrapper(tableBase, constraintColumn, constraintType, incrementBy, startWith);
    }

}
