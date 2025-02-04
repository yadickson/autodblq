/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.columns.support;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.columns.DataBaseTableColumnsQuery;

/**
 *
 * @author Yadickson Soto
 */
public class OracleDataBaseTableColumnsQuery implements DataBaseTableColumnsQuery {

    @Override
    public String get(final TableBase table, boolean keepTypes) {
        return "SELECT DISTINCT \n"
                + "tc.column_id position, \n"
                + "tc.column_name name, \n"
                + "tc.data_type type, \n"
                + "DECODE (tc.data_type, 'VARCHAR2', tc.data_length, DECODE(tc.data_type, 'NUMBER', tc.data_precision, null)) length, \n"
                + "DECODE(tc.nullable, 'N', 'false', 'true') nullable, \n"
                + "tc.data_scale scale, \n"
                + "c.comments remarks, \n"
                + "'false' identity, \n"
                + "0 startwith, \n"
                + "0 incrementby \n"
                + "FROM sys.all_tab_columns tc \n"
                + "LEFT JOIN sys.all_col_comments c ON tc.owner = c.owner AND tc.table_name = c.table_name AND tc.column_name = c.column_name \n"
                + "WHERE \n"
                + filterByName(table)
                + filterBySchema(table)
                + "ORDER BY tc.column_id";
    }

    private String filterByName(final TableBase table) {
        return " LOWER(tc.table_name) = '" + table.getName() + "' \n";
    }

    private String filterBySchema(final TableBase table) {

        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND LOWER(tc.owner) = LOWER('" + table.getSchema() + "') \n";
    }
}
