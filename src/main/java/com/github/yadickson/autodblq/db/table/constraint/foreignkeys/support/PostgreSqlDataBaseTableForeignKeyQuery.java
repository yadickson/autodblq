/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.foreignkeys.support;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintQuery;

/**
 *
 * @author Yadickson Soto
 */
public class PostgreSqlDataBaseTableForeignKeyQuery implements DataBaseTableConstraintQuery {

    @Override
    public String get(final TableBase table, final boolean keepTypes) {
        return "SELECT \n"
                + "t.name, \n"
                + "t.refname, \n"
                + "t.refschema, \n"
                + "array_to_string(array_agg(t.column), ' ') as columns, \n"
                + "array_to_string(array_agg(t.refcolumn), ' ') as refcolumns \n"
                + "from \n"
                + "(\n"
                + "SELECT distinct \n"
                + "tc.constraint_name as name, \n"
                + "ccu.table_name AS refname, \n"
                + "ccu.table_schema AS refschema, \n"
                + "kcu.column_name column, \n"
                + "ccu.column_name refcolumn \n"
                + "FROM information_schema.table_constraints AS tc \n"
                + "INNER JOIN information_schema.key_column_usage AS kcu ON tc.constraint_name = kcu.constraint_name AND tc.table_schema = kcu.table_schema \n"
                + "INNER JOIN information_schema.constraint_column_usage AS ccu ON ccu.constraint_name = tc.constraint_name \n"
                + " \n"
                + "WHERE tc.constraint_type = 'FOREIGN KEY' \n"
                + filterByName(table)
                + filterBySchema(table)
                + ") t \n"
                + "GROUP BY t.name, t.refname, t.refschema \n"
                + "ORDER BY 1 ";
    }

    private String filterByName(final TableBase table) {
        return " AND tc.table_name = '" + table.getName() + "' \n";
    }

    private String filterBySchema(final TableBase table) {

        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND tc.table_schema = '" + table.getSchema() + "' \n";
    }

}
