/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.primarykeys;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.primarykeys.model.TablePrimaryKeyBean;
import com.github.yadickson.autodblq.db.table.primarykeys.model.TablePrimaryKeyWrapper;
import com.github.yadickson.autodblq.db.util.StringJoinUtil;
import com.github.yadickson.autodblq.db.util.StringTrimUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTablePrimaryKeyMapper implements BiFunction<TableBase, List<TablePrimaryKeyBean>, List<TableBase>> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTablePrimaryKeyMapper.class);

    private final StringTrimUtil stringTrimUtil;
    private final StringJoinUtil stringJoinUtil;

    @Inject
    public DataBaseTablePrimaryKeyMapper(
            final StringTrimUtil stringTrimUtil,
            final StringJoinUtil stringJoinUtil
    ) {
        this.stringTrimUtil = stringTrimUtil;
        this.stringJoinUtil = stringJoinUtil;
    }

    @Override
    public List<TableBase> apply(final TableBase tableBase, final List<TablePrimaryKeyBean> tablePrimaryKeyBeans) {

        final List<TableBase> primarykeys = new ArrayList<>();

        for (TablePrimaryKeyBean tablePrimaryKeyBean : tablePrimaryKeyBeans) {
            TableBase primaryKey = processPrimaryKey(tableBase, tablePrimaryKeyBean);
            primarykeys.add(primaryKey);
        }

        return primarykeys;
    }

    private TableBase processPrimaryKey(final TableBase tableBase, final TablePrimaryKeyBean tablePrimaryKeyBean) {

        final String constraintName = stringTrimUtil.apply(tablePrimaryKeyBean.getName());
        final String constraintColumns = stringJoinUtil.apply(tablePrimaryKeyBean.getColumns());

        LOGGER.debug("[DataBaseTablePrimaryKeyMapper] Table Schema: " + tableBase.getSchema());
        LOGGER.debug("[DataBaseTablePrimaryKeyMapper] Table Name: " + tableBase.getName());
        LOGGER.debug("[DataBaseTablePrimaryKeyMapper] Table Constraint Name: " + constraintName);
        LOGGER.debug("[DataBaseTablePrimaryKeyMapper] Table Constraint Columns: " + constraintColumns);

        return new TablePrimaryKeyWrapper(tableBase, constraintName, constraintColumns);
    }

}
