/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.defaults;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintMapper;
import com.github.yadickson.autodblq.db.table.constraint.defaults.model.TableDefaultBean;
import com.github.yadickson.autodblq.db.table.constraint.defaults.model.TableDefaultWrapper;
import com.github.yadickson.autodblq.util.StringTrimUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableDefaultMapper extends DataBaseTableConstraintMapper<TableDefaultBean> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableDefaultMapper.class);

    private final StringTrimUtil stringTrimUtil;

    @Inject
    public DataBaseTableDefaultMapper(final StringTrimUtil stringTrimUtil) {
        this.stringTrimUtil = stringTrimUtil;
    }

    @Override
    protected TableBase mapper(final TableBase tableBase, final TableDefaultBean tableBean) {

        final String constraintColumn = stringTrimUtil.apply(tableBean.getColumn());
        final String constraintType = stringTrimUtil.apply(tableBean.getType());
        final String constraintValue = stringTrimUtil.apply(tableBean.getValue());

        LOGGER.debug("[DataBaseTableDefaultMapper] Table Schema: " + tableBase.getSchema());
        LOGGER.debug("[DataBaseTableDefaultMapper] Table Name: " + tableBase.getName());
        LOGGER.debug("[DataBaseTableDefaultMapper] Table Constraint Column: " + constraintColumn);
        LOGGER.debug("[DataBaseTableDefaultMapper] Table Constraint Type: " + constraintType);
        LOGGER.debug("[DataBaseTableDefaultMapper] Table Constraint Value: " + constraintValue);

        return new TableDefaultWrapper(tableBase, constraintColumn, constraintType, constraintValue);
    }

}
