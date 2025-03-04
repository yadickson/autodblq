/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.defaults.support;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintQuery;

/**
 *
 * @author Yadickson Soto
 */
public class MsSqlDataBaseTableDefaultQuery implements DataBaseTableConstraintQuery {

    @Override
    public String get(final TableBase table, final boolean keepTypes) {
        return "SELECT "
                + " c.name as 'column', \n"
                + " ct.name as 'columntype', \n"
                + " dc.definition as 'value' \n"
                + "FROM sys.tables t \n"
                + "INNER JOIN sys.default_constraints dc on t.object_id = dc.parent_object_id \n"
                + "INNER JOIN sys.columns c on dc.parent_object_id = c.object_id and c.column_id = dc.parent_column_id \n"
                + "inner join sys.types ct ON ct.user_type_id = c.user_type_id \n"
                + "WHERE \n"
                + filterByName(table)
                + filterBySchema(table)
                + "ORDER BY c.column_id ";
    }

    private String filterByName(final TableBase table) {
        return " t.name = '" + table.getName() + "' \n";
    }

    private String filterBySchema(final TableBase table) {

        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND t.schema_id = schema_id('" + table.getSchema() + "') \n";
    }

}
