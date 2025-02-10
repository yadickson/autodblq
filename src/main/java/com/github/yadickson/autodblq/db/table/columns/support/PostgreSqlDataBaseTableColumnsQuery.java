/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.columns.support;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.columns.DataBaseTableColumnsQuery;

/**
 *
 * @author Yadickson Soto
 */
public class PostgreSqlDataBaseTableColumnsQuery implements DataBaseTableColumnsQuery {

    @Override
    public String get(final TableBase table, boolean keepTypes) {
        return "SELECT \n"
                + " c.ordinal_position as position, \n"
                + " c.column_name as name, \n"
                + getColumns(keepTypes)
                + " case when c.is_nullable = 'YES' then 'true' else 'false' end as nullable, \n"
                + " c.numeric_precision as precision, \n"
                + " c.numeric_scale as scale, \n"
                + " d.description as remarks, \n"
                + " case when c.identity_generation is not null then 'true' else 'false' end as identity, \n"
                + " case when (c.identity_start is null or c.identity_start = '0') then '1' else c.identity_start end as startwith, \n"
                + " case when (c.identity_increment is null or c.identity_increment = '0') then '1' else c.identity_increment end as incrementby \n"
                + "FROM information_schema.columns c \n"
                + "inner join pg_catalog.pg_class c1 on c.table_name=c1.relname \n"
                + "inner join pg_catalog.pg_namespace n on c.table_schema=n.nspname and c1.relnamespace=n.oid \n"
                + "left join pg_catalog.pg_description d on d.objsubid=c.ordinal_position and d.objoid=c1.oid \n"
                + "WHERE \n"
                + filterByName(table)
                + filterBySchema(table)
                + "ORDER BY c.ordinal_position";
    }

    private String getColumns(boolean keepTypes) {

        if (keepTypes) {
            return  " c.udt_name as type, \n"
                    + " c.character_maximum_length as length, \n";
        }

        return " case when c.data_type = 'USER-DEFINED' AND (select COUNT(*) from pg_type t inner join pg_enum e ON e.enumtypid = t.oid where c.udt_name = t.typname AND c.udt_schema = n.nspname and t.typtype = 'e') != 0 then 'varchar' else c.udt_name end as type, \n"
                + " case when c.data_type = 'USER-DEFINED' then (select MAX(LENGTH(e.enumlabel)) from pg_type t inner join pg_enum e ON e.enumtypid = t.oid where c.udt_name = t.typname AND c.udt_schema = n.nspname and t.typtype = 'e') else c.character_maximum_length end as length, \n";
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
