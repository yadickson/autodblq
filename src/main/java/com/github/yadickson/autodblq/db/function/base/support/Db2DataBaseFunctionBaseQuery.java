/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.function.base.support;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.function.base.DataBaseFunctionBaseQuery;

/**
 *
 * @author Yadickson Soto
 */
public class Db2DataBaseFunctionBaseQuery implements DataBaseFunctionBaseQuery {

    private static final String SEPARATOR = "','";

    @Override
    public String getFunctions(final List<String> filter) {
        return "SELECT \n"
                + " FUNCSCHEMA schema, \n"
                + " FUNCNAME name, \n"
                + " BODY content,"
                + " 'true' ISFUNCTION \n"
                + "FROM syscat.FUNCTIONS \n"
                + "WHERE \n"
                + filterFunctionByNames(filter)
                + "ORDER BY FUNCSCHEMA ASC, FUNCNAME asc";
    }

    @Override
    public String getProcedures(final List<String> filter) {
        return "select \n"
                + " PROCSCHEMA schema, \n"
                + " PROCNAME name, \n"
                + " TEXT content, \n"
                + " 'false' ISFUNCTION \n"
                + "from SYSCAT.PROCEDURES \n"
                + "WHERE \n"
                + filterProceduresByNames(filter)
                + "ORDER BY PROCSCHEMA ASC, PROCNAME asc";
    }

    private String filterFunctionByNames(final List<String> filter) {
        return " FUNCNAME in ('" + StringUtils.join(filter, SEPARATOR) + "') \n";
    }

    private String filterProceduresByNames(final List<String> filter) {
        return " PROCNAME in ('" + StringUtils.join(filter, SEPARATOR) + "') \n";
    }

}
