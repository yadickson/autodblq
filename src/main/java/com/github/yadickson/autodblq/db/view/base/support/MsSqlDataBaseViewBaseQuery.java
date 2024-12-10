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
public class MsSqlDataBaseViewBaseQuery implements DataBaseViewBaseQuery {

    private static final String SEPARATOR = "','";

    @Override
    public String get(final List<String> filter) {
        return "SELECT \n"
                + " SCHEMA_NAME(v.schema_id) AS 'schema', \n"
                + " v.name AS 'name', \n"
                + " m.definition as 'content' \n"
                + "FROM sys.views v \n"
                + "inner join sys.sql_modules m on v.object_id = m.object_id \n"
                + "WHERE \n"
                + filterByNames(filter)
                + "order by 1, 2";
    }

    private String filterByNames(final List<String> filter) {
        return " v.name in ('" + StringUtils.join(filter, SEPARATOR) + "') \n";
    }

}
