/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.foreignkeys;

import com.github.yadickson.autodblq.db.table.constraint.foreignkeys.model.TableForeignKey;
import com.github.yadickson.autodblq.db.table.constraint.foreignkeys.model.TableForeignKeyBean;
import com.github.yadickson.autodblq.db.table.property.DataBaseTableProperty;
import com.github.yadickson.autodblq.util.StringJoinUtil;
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
public class DataBaseTableForeignKeyConstraintMapper implements Function<TableForeignKeyBean, DataBaseTableProperty> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableForeignKeyConstraintMapper.class);

    private final StringToLowerCaseUtil stringToLowerCaseUtil;
    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringJoinUtil stringJoinUtil;
    private final StringTrimUtil stringTrimUtil;

    @Inject
    public DataBaseTableForeignKeyConstraintMapper(StringToLowerCaseUtil stringToLowerCaseUtil, final StringToSnakeCaseUtil stringToSnakeCaseUtil, final StringJoinUtil stringJoinUtil, StringTrimUtil stringTrimUtil) {
        this.stringToLowerCaseUtil = stringToLowerCaseUtil;
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringJoinUtil = stringJoinUtil;
        this.stringTrimUtil = stringTrimUtil;
    }

    @Override
    public DataBaseTableProperty apply(final TableForeignKeyBean tableBean) {
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

        LOGGER.debug("[DataBaseTableForeignKeyMapper] ForeignKey Constraint Name: " + constraintRealName);
        LOGGER.debug("[DataBaseTableForeignKeyMapper] ForeignKey Constraint Columns: " + constraintRealColumns);
        LOGGER.debug("[DataBaseTableForeignKeyMapper] Reference Table Schema: " + constraintReferenceRealSchema);
        LOGGER.debug("[DataBaseTableForeignKeyMapper] Reference Table Name: " + constraintReferenceRealName);
        LOGGER.debug("[DataBaseTableForeignKeyMapper] Reference Table Columns: " + constraintReferenceRealColumns);

        return new TableForeignKey(constraintRealName, constraintName, constraintNewName, constraintRealColumns, constraintColumns, constraintReferenceRealSchema, constraintReferenceSchema, constraintReferenceRealName, constraintReferenceName, constraintReferenceRealColumns, constraintReferenceColumns);
    }
}
