/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.indexes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.indexes.model.TableIndexBean;
import com.github.yadickson.autodblq.db.table.indexes.model.TableIndexWrapper;
import com.github.yadickson.autodblq.db.util.StringJoinUtil;
import com.github.yadickson.autodblq.db.util.StringToBooleanUtil;
import com.github.yadickson.autodblq.db.util.StringTrimUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableIndexMapper implements BiFunction<TableBase, List<TableIndexBean>, List<TableBase>> {

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
    public List<TableBase> apply(final TableBase tableBase, final List<TableIndexBean> tableIndexBeans) {

        final List<TableBase> indexes = new ArrayList<>();

        for (TableIndexBean tableIndexBean : tableIndexBeans) {
            TableBase index = processIndex(tableBase, tableIndexBean);
            indexes.add(index);
        }

        return indexes;
    }

    private TableBase processIndex(final TableBase tableBase, final TableIndexBean tableIndexBean) {

        final String constraintName = stringTrimUtil.apply(tableIndexBean.getName());
        final String constraintColumns = stringJoinUtil.apply(tableIndexBean.getColumns());
        final Boolean constraintIsUnique = stringToBooleanUtil.apply(tableIndexBean.getIsunique());

        LOGGER.debug("[DataBaseTableIndexMapper] Table Schema: " + tableBase.getSchema());
        LOGGER.debug("[DataBaseTableIndexMapper] Table Name: " + tableBase.getName());
        LOGGER.debug("[DataBaseTableIndexMapper] Table Constraint Name: " + constraintName);
        LOGGER.debug("[DataBaseTableIndexMapper] Table Constraint Columns: " + constraintColumns);
        LOGGER.debug("[DataBaseTableIndexMapper] Table Constraint Is Unique: " + constraintIsUnique);

        return new TableIndexWrapper(tableBase, constraintName, constraintColumns, constraintIsUnique);
    }

}
