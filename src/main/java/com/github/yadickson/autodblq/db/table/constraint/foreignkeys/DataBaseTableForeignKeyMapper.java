/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.foreignkeys;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintMapper;
import com.github.yadickson.autodblq.db.table.constraint.foreignkeys.model.TableForeignKeyBean;
import com.github.yadickson.autodblq.db.table.constraint.foreignkeys.model.TableForeignKeyWrapper;
import com.github.yadickson.autodblq.util.StringJoinUtil;
import com.github.yadickson.autodblq.util.StringTrimUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableForeignKeyMapper extends DataBaseTableConstraintMapper<TableForeignKeyBean> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableForeignKeyMapper.class);

    private final StringTrimUtil stringTrimUtil;
    private final StringJoinUtil stringJoinUtil;

    @Inject
    public DataBaseTableForeignKeyMapper(
            final StringTrimUtil stringTrimUtil,
            final StringJoinUtil stringJoinUtil
    ) {
        this.stringTrimUtil = stringTrimUtil;
        this.stringJoinUtil = stringJoinUtil;
    }

    @Override
    protected TableBase mapper(final TableBase tableBase, final TableForeignKeyBean tableBean) {
        final String constraintName = stringTrimUtil.apply(stringTrimUtil.apply(tableBean.getName()));
        final String constraintColumns = stringJoinUtil.apply(tableBean.getColumns());
        final String referenceSchema = stringTrimUtil.apply(tableBean.getRefschema());
        final String referenceName = stringTrimUtil.apply(tableBean.getRefname());
        final String referenceColumns = stringJoinUtil.apply(tableBean.getRefcolumns());

        LOGGER.debug("[DataBaseTableForeignKeyMapper] Table Schema: " + tableBase.getSchema());
        LOGGER.debug("[DataBaseTableForeignKeyMapper] Table Name: " + tableBase.getName());
        LOGGER.debug("[DataBaseTableForeignKeyMapper] Table Constraint Name: " + constraintName);
        LOGGER.debug("[DataBaseTableForeignKeyMapper] Table Constraint Columns: " + constraintColumns);
        LOGGER.debug("[DataBaseTableForeignKeyMapper] Ref Table Schema: " + referenceSchema);
        LOGGER.debug("[DataBaseTableForeignKeyMapper] Ref Table Name: " + referenceName);
        LOGGER.debug("[DataBaseTableForeignKeyMapper] Ref Table Columns: " + referenceColumns);

        return new TableForeignKeyWrapper(tableBase, constraintName, constraintColumns, referenceSchema, referenceName, referenceColumns);
    }

}
