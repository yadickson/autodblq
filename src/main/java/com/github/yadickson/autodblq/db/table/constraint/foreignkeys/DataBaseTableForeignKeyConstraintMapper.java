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
import com.github.yadickson.autodblq.util.StringToSnakeCaseUtil;
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

    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringJoinUtil stringJoinUtil;

    @Inject
    public DataBaseTableForeignKeyConstraintMapper(final StringToSnakeCaseUtil stringToSnakeCaseUtil, final StringJoinUtil stringJoinUtil) {
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringJoinUtil = stringJoinUtil;
    }

    @Override
    public DataBaseTableProperty apply(final TableForeignKeyBean tableBean) {
        final String constraintName = stringToSnakeCaseUtil.apply(tableBean.getName());
        final String constraintColumns = stringJoinUtil.apply(stringToSnakeCaseUtil.apply(tableBean.getColumns()));
        final String constraintReferenceSchema = stringToSnakeCaseUtil.apply(tableBean.getRefschema());
        final String constraintReferenceName = stringToSnakeCaseUtil.apply(tableBean.getRefname());
        final String constraintReferenceColumns = stringJoinUtil.apply(stringToSnakeCaseUtil.apply(tableBean.getRefcolumns()));

        LOGGER.debug("[DataBaseTableForeignKeyMapper] ForeignKey Constraint Name: " + constraintName);
        LOGGER.debug("[DataBaseTableForeignKeyMapper] ForeignKey Constraint Columns: " + constraintColumns);
        LOGGER.debug("[DataBaseTableForeignKeyMapper] Reference Table Schema: " + constraintReferenceSchema);
        LOGGER.debug("[DataBaseTableForeignKeyMapper] Reference Table Name: " + constraintReferenceName);
        LOGGER.debug("[DataBaseTableForeignKeyMapper] Reference Table Columns: " + constraintReferenceColumns);

        return new TableForeignKey(constraintName, constraintColumns, constraintReferenceSchema, constraintReferenceName, constraintReferenceColumns);
    }
}
