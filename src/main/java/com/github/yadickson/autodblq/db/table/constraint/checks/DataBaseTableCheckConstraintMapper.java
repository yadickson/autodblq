/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.checks;

import com.github.yadickson.autodblq.db.table.constraint.checks.model.TableCheck;
import com.github.yadickson.autodblq.db.table.constraint.checks.model.TableCheckBean;
import com.github.yadickson.autodblq.db.table.property.DataBaseTableProperty;
import com.github.yadickson.autodblq.util.CleanStringValueUtil;
import com.github.yadickson.autodblq.util.StringToLowerCaseUtil;
import com.github.yadickson.autodblq.util.StringToSnakeCaseUtil;
import com.github.yadickson.autodblq.util.StringTrimUtil;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.function.Function;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableCheckConstraintMapper implements Function<TableCheckBean, DataBaseTableProperty> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableCheckConstraintMapper.class);

    private final CleanStringValueUtil cleanStringValueUtil;
    private final StringToLowerCaseUtil stringToLowerCaseUtil;
    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringTrimUtil stringTrimUtil;

    @Inject
    public DataBaseTableCheckConstraintMapper(CleanStringValueUtil cleanStringValueUtil, StringToLowerCaseUtil stringToLowerCaseUtil, final StringToSnakeCaseUtil stringToSnakeCaseUtil, final StringTrimUtil stringTrimUtil) {
        this.cleanStringValueUtil = cleanStringValueUtil;
        this.stringToLowerCaseUtil = stringToLowerCaseUtil;
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringTrimUtil = stringTrimUtil;
    }

    @Override
    public DataBaseTableProperty apply(final TableCheckBean tableBean) {
        final String constraintRealName = stringTrimUtil.apply(tableBean.getName());
        final String constraintName = stringToLowerCaseUtil.apply(tableBean.getName());
        final String constraintNewName = stringToSnakeCaseUtil.apply(tableBean.getName());
        final String constraintRealColumn = stringTrimUtil.apply(tableBean.getColumn());
        final String constraintNewColumn = stringToSnakeCaseUtil.apply(tableBean.getColumn());
        final String constraintValue = cleanStringValueUtil.apply(tableBean.getValue());

        LOGGER.debug("[DataBaseTableCheckConstraintMapper] Default Constraint Name: " + constraintRealName);
        LOGGER.debug("[DataBaseTableCheckConstraintMapper] Default Constraint Column: " + constraintRealColumn);
        LOGGER.debug("[DataBaseTableCheckConstraintMapper] Default Constraint Value: " + constraintValue);

        return new TableCheck(constraintRealName, constraintName, constraintNewName, constraintRealColumn, constraintNewColumn, constraintValue);
    }
}
