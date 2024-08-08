/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.view.base.support;

import java.util.List;

import com.github.yadickson.autodblq.db.view.base.DataBaseViewBaseQuery;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Yadickson Soto
 */
public class PostgreSqlDataBaseViewBaseQuery implements DataBaseViewBaseQuery {

    private static final String SEPARATOR = "','";

    @Override
    public String get(final List<String> filter) {
        return "SELECT \n"
                + " v.table_schema AS schema, \n"
                + " v.table_name AS name, \n"
                + " 'create view ' || v.table_name || ' as ' || v.view_definition as content \n"
                + "FROM information_schema.views v \n"
                + "WHERE \n"
                + filterByNames(filter)
                + "order by 1, 2";
    }

    private String filterByNames(final List<String> filter) {
        return " v.table_schema not in('information_schema', 'pg_catalog') and v.table_name in ('" + StringUtils.join(filter, SEPARATOR) + "') \n";
    }

}
