/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.increments.support;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.increments.DataBaseTableIncrementQuery;

/**
 *
 * @author Yadickson Soto
 */
public class Db2DataBaseTableIncrementQuery implements DataBaseTableIncrementQuery {

    @Override
    public String get(final TableBase table) {
        return "SELECT \n"
                + " t.colname column, \n "
                + " t.typename type,\n "
                + " 1 incrementby,\n"
                + " 1 startwith\n"
                + "FROM syscat.columns t\n"
                + "WHERE  \n"
                + filterByName(table)
                + filterBySchema(table)
                + " AND t.IDENTITY = 'Y'" + "\n"
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
