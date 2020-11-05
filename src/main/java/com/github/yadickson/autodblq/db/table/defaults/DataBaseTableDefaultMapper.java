/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.defaults;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.defaults.model.TableDefaultBean;
import com.github.yadickson.autodblq.db.table.defaults.model.TableDefaultWrapper;
import com.github.yadickson.autodblq.db.util.StringTrimUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableDefaultMapper implements BiFunction<TableBase, List<TableDefaultBean>, List<TableBase>> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableDefaultMapper.class);

    private final StringTrimUtil stringTrimUtil;

    @Inject
    public DataBaseTableDefaultMapper(
            final StringTrimUtil stringTrimUtil
    ) {
        this.stringTrimUtil = stringTrimUtil;
    }

    @Override
    public List<TableBase> apply(final TableBase tableBase, final List<TableDefaultBean> tableDefaultBeans) {

        final List<TableBase> defaults = new ArrayList<>();

        for (TableDefaultBean tableDefaultBean : tableDefaultBeans) {
            TableBase def = processDefault(tableBase, tableDefaultBean);
            defaults.add(def);
        }

        return defaults;
    }

    private TableBase processDefault(final TableBase tableBase, final TableDefaultBean tableDefaultBean) {

        final String constraintColumn = stringTrimUtil.apply(tableDefaultBean.getColumn());
        final String constraintType = stringTrimUtil.apply(tableDefaultBean.getType());
        final String constraintValue = stringTrimUtil.apply(tableDefaultBean.getValue());

        LOGGER.debug("[DataBaseTableDefaultMapper] Table Schema: " + tableBase.getSchema());
        LOGGER.debug("[DataBaseTableDefaultMapper] Table Name: " + tableBase.getName());
        LOGGER.debug("[DataBaseTableDefaultMapper] Table Constraint Column: " + constraintColumn);
        LOGGER.debug("[DataBaseTableDefaultMapper] Table Constraint Type: " + constraintType);
        LOGGER.debug("[DataBaseTableDefaultMapper] Table Constraint Value: " + constraintValue);

        return new TableDefaultWrapper(tableBase, constraintColumn, constraintType, constraintValue);
    }

}
