/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.indexes;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintMapper;
import com.github.yadickson.autodblq.db.table.constraint.indexes.model.TableIndexBean;
import com.github.yadickson.autodblq.db.table.constraint.indexes.model.TableIndexWrapper;
import com.github.yadickson.autodblq.util.StringJoinUtil;
import com.github.yadickson.autodblq.util.StringToBooleanUtil;
import com.github.yadickson.autodblq.util.StringTrimUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableIndexMapper extends DataBaseTableConstraintMapper<TableIndexBean> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableIndexMapper.class);

    private final StringTrimUtil stringTrimUtil;
    private final StringJoinUtil stringJoinUtil;
    private final StringToBooleanUtil stringToBooleanUtil;

    @Inject
    public DataBaseTableIndexMapper(
            final StringTrimUtil stringTrimUtil,
            final StringJoinUtil stringJoinUtil,
            final StringToBooleanUtil stringToBooleanUtil
    ) {
        this.stringTrimUtil = stringTrimUtil;
        this.stringJoinUtil = stringJoinUtil;
        this.stringToBooleanUtil = stringToBooleanUtil;
    }

    @Override
    protected TableBase mapper(final TableBase tableBase, final TableIndexBean tableBean) {

        final String constraintName = stringTrimUtil.apply(tableBean.getName());
        final String constraintColumns = stringJoinUtil.apply(tableBean.getColumns());
        final Boolean constraintIsUnique = stringToBooleanUtil.apply(tableBean.getIsunique());

        LOGGER.debug("[DataBaseTableIndexMapper] Table Schema: " + tableBase.getSchema());
        LOGGER.debug("[DataBaseTableIndexMapper] Table Name: " + tableBase.getName());
        LOGGER.debug("[DataBaseTableIndexMapper] Table Constraint Name: " + constraintName);
        LOGGER.debug("[DataBaseTableIndexMapper] Table Constraint Columns: " + constraintColumns);
        LOGGER.debug("[DataBaseTableIndexMapper] Table Constraint Is Unique: " + constraintIsUnique);

        return new TableIndexWrapper(tableBase, constraintName, constraintColumns, constraintIsUnique);
    }

}
