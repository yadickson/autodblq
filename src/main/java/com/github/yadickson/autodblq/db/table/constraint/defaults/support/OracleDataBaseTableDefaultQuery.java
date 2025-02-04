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
public class OracleDataBaseTableDefaultQuery implements DataBaseTableConstraintQuery {

    @Override
    public String get(final TableBase table, final boolean keepTypes) {
        return "SELECT "
                + " t.column_name \"column\", \n"
                + " t.data_type columntype, \n"
                + " trim(extractvalue ( dbms_xmlgen.getxmltype ( 'select data_default from sys.all_tab_columns where owner = ''' || t.owner || ''' and table_name = ''' || t.table_name || ''' and column_name = ''' || t.column_name || '''' ) , '//text()' )) value \n"
                + "FROM sys.all_TAB_COLUMNS t \n"
                + "WHERE t.data_default IS NOT NULL \n"
                + filterByName(table)
                + filterBySchema(table)
                + " ORDER BY 1";
    }

    private String filterByName(final TableBase table) {
        return " AND LOWER(t.table_name) = LOWER('" + table.getName() + "') \n";
    }

    private String filterBySchema(final TableBase table) {

        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND LOWER(t.owner) = LOWER('" + table.getSchema() + "') \n";
    }

}
