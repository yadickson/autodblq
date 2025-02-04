/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.primarykeys.support;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintQuery;

/**
 *
 * @author Yadickson Soto
 */
public class OracleDataBaseTablePrimaryKeyQuery implements DataBaseTableConstraintQuery {

    @Override
    public String get(final TableBase table, final boolean keepTypes) {
        return "SELECT \n"
                + " cc.constraint_name name, \n"
                + " replace(WMSYS.WM_CONCAT(cc.column_name), ',', ' ') columns \n"
                + "from sys.all_cons_columns cc \n"
                + "INNER JOIN sys.all_constraints c ON cc.constraint_name = c.constraint_name AND c.constraint_type = 'P' \n"
                + "WHERE  \n"
                + filterByName(table)
                + filterBySchema(table)
                + "group BY cc.constraint_name\n"
                + "order BY 1 asc";
    }

    private String filterByName(final TableBase table) {
        return " lower(cc.table_name) = lower('" + table.getName() + "') \n";
    }

    private String filterBySchema(final TableBase table) {

        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND lower(cc.owner) = lower('" + table.getSchema() + "') \n";
    }
}
