/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.checks.support;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintQuery;

/**
 *
 * @author Yadickson Soto
 */
public class PostgreSqlDataBaseTableCheckQuery implements DataBaseTableConstraintQuery {

    @Override
    public String get(final TableBase table, final boolean keepTypes) {
        return "(SELECT "
                + " tc.constraint_name as name, \n"
                + " col.column_name as column, \n"
                + " cc.check_clause as value \n"
                + "FROM information_schema.table_constraints tc \n"
                + "inner join information_schema.check_constraints cc on tc.constraint_schema = cc.constraint_schema and tc.constraint_name = cc.constraint_name \n"
                + "inner join pg_namespace nsp on nsp.nspname = cc.constraint_schema \n"
                + "inner join pg_constraint pgc on pgc.conname = cc.constraint_name and pgc.connamespace = nsp.oid and pgc.contype = 'c' \n"
                + "inner join information_schema.columns col on col.table_schema = tc.table_schema and col.table_name = tc.table_name and col.ordinal_position = ANY(pgc.conkey) \n"
                + "WHERE \n"
                + filterByName(table)
                + filterBySchema(table)
                + "ORDER BY col.ordinal_position) \n"
                + getConstrains(table, keepTypes);
    }

    private String getConstrains(final TableBase table, final boolean keepTypes) {

        if (keepTypes) {
            return  "";
        }

        return "UNION \n"
                + "(select \n"
                + "t.typname as name, \n"
                + "tc.column_name as column, \n"
                + "tc.column_name || ' in (''' || string_agg(e.enumlabel, ''', ''') || ''')' as value \n"
                + "FROM pg_type t \n"
                + "inner join pg_enum e ON e.enumtypid = t.oid \n"
                + "inner join pg_namespace n on t.typnamespace = n.oid \n"
                + "inner join information_schema.columns tc ON (tc.udt_name = t.typname AND tc.udt_schema = n.nspname) \n"
                + "WHERE \n"
                + filterByName(table)
                + filterBySchema(table)
                + "AND t.typtype = 'e' \n"
                + "group by t.typname, tc.column_name )";
    }

    private String filterByName(final TableBase table) {
        return " tc.table_name = '" + table.getName() + "' \n";
    }

    private String filterBySchema(final TableBase table) {

        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND tc.table_schema = '" + table.getSchema() + "' \n";
    }

}
