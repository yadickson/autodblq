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
public class OracleDataBaseTableIndexQuery implements DataBaseTableConstraintQuery {

    @Override
    public String get(final TableBase table, final boolean keepTypes) {
        return "select \n"
                + " i.index_name name, \n"
                + " DECODE(i.uniqueness, 'UNIQUE', 'true', 'false') isunique, \n"
                + " replace(WMSYS.WM_CONCAT(c.column_name), ',', ' ') columns \n"
                + "from sys.all_INDEXES i \n"
                + "INNER JOIN sys.all_IND_COLUMNS c ON i.TABLE_NAME = c.TABLE_NAME and i.INDEX_NAME = c.INDEX_NAME \n"
                + " WHERE \n"
                + filterByName(table)
                + filterBySchema(table)
                + "GROUP BY i.index_name, i.uniqueness \n"
                + "order BY 1";
    }

    private String filterByName(final TableBase table) {
        return " LOWER(i.table_name) = lower('" + table.getName() + "') \n";
    }

    private String filterBySchema(final TableBase table) {
        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND LOWER(i.owner) = LOWER('" + table.getSchema() + "') \n";
    }
}
