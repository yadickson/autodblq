/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.definitions.support;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.definitions.DataBaseTableDefinitionQuery;

/**
 *
 * @author Yadickson Soto
 */
public class Db2DataBaseTableDefinitionQuery implements DataBaseTableDefinitionQuery {

    @Override
    public String get(final TableBase table) {
        return "SELECT \n"
                + " t.colno POSITION, \n"
                + " t.colname name, \n"
                + " t.typename type, \n"
                + " t.length, \n"
                + " t.NULLS nullable, \n"
                + " t.scale, \n"
                + " t.remarks \n"
                + "FROM syscat.columns t \n"
                + "WHERE \n"
                + filterByName(table)
                + filterBySchema(table)
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
