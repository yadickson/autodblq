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
        return "(with enum_types as (" +
                "select \n" +
                "n.nspname as schema, \n" +
                "t.typname as name, \n" +
                "e.enumlabel as label \n" +
                "from pg_type t\n" +
                "inner join pg_enum e ON e.enumtypid = t.oid \n" +
                "inner join pg_namespace n on t.typnamespace = n.oid \n" +
                "order by n.nspname, t.typname, e.enumlabel" +
                ")\n" +
                "select \n" +
                "et.schema, \n" +
                "et.name, \n" +
                "'enum' as type, \n" +
                "array_to_string(array_agg(''''||et.label||''''), ', ') as content \n" +
                "from enum_types et \n" +
                "where \n" +
                filterByNames(filter) +
                "group by et.schema, et.name, 3)";
    }

    private String filterByNames(final List<String> filter) {
        return " et.schema not in('information_schema', 'pg_catalog') and et.name in ('" + StringUtils.join(filter, SEPARATOR) + "') \n";
    }

}
