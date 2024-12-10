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
public class MsSqlDataBaseFunctionBaseQuery implements DataBaseFunctionBaseQuery {

    private static final String SEPARATOR = "','";

    @Override
    public String getFunctions(final List<String> filter) {
        return "select \n"
                + "r.routine_schema as 'schema', \n"
                + "r.ROUTINE_NAME AS 'name', \n"
                + "case when r.ROUTINE_TYPE = 'FUNCTION' then 'true' else 'false' end 'isfunction', \n"
                + "r.routine_definition as 'content', \n"
                + "r.DATA_TYPE as 'returntype' \n"
                + "FROM INFORMATION_SCHEMA.ROUTINES r \n"
                + "LEFT JOIN INFORMATION_SCHEMA.ROUTINE_COLUMNS rc ON rc.TABLE_NAME = r.ROUTINE_NAME \n"
                + "WHERE \n"
                + filterByNames(filter);
    }

    private String filterByNames(final List<String> filter) {
        return " LOWER(r.ROUTINE_NAME) in ('" + StringUtils.join(filter, SEPARATOR) + "') \n";
    }

}
