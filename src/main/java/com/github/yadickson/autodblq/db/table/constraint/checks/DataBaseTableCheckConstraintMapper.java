/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.checks;

import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.property.DataBaseProperty;
import com.github.yadickson.autodblq.db.table.constraint.checks.model.Check;
import com.github.yadickson.autodblq.db.table.constraint.checks.model.TableCheckBean;
import com.github.yadickson.autodblq.util.*;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableCheckConstraintMapper implements Function<TableCheckBean, DataBaseProperty> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableCheckConstraintMapper.class);

    private final CleanStringValueUtil cleanStringValueUtil;
    private final StringToContentUtil stringToContentUtil;
    private final StringToLowerCaseUtil stringToLowerCaseUtil;
    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringTrimUtil stringTrimUtil;

    @Inject
    public DataBaseTableCheckConstraintMapper(CleanStringValueUtil cleanStringValueUtil, StringToContentUtil stringToContentUtil, StringToLowerCaseUtil stringToLowerCaseUtil, final StringToSnakeCaseUtil stringToSnakeCaseUtil, final StringTrimUtil stringTrimUtil) {
        this.cleanStringValueUtil = cleanStringValueUtil;
        this.stringToContentUtil = stringToContentUtil;
        this.stringToLowerCaseUtil = stringToLowerCaseUtil;
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringTrimUtil = stringTrimUtil;
    }

    @Override
    public DataBaseProperty apply(final TableCheckBean tableBean) {
        final String constraintRealName = stringTrimUtil.apply(tableBean.getName());
        final String constraintName = stringToLowerCaseUtil.apply(tableBean.getName());
        final String constraintNewName = stringToSnakeCaseUtil.apply(tableBean.getName());
        final String constraintRealColumns = stringTrimUtil.apply(tableBean.getColumns());
        final String constraintNewColumns = stringToSnakeCaseUtil.apply(tableBean.getColumns());
        final String constraintValue = cleanStringValueUtil.apply(stringToContentUtil.apply(cleanStringValueUtil.apply(tableBean.getValue())));

        LOGGER.debug("[DataBaseTableCheckConstraintMapper] Default Constraint Name: " + constraintRealName);
        LOGGER.debug("[DataBaseTableCheckConstraintMapper] Default Constraint Column: " + constraintRealColumns);
        LOGGER.debug("[DataBaseTableCheckConstraintMapper] Default Constraint Value: " + constraintValue);

        return new Check(constraintRealName, constraintName, constraintNewName, constraintRealColumns, constraintNewColumns, constraintValue);
    }
}
