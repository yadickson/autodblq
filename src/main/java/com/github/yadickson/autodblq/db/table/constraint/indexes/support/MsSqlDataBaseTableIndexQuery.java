/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.indexes.support;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintQuery;

/**
 *
 * @author Yadickson Soto
 */
public class MsSqlDataBaseTableIndexQuery implements DataBaseTableConstraintQuery {

    @Override
    public String get(final TableBase table) {
        return "select \n"
                + " ind.name, \n"
                + " case when ind.is_unique = 1 then 'true' else 'false' end 'isunique', \n"
                + " (SELECT ' ' + c.name FROM sys.columns c WHERE c.column_id = col.column_id AND c.object_id = col.object_id FOR XML PATH('')) as 'columns' \n"
                + "from sys.indexes ind \n"
                + "INNER JOIN sys.index_columns ic ON  ind.object_id = ic.object_id and ind.index_id = ic.index_id \n"
                + "INNER JOIN sys.columns col ON ic.object_id = col.object_id and ic.column_id = col.column_id \n"
                + "INNER JOIN sys.tables t ON ind.object_id = t.object_id \n"
                + "INNER JOIN sys.schemas sch ON t.schema_id = sch.schema_id \n"
                + "where \n"
                + " ind.is_primary_key = 0 \n"
                + " AND ind.is_unique = 0 \n"
                + " AND ind.is_unique_constraint = 0 \n"
                + " AND t.is_ms_shipped = 0 \n"
                + filterByName(table)
                + filterBySchema(table)
                + "GROUP BY ind.name, ind.is_unique, col.column_id, col.object_id \n"
                + "order BY ind.name asc";
    }

    private String filterByName(final TableBase table) {
        return " AND t.name = '" + table.getName() + "'\n ";
    }

    private String filterBySchema(final TableBase table) {
        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND sch.name = '" + table.getSchema() + "'\n ";
    }

}
