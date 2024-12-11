/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.columns.support;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.columns.DataBaseTableColumnsQuery;

/**
 *
 * @author Yadickson Soto
 */
public class Db2DataBaseTableColumnsQuery implements DataBaseTableColumnsQuery {

    @Override
    public String get(final TableBase table, boolean keepTypes) {
        return "SELECT \n"
                + " t.colno POSITION, \n"
                + " t.colname name, \n"
                + " t.typename type, \n"
                + " t.length, \n"
                + " CASE WHEN t.NULLS = 'Y' THEN 'true' ELSE 'false' end nullable, \n"
                + " t.scale, \n"
                + " t.remarks, \n"
                + " CASE WHEN t.IDENTITY = 'Y' THEN 'true' ELSE 'false' end identity,  \n"
                + " 1 startwith,  \n"
                + " 1 incrementby  \n"
                + "FROM syscat.columns t \n"
                + "WHERE \n"
                + filterByName(table)
                + filterBySchema(table)
                + "ORDER BY t.colno";
    }

    private String filterByName(final TableBase table) {
        return " LOWER(t.tabname) = '" + table.getName() + "' \n";
    }

    private String filterBySchema(final TableBase table) {

        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND LOWER(t.tabschema) = '" + table.getSchema() + "' \n";
    }

}
