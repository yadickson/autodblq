/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.columns.support;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.columns.DataBaseTableColumnsQuery;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Yadickson Soto
 */
public class PostgreSqlDataBaseTableColumnsQuery implements DataBaseTableColumnsQuery {

    @Override
    public String get(final TableBase table) {
        return "SELECT \n"
                + " c.ordinal_position as position, \n"
                + " c.column_name as name, \n"
                + " c.udt_name as type, \n"
                + " c.character_maximum_length as length, \n"
                + " case when c.is_nullable = 'YES' then 'true' else 'false' end as nullable, \n"
                + " c.numeric_precision as precision, \n"
                + " c.numeric_scale as scale, \n"
                + " d.description as remarks, \n"
                + " case when c.identity_generation is not null then 'true' else 'false' end as identity, \n"
                + " c.identity_start as startwith, \n"
                + " c.identity_increment incrementby \n"
                + "FROM information_schema.columns c \n"
                + "inner join pg_catalog.pg_class c1 on c.table_name=c1.relname \n"
                + "inner join pg_catalog.pg_namespace n on c.table_schema=n.nspname and c1.relnamespace=n.oid \n"
                + "left join pg_catalog.pg_description d on d.objsubid=c.ordinal_position and d.objoid=c1.oid \n"
                + "WHERE \n"
                + filterByName(table)
                + filterBySchema(table)
                + "ORDER BY c.ordinal_position";
    }

    private String filterByName(final TableBase table) {
        return " c.table_name = '" + table.getName() + "' \n";
    }

    private String filterBySchema(final TableBase table) {

        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND c.table_schema = '" + table.getSchema() + "' \n";
    }

}
