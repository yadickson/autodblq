/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.foreignkeys;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.foreignkeys.model.TableForeignKeyBean;
import com.github.yadickson.autodblq.db.table.foreignkeys.model.TableForeignKeyWrapper;
import com.github.yadickson.autodblq.db.util.StringJoinUtil;
import com.github.yadickson.autodblq.db.util.StringTrimUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableForeignKeyMapper implements BiFunction<TableBase, List<TableForeignKeyBean>, List<TableBase>> {

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
    public List<TableBase> apply(TableBase tableBase, List<TableForeignKeyBean> tableForeignKeysBeans) {

        final List<TableBase> foreignKeys = new ArrayList<>();

        for (TableForeignKeyBean tableForeignKeyBean : tableForeignKeysBeans) {
            TableBase foreignKey = processForeignKey(tableBase, tableForeignKeyBean);
            foreignKeys.add(foreignKey);
        }

        return foreignKeys;
    }

    private TableBase processForeignKey(final TableBase table, TableForeignKeyBean tableForeignKeyBean) {
        final String constraintName = stringTrimUtil.apply(stringTrimUtil.apply(tableForeignKeyBean.getName()));
        final String constraintColumns = stringJoinUtil.apply(tableForeignKeyBean.getColumns());
        final String referenceSchema = stringTrimUtil.apply(tableForeignKeyBean.getRefschema());
        final String referenceName = stringTrimUtil.apply(tableForeignKeyBean.getRefname());
        final String referenceColumns = stringJoinUtil.apply(tableForeignKeyBean.getRefcolumns());

        LOGGER.debug("[DataBaseTableForeignKeyMapper] Table Schema: " + table.getSchema());
        LOGGER.debug("[DataBaseTableForeignKeyMapper] Table Name: " + table.getName());
        LOGGER.debug("[DataBaseTableForeignKeyMapper] Table Constraint Name: " + constraintName);
        LOGGER.debug("[DataBaseTableForeignKeyMapper] Table Constraint Columns: " + constraintColumns);
        LOGGER.debug("[DataBaseTableForeignKeyMapper] Ref Table Schema: " + referenceSchema);
        LOGGER.debug("[DataBaseTableForeignKeyMapper] Ref Table Name: " + referenceName);
        LOGGER.debug("[DataBaseTableForeignKeyMapper] Ref Table Columns: " + referenceColumns);

        return new TableForeignKeyWrapper(table, constraintName, constraintColumns, referenceSchema, referenceName, referenceColumns);
    }

}
