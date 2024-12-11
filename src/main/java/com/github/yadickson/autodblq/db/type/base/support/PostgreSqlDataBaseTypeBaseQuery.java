/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.type.base.support;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.support.SupportType;
import com.github.yadickson.autodblq.db.type.base.DataBaseTypeBaseQuery;

/**
 *
 * @author Yadickson Soto
 */
public class PostgreSqlDataBaseTypeBaseQuery extends SupportType implements DataBaseTypeBaseQuery {

    private static final String SEPARATOR = "','";

    public PostgreSqlDataBaseTypeBaseQuery() {
        super(Driver.POSTGRESQL);
    }

    @Override
    public String get(final List<String> filter) {
        return "(select \n" +
                " t.typname as name,\n" +
                " n.nspname as schema, \n" +
                " 'enum' as type, \n" +
                " array_to_string(array_agg(''''||e.enumlabel||''''), ', ') as content \n" +
                " from pg_type t\n" +
                "inner join pg_enum e ON e.enumtypid = t.oid \n" +
                "inner join pg_namespace n on t.typnamespace = n.oid \n" +
                " where \n" +
                filterByNames(filter) +
                " group by t.typname, n.nspname, 3" +
                " order by 1 asc, 2 asc)";
    }

    private String filterByNames(final List<String> filter) {
        return " n.nspname not in('information_schema', 'pg_catalog') and t.typname in ('" + StringUtils.join(filter, SEPARATOR) + "') \n";
    }

}
