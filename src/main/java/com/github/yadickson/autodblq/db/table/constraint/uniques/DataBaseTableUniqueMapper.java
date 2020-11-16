/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.uniques;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintMapper;
import com.github.yadickson.autodblq.db.table.constraint.uniques.model.TableUniqueBean;
import com.github.yadickson.autodblq.db.table.constraint.uniques.model.TableUniqueWrapper;
import com.github.yadickson.autodblq.util.StringJoinUtil;
import com.github.yadickson.autodblq.util.StringTrimUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableUniqueMapper extends DataBaseTableConstraintMapper<TableUniqueBean> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableUniqueMapper.class);

    private final StringTrimUtil stringTrimUtil;
    private final StringJoinUtil stringJoinUtil;

    @Inject
    public DataBaseTableUniqueMapper(
            final StringTrimUtil stringTrimUtil,
            final StringJoinUtil stringJoinUtil
    ) {
        this.stringTrimUtil = stringTrimUtil;
        this.stringJoinUtil = stringJoinUtil;
    }

    @Override
    protected TableBase mapper(final TableBase tableBase, final TableUniqueBean tableBean) {

        final String constraintName = stringTrimUtil.apply(tableBean.getName());
        final String constraintColumns = stringJoinUtil.apply(tableBean.getColumns());

        LOGGER.debug("[DataBaseTableUniqueMapper] Table Schema: " + tableBase.getSchema());
        LOGGER.debug("[DataBaseTableUniqueMapper] Table Name: " + tableBase.getName());
        LOGGER.debug("[DataBaseTableUniqueMapper] Table Constraint Name: " + constraintName);
        LOGGER.debug("[DataBaseTableUniqueMapper] Table Constraint Columns: " + constraintColumns);

        return new TableUniqueWrapper(tableBase, constraintName, constraintColumns);
    }

}
