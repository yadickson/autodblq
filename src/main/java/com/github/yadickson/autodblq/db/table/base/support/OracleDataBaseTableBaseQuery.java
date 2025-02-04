/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.base.support;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.support.SupportType;
import com.github.yadickson.autodblq.db.table.base.DataBaseTableBaseQuery;

/**
 *
 * @author Yadickson Soto
 */
public class OracleDataBaseTableBaseQuery extends SupportType implements DataBaseTableBaseQuery {

    private static final String SEPARATOR = "','";

    public OracleDataBaseTableBaseQuery() {
        super(Driver.ORACLE);
    }

    @Override
    public String get(final List<String> filter) {
        return "SELECT DISTINCT \n"
                + "t.owner schema, \n"
                + "t.table_name name, \n"
                + "c.comments remarks \n"
                + "FROM sys.all_tables t \n"
                + "LEFT JOIN sys.all_tab_comments c ON t.owner = c.owner AND t.table_name = c.table_name \n"
                + "WHERE \n"
                + filterByNames(filter)
                + "ORDER BY 1, 2";
    }

    private String filterByNames(final List<String> filter) {
        return "LOWER(t.table_name) in ('" + StringUtils.join(filter, SEPARATOR) + "') \n";
    }
}
