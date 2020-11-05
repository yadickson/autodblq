/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.uniques;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.uniques.model.TableUniqueBean;
import com.github.yadickson.autodblq.db.table.uniques.model.TableUniqueWrapper;
import com.github.yadickson.autodblq.db.util.StringJoinUtil;
import com.github.yadickson.autodblq.db.util.StringTrimUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableUniqueMapper implements BiFunction<TableBase, List<TableUniqueBean>, List<TableBase>> {

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
    public List<TableBase> apply(final TableBase tableBase, final List<TableUniqueBean> tableUniqueBeans) {

        final List<TableBase> uniques = new ArrayList<>();

        for (TableUniqueBean tableUniqueBean : tableUniqueBeans) {
            TableBase unique = processUnique(tableBase, tableUniqueBean);
            uniques.add(unique);
        }

        return uniques;
    }

    private TableBase processUnique(final TableBase tableBase, final TableUniqueBean tableUniqueBean) {

        final String constraintName = stringTrimUtil.apply(tableUniqueBean.getName());
        final String constraintColumns = stringJoinUtil.apply(tableUniqueBean.getColumns());

        LOGGER.debug("[DataBaseTableUniqueMapper] Table Schema: " + tableBase.getSchema());
        LOGGER.debug("[DataBaseTableUniqueMapper] Table Name: " + tableBase.getName());
        LOGGER.debug("[DataBaseTableUniqueMapper] Table Constraint Name: " + constraintName);
        LOGGER.debug("[DataBaseTableUniqueMapper] Table Constraint Columns: " + constraintColumns);

        return new TableUniqueWrapper(tableBase, constraintName, constraintColumns);
    }

}
