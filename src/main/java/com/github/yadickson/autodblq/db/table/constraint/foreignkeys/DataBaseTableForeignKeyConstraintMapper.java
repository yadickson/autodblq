/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.foreignkeys;

import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.property.DataBaseProperty;
import com.github.yadickson.autodblq.db.table.constraint.foreignkeys.model.ForeignKey;
import com.github.yadickson.autodblq.db.table.constraint.foreignkeys.model.TableForeignKeyBean;
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
public class DataBaseTableForeignKeyConstraintMapper implements Function<TableForeignKeyBean, DataBaseProperty> {

    private final LoggerManager loggerManager;
    private final StringToLowerCaseUtil stringToLowerCaseUtil;
    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringJoinUtil stringJoinUtil;
    private final StringTrimUtil stringTrimUtil;

    @Inject
    public DataBaseTableForeignKeyConstraintMapper(LoggerManager loggerManager, StringToLowerCaseUtil stringToLowerCaseUtil, final StringToSnakeCaseUtil stringToSnakeCaseUtil, final StringJoinUtil stringJoinUtil, StringTrimUtil stringTrimUtil) {
        this.loggerManager = loggerManager;
        this.stringToLowerCaseUtil = stringToLowerCaseUtil;
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringJoinUtil = stringJoinUtil;
        this.stringTrimUtil = stringTrimUtil;
    }

    @Override
    public DataBaseProperty apply(final TableForeignKeyBean tableBean) {
        final String constraintRealName = stringTrimUtil.apply(tableBean.getName());
        final String constraintName = stringToLowerCaseUtil.apply(tableBean.getName());
        final String constraintNewName = stringToSnakeCaseUtil.apply(tableBean.getName());
        final String constraintRealColumns = stringJoinUtil.apply(stringTrimUtil.apply(tableBean.getColumns()));
        final String constraintColumns = stringJoinUtil.apply(stringToSnakeCaseUtil.apply(tableBean.getColumns()));
        final String constraintReferenceRealSchema = stringTrimUtil.apply(tableBean.getRefschema());
        final String constraintReferenceSchema = stringToSnakeCaseUtil.apply(tableBean.getRefschema());
        final String constraintReferenceRealName = stringTrimUtil.apply(tableBean.getRefname());
        final String constraintReferenceName = stringToSnakeCaseUtil.apply(tableBean.getRefname());
        final String constraintReferenceRealColumns = stringJoinUtil.apply(stringTrimUtil.apply(tableBean.getRefcolumns()));
        final String constraintReferenceColumns = stringJoinUtil.apply(stringToSnakeCaseUtil.apply(tableBean.getRefcolumns()));

        loggerManager.debug("[DataBaseTableForeignKeyMapper] ForeignKey Constraint Name: " + constraintRealName);
        loggerManager.debug("[DataBaseTableForeignKeyMapper] ForeignKey Constraint Columns: " + constraintRealColumns);
        loggerManager.debug("[DataBaseTableForeignKeyMapper] Reference Table Schema: " + constraintReferenceRealSchema);
        loggerManager.debug("[DataBaseTableForeignKeyMapper] Reference Table Name: " + constraintReferenceRealName);
        loggerManager.debug("[DataBaseTableForeignKeyMapper] Reference Table Columns: " + constraintReferenceRealColumns);

        return new ForeignKey(constraintRealName, constraintName, constraintNewName, constraintRealColumns, constraintColumns, constraintReferenceRealSchema, constraintReferenceSchema, constraintReferenceRealName, constraintReferenceName, constraintReferenceRealColumns, constraintReferenceColumns);
    }
}
