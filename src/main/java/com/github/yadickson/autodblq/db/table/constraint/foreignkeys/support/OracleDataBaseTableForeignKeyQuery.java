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
public class OracleDataBaseTableForeignKeyQuery implements DataBaseTableConstraintQuery {

    @Override
    public String get(final TableBase table, final boolean keepTypes) {
        return "select \n"
                + "b.constraint_name name, \n"
                + "c.table_name refname, \n"
                + "c.owner refschema, \n"
                + "a.delete_rule deleterule, \n"
                + "replace(WMSYS.WM_CONCAT(b.column_name), ',', ' ') columns, \n"
                + "replace(WMSYS.WM_CONCAT(c.column_name), ',', ' ') refcolumns \n"
                + "from sys.all_constraints a \n"
                + "INNER JOIN sys.all_cons_columns b ON b.constraint_name = a.constraint_name AND a.owner = b.owner \n"
                + "INNER JOIN sys.all_cons_columns c ON b.position = c.POSITION AND c.owner = a.r_owner AND c.constraint_name = a.r_constraint_name \n"
                + "where a.constraint_type = 'R' \n"
                + filterByName(table)
                + filterBySchema(table)
                + "GROUP BY b.constraint_name, c.table_name, c.owner, a.delete_rule";
    }

    private String filterByName(final TableBase table) {
        return " AND lower(b.table_name) = lower('" + table.getName() + "') \n";
    }

    private String filterBySchema(final TableBase table) {

        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND lower(b.owner) = lower('" + table.getSchema() + "') \n";
    }
}
