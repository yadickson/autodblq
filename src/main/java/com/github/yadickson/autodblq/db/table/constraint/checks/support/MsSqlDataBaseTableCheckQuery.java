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
public class MsSqlDataBaseTableCheckQuery implements DataBaseTableConstraintQuery {

    @Override
    public String get(final TableBase table) {
        return "SELECT "
                + " chk.name, \n"
                + " chk.definition as 'value', \n"
                + " (SELECT ' ' + c.name FROM sys.columns c WHERE t.object_id = c.object_id order by LEN(c.name) desc FOR XML PATH('')) as 'columns' \n"
                + "FROM sys.check_constraints chk \n"
                + "inner join sys.tables t on chk.parent_object_id = t.object_id \n"
                + "inner join sys.schemas AS s ON s.schema_id = t.schema_id \n"
                + "WHERE \n"
                + filterByName(table)
                + filterBySchema(table)
                + "GROUP BY chk.name, chk.definition, t.object_id \n"
                + "ORDER BY chk.name ";
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
