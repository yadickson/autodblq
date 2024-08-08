/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.checks.support;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintQuery;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Yadickson Soto
 */
public class PostgreSqlDataBaseTableCheckQuery implements DataBaseTableConstraintQuery {

    @Override
    public String get(final TableBase table) {
        return "SELECT "
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
                + "ORDER BY col.ordinal_position ";
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
