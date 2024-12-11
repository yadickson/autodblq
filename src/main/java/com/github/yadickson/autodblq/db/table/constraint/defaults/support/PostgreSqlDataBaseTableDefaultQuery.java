/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.defaults.support;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintQuery;

/**
 *
 * @author Yadickson Soto
 */
public class PostgreSqlDataBaseTableDefaultQuery implements DataBaseTableConstraintQuery {

    @Override
    public String get(final TableBase table, final boolean keepTypes) {
        return "SELECT "
                + " col.column_name as column, \n"
                + " col.udt_name as columntype, \n"
                + " col.column_default as value \n"
                + "FROM information_schema.columns col \n"
                + "WHERE col.column_default is not null \n"
                + filterByName(table)
                + filterBySchema(table)
                + "ORDER BY col.ordinal_position ";
    }

    private String filterByName(final TableBase table) {
        return " AND col.table_name = '" + table.getName() + "' \n";
    }

    private String filterBySchema(final TableBase table) {

        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND col.table_schema = '" + table.getSchema() + "' \n";
    }

}
