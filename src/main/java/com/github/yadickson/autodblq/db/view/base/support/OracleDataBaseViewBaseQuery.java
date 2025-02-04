/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.view.base.support;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.view.base.DataBaseViewBaseQuery;

/**
 *
 * @author Yadickson Soto
 */
public class OracleDataBaseViewBaseQuery implements DataBaseViewBaseQuery {

    private static final String SEPARATOR = "','";

    @Override
    public String get(final List<String> filter) {
        return "SELECT \n"
                + "schema, \n"
                + "name, \n"
                + "'create view ' || name || ' (' || replace(WMSYS.WM_CONCAT(column_name), ',', ', ') || ') as ' || text content \n"
                + "FROM \n"
                + "( \n"
                + "SELECT \n"
                + "v.owner schema, \n"
                + "v.view_name name, \n"
                + "col.column_name, \n"
                + "trim(extractvalue ( dbms_xmlgen.getxmltype ( 'select text from sys.ALL_VIEWS vv where vv.owner = ''' || v.owner || ''' and vv.view_name = ''' || v.view_name || '''' ) , '//text()' )) text \n"
                + "FROM sys.ALL_VIEWS v \n"
                + "inner join sys.all_tab_columns col on col.owner = v.owner and col.table_name = v.view_name \n"
                + "WHERE \n"
                + filterByNames(filter)
                + "order by 1, 2, col.column_id \n"
                + ") \n"
                + "GROUP BY SCHEMA, name, text";
    }

    private String filterByNames(final List<String> filter) {
        return " lower(v.view_name) in ('" + StringUtils.join(filter, SEPARATOR) + "') \n";
    }
}
