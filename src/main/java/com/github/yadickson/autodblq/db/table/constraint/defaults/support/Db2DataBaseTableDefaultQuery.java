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
public class Db2DataBaseTableDefaultQuery implements DataBaseTableConstraintQuery {

    @Override
    public String get(final TableBase table) {
        return "SELECT "
                + " t.colname column, \n"
                + " t.typename columntype, \n"
                + " t.default value \n"
                + "FROM syscat.columns t \n"
                + "WHERE \n"
                + filterByName(table)
                + filterBySchema(table)
                + " AND t.default IS NOT NULL \n"
                + "ORDER BY t.colno";
    }

    private String filterByName(final TableBase table) {
        return " t.tabname = '" + table.getName() + "' \n";
    }

    private String filterBySchema(final TableBase table) {

        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND t.tabschema = '" + table.getSchema() + "' \n";
    }

}
