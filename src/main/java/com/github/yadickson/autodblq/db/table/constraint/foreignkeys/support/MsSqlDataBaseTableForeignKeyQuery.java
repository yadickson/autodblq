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
public class MsSqlDataBaseTableForeignKeyQuery implements DataBaseTableConstraintQuery {

    @Override
    public String get(final TableBase table, final boolean keepTypes) {
        return "SELECT "
                + " obj.name as 'name', \n"
                + " tab2.name AS 'refname', \n"
                + " sch2.name as 'refschema', \n"
                + " (SELECT ' ' + c.name FROM sys.columns c WHERE c.column_id = col1.column_id AND c.object_id = col1.object_id FOR XML PATH('')) as 'columns', \n"
                + " (SELECT ' ' + c.name FROM sys.columns c WHERE c.column_id = col2.column_id AND c.object_id = col2.object_id FOR XML PATH('')) as 'refcolumns' \n"
                + "FROM sys.foreign_key_columns fkc \n"
                + "INNER JOIN sys.objects obj ON obj.object_id = fkc.constraint_object_id \n"
                + "INNER JOIN sys.tables tab1 ON tab1.object_id = fkc.parent_object_id \n"
                + "INNER JOIN sys.schemas sch ON tab1.schema_id = sch.schema_id \n"
                + "INNER JOIN sys.columns col1 ON col1.column_id = parent_column_id AND col1.object_id = tab1.object_id \n"
                + "INNER JOIN sys.tables tab2 ON tab2.object_id = fkc.referenced_object_id \n"
                + "INNER JOIN sys.schemas sch2 ON tab2.schema_id = sch2.schema_id \n"
                + "INNER JOIN sys.columns col2 ON col2.column_id = referenced_column_id AND col2.object_id = tab2.object_id \n"
                + " \n"
                + "WHERE \n"
                + filterByName(table)
                + filterBySchema(table)
                + "GROUP BY obj.name, tab2.name, sch2.name, col1.column_id, col1.object_id, col2.column_id, col2.object_id \n"
                + "ORDER BY 1 ";
    }

    private String filterByName(final TableBase table) {
        return " tab1.name = '" + table.getName() + "' \n";
    }

    private String filterBySchema(final TableBase table) {

        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND sch.name = '" + table.getSchema() + "' \n";
    }

}
