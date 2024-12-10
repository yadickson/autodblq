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
public class MsSqlDataBaseTableBaseQuery extends SupportType implements DataBaseTableBaseQuery {

    private static final String SEPARATOR = "','";

    public MsSqlDataBaseTableBaseQuery() {
        super(Driver.MSSQL);
    }

    @Override
    public String get(final List<String> filter) {
        return "SELECT DISTINCT \n"
                + " schema_name(t.schema_id) as 'schema', \n"
                + " t.name as name, \n"
                + " td.value as remarks \n"
                + "FROM sys.tables t\n"
                + "left join sys.extended_properties td on td.major_id = t.object_id and td.name = 'MS_Description' and td.minor_id = 0 \n"
                + "WHERE\n"
                + " t.temporal_type = 0 \n"
                + filterByNames(filter)
                + "order by 1 asc, 2 asc";
    }

    private String filterByNames(final List<String> filter) {
        return " AND t.name in ('" + StringUtils.join(filter, SEPARATOR) + "') \n";
    }

}
