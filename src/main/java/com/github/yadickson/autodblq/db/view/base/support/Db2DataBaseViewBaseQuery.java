/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.view.base.support;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.view.base.DataBaseViewBaseQuery;

/**
 *
 * @author Yadickson Soto
 */
public class Db2DataBaseViewBaseQuery implements DataBaseViewBaseQuery {

    private static final String SEPARATOR = "','";

    @Override
    public String get(final List<String> filter) {
        return "SELECT \n"
                + " v.viewschema schema, \n"
                + " v.viewname name, \n"
                + " v.text content \n"
                + "FROM SYSCAT.VIEWS V \n"
                + "WHERE \n"
                + filterByNames(filter)
                + "ORDER BY v.viewschema ASC, v.viewname asc";
    }

    private String filterByNames(final List<String> filter) {
        return " LOWER(v.viewname) in ('" + StringUtils.join(filter, SEPARATOR) + "') \n";
    }

}
