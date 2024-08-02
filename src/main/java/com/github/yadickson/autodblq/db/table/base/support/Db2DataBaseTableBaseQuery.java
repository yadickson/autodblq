/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.base.support;

import java.util.List;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.support.SupportType;
import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.table.base.DataBaseTableBaseQuery;

/**
 *
 * @author Yadickson Soto
 */
public class Db2DataBaseTableBaseQuery extends SupportType implements DataBaseTableBaseQuery {

    private static final String SEPARATOR = "','";

    public Db2DataBaseTableBaseQuery() {
        super(Driver.DB2);
    }

    @Override
    public String get(final List<String> filter) {
        return "SELECT DISTINCT \n"
                + " tabschema schema, \n"
                + " tabname name, \n"
                + " remarks \n"
                + "FROM syscat.tables\n"
                + "WHERE\n"
                + " TYPE != 'V' \n"
                + filterByNames(filter)
                + "ORDER BY SCHEMA ASC, name asc";
    }

    private String filterByNames(final List<String> filter) {
        return " AND tabname in ('" + StringUtils.join(filter, SEPARATOR) + "') \n";
    }

}
