/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.uniques.support;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintQuery;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Yadickson Soto
 */
public class MsSqlDataBaseTableUniqueQuery implements DataBaseTableConstraintQuery {

    @Override
    public String get(final TableBase table) {
        return "SELECT "
                + " tc.constraint_name as 'name', \n"
                + " (SELECT ' ' + cc.column_name FROM information_schema.constraint_column_usage cc WHERE tc.constraint_name = cc.constraint_name FOR XML PATH('')) as 'columns' \n"
                + "FROM information_schema.table_constraints tc \n"
                + "inner join information_schema.constraint_column_usage ccu on tc.constraint_name = ccu.constraint_name \n"
                + "WHERE tc.constraint_type = 'unique' \n"
                + filterByName(table)
                + filterBySchema(table)
                + "GROUP BY tc.constraint_name \n"
                + "ORDER BY 1 ";
    }

    private String filterByName(final TableBase table) {
        return " AND ccu.table_name = '" + table.getName() + "' \n";
    }

    private String filterBySchema(final TableBase table) {

        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND ccu.table_schema = '" + table.getSchema() + "' \n";
    }

}
