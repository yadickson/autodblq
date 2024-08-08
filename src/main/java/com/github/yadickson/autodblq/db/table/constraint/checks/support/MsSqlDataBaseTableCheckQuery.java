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
public class MsSqlDataBaseTableCheckQuery implements DataBaseTableConstraintQuery {

    @Override
    public String get(final TableBase table) {
        return "SELECT "
                + " chk.name, \n"
                + " col.name as 'column', \n"
                + " chk.definition as 'value' \n"
                + "FROM sys.check_constraints chk \n"
                + "inner join sys.columns col on chk.parent_object_id = col.object_id \n"
                + "inner join sys.tables t on t.object_id = col.object_id \n"
                + "inner join sys.tables st on chk.parent_object_id = st.object_id \n"
                + "WHERE \n"
                + "col.column_id = chk.parent_column_id \n"
                + filterByName(table)
                + filterBySchema(table)
                + "ORDER BY col.column_id ";
    }

    private String filterByName(final TableBase table) {
        return " AND t.name = '" + table.getName() + "' \n";
    }

    private String filterBySchema(final TableBase table) {

        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND t.schema_id = schema_id('" + table.getSchema() + "') \n";
    }

}
