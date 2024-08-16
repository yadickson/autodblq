/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.function.base.support;

import java.util.List;

import com.github.yadickson.autodblq.db.function.base.DataBaseFunctionBaseQuery;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Yadickson Soto
 */
public class PostgreSqlDataBaseFunctionBaseQuery implements DataBaseFunctionBaseQuery {

    private static final String SEPARATOR = "','";

    @Override
    public String getFunctions(final List<String> filter) {
        return "select \n"
                + "r.routine_schema as schema, \n"
                + "r.routine_name as name, \n"
                + "case when pg_catalog.pg_get_function_result(p.oid) is not null then 'true' else 'false' end as isfunction, \n"
                + "r.routine_definition as content, \n"
                + "t.typname as returntype \n"
                + "from information_schema.routines r \n"
                + "inner join pg_catalog.pg_proc p on p.proname = r.routine_name \n"
                + "inner JOIN pg_catalog.pg_namespace n ON n.oid = p.pronamespace \n"
                + "inner JOIN pg_type t on p.prorettype = t.oid \n"
                + "WHERE pg_catalog.pg_function_is_visible(p.oid) \n"
                + "AND n.nspname <> 'pg_catalog' \n"
                + "AND n.nspname <> 'information_schema' \n"
                + filterByNames(filter);
    }

    private String filterByNames(final List<String> filter) {
        return " AND LOWER(r.routine_name) in ('" + StringUtils.join(filter, SEPARATOR) + "') \n";
    }

}
