/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.base.support;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.support.SupportType;
import com.github.yadickson.autodblq.db.table.base.DataBaseTableBaseQuery;

/**
 *
 * @author Yadickson Soto
 */
public class PostgreSqlDataBaseTableBaseQuery extends SupportType implements DataBaseTableBaseQuery {

    private static final String SEPARATOR = "','";

    public PostgreSqlDataBaseTableBaseQuery() {
        super(Driver.POSTGRESQL);
    }

    @Override
    public String get(final List<String> filter) {
        return "SELECT DISTINCT \n"
                + " t.table_schema as schema, \n"
                + " t.table_name as name, \n"
                + " pg_catalog.obj_description(pgc.oid, 'pg_class') as remarks \n"
                + "FROM information_schema.tables t \n"
                + "inner join pg_catalog.pg_class pgc on t.table_name=pgc.relname \n"
                + "inner join pg_catalog.pg_namespace n on t.table_schema=n.nspname and pgc.relnamespace=n.oid \n"
                + "left join pg_catalog.pg_description d on d.objsubid=pgc.oid \n"
                + "WHERE\n"
                + filterByNames(filter)
                + "order by 1 asc, 2 asc";
    }

    private String filterByNames(final List<String> filter) {
        return " t.table_schema not in('information_schema', 'pg_catalog') and t.table_name in ('" + StringUtils.join(filter, SEPARATOR) + "') \n";
    }

}
