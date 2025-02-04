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
public class OracleDataBaseTableCheckQuery implements DataBaseTableConstraintQuery {

    @Override
    public String get(final TableBase table, final boolean keepTypes) {

        return "select \n"
            + "a.constraint_name name, \n"
            + "c.column_name \"column\", \n"
            + "trim(extractvalue ( dbms_xmlgen.getxmltype ( 'select search_condition from sys.all_constraints aa inner join all_cons_columns cc on aa.owner = cc.owner and aa.constraint_name = cc.constraint_name and aa.table_name = cc.table_name where aa.constraint_name = ''' || a.constraint_name || ''' and cc.column_name = ''' || c.column_name || '''' ) , '//text()' )) value \n"
            + "from sys.all_constraints a \n"
            + "inner join sys.all_cons_columns c on a.owner = c.owner and a.constraint_name = c.constraint_name and a.table_name = c.table_name \n"
            + "where a.constraint_type='C' \n"
            + filterByName(table)
            + filterBySchema(table)
            + "ORDER BY c.position";
    }

    private String filterByName(final TableBase table) {
        return " and lower(a.table_name) = lower('" + table.getName() + "') \n";
    }

    private String filterBySchema(final TableBase table) {

        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND lower(a.owner) = lower('" + table.getSchema() + "') \n";
    }

}
