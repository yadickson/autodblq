/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.defaults;

import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.property.DataBaseProperty;
import com.github.yadickson.autodblq.db.table.constraint.defaults.model.Default;
import com.github.yadickson.autodblq.db.table.constraint.defaults.model.TableDefaultBean;
import com.github.yadickson.autodblq.logger.LoggerManager;
import com.github.yadickson.autodblq.util.CleanStringValueUtil;
import com.github.yadickson.autodblq.util.StringToLowerCaseUtil;
import com.github.yadickson.autodblq.util.StringToSnakeCaseUtil;
import com.github.yadickson.autodblq.util.StringTrimUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableDefaultConstraintMapper implements Function<TableDefaultBean, DataBaseProperty> {

    private final LoggerManager loggerManager;
    private final CleanStringValueUtil cleanStringValueUtil;
    private final StringToLowerCaseUtil stringToLowerCaseUtil;
    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringTrimUtil stringTrimUtil;

    @Inject
    public DataBaseTableDefaultConstraintMapper(LoggerManager loggerManager, CleanStringValueUtil cleanStringValueUtil, StringToLowerCaseUtil stringToLowerCaseUtil, final StringToSnakeCaseUtil stringToSnakeCaseUtil, final StringTrimUtil stringTrimUtil) {
        this.loggerManager = loggerManager;
        this.cleanStringValueUtil = cleanStringValueUtil;
        this.stringToLowerCaseUtil = stringToLowerCaseUtil;
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringTrimUtil = stringTrimUtil;
    }

    @Override
    public DataBaseProperty apply(final TableDefaultBean tableBean) {
        final String constraintRealColumn = stringTrimUtil.apply(tableBean.getColumn());
        final String constraintColumn = stringToLowerCaseUtil.apply(tableBean.getColumn());
        final String constraintNewColumn = stringToSnakeCaseUtil.apply(tableBean.getColumn());
        final String constraintColumnType = stringToSnakeCaseUtil.apply(tableBean.getColumntype());
        final String constraintValue = cleanStringValueUtil.apply(tableBean.getValue());

        loggerManager.debug("[DataBaseTableDefaultMapper] Default Constraint Column: " + constraintRealColumn);
        loggerManager.debug("[DataBaseTableDefaultMapper] Default Constraint ColumnType: " + constraintColumnType);
        loggerManager.debug("[DataBaseTableDefaultMapper] Default Constraint Value: " + constraintValue);

        return new Default(constraintRealColumn, constraintColumn, constraintNewColumn, constraintColumnType, constraintValue);
    }
}
