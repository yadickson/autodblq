/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.uniques;

import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.property.DataBaseProperty;
import com.github.yadickson.autodblq.db.table.constraint.uniques.model.TableUniqueBean;
import com.github.yadickson.autodblq.db.table.constraint.uniques.model.Unique;
import com.github.yadickson.autodblq.logger.LoggerManager;
import com.github.yadickson.autodblq.util.StringJoinUtil;
import com.github.yadickson.autodblq.util.StringToLowerCaseUtil;
import com.github.yadickson.autodblq.util.StringToSnakeCaseUtil;
import com.github.yadickson.autodblq.util.StringTrimUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableUniqueConstraintMapper implements Function<TableUniqueBean, DataBaseProperty> {

    private final LoggerManager loggerManager;
    private final StringToLowerCaseUtil stringToLowerCaseUtil;
    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringJoinUtil stringJoinUtil;
    private final StringTrimUtil stringTrimUtil;

    @Inject
    public DataBaseTableUniqueConstraintMapper(LoggerManager loggerManager, StringToLowerCaseUtil stringToLowerCaseUtil, final StringToSnakeCaseUtil stringToSnakeCaseUtil, final StringJoinUtil stringJoinUtil, StringTrimUtil stringTrimUtil) {
        this.loggerManager = loggerManager;
        this.stringToLowerCaseUtil = stringToLowerCaseUtil;
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringJoinUtil = stringJoinUtil;
        this.stringTrimUtil = stringTrimUtil;
    }

    @Override
    public DataBaseProperty apply(final TableUniqueBean tableBean) {
        final String constraintRealName = stringTrimUtil.apply(tableBean.getName());
        final String constraintName = stringToLowerCaseUtil.apply(tableBean.getName());
        final String constraintNewName = stringToSnakeCaseUtil.apply(tableBean.getName());
        final String constraintRealColumns = stringJoinUtil.apply(stringTrimUtil.apply(tableBean.getColumns()));
        final String constraintColumns = stringJoinUtil.apply(stringToSnakeCaseUtil.apply(tableBean.getColumns()));

        loggerManager.debug("[DataBaseTableDefaultMapper] Unique Constraint Name: " + constraintRealName);
        loggerManager.debug("[DataBaseTableDefaultMapper] Unique Constraint Columns: " + constraintRealColumns);

        return new Unique(constraintRealName, constraintName, constraintNewName, constraintRealColumns, constraintColumns);
    }
}
