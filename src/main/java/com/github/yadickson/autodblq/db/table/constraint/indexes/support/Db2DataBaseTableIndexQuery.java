/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.indexes.support;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintQuery;

/**
 *
 * @author Yadickson Soto
 */
public class Db2DataBaseTableIndexQuery implements DataBaseTableConstraintQuery {

    @Override
    public String get(final TableBase table, final boolean keepTypes) {
        return "select \n"
                + " indname as name, \n"
                + " replace(trim(replace(replace(colnames,'-',' '),'+',' ')), ' ', ',') as columns, \n"
                + " DECODE(uniquerule, 'U', 'Y', 'N') isunique \n"
                + "from syscat.indexes \n"
                + "where \n"
                + " uniquerule != 'P' \n"
                + filterByName(table)
                + filterBySchema(table)
                + "order BY name asc";
    }

    private String filterByName(final TableBase table) {
        return " AND LOWER(tabname) = '" + table.getName() + "'\n ";
    }

    private String filterBySchema(final TableBase table) {
        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND LOWER(tabschema) = '" + table.getSchema() + "'\n ";
    }

}
