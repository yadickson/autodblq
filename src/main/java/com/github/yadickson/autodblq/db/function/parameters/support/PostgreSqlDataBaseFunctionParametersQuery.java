/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.function.parameters.support;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.function.base.model.FunctionBase;
import com.github.yadickson.autodblq.db.function.parameters.DataBaseFunctionParametersQuery;

/**
 *
 * @author Yadickson Soto
 */
public class PostgreSqlDataBaseFunctionParametersQuery implements DataBaseFunctionParametersQuery {

    @Override
    public String get(final FunctionBase function) {
        return "SELECT \n"
                + " pp.ordinal_position as position, \n"
                + " pp.parameter_mode as mode, \n"
                + " pp.parameter_name as name, \n"
                + " case when pp.data_type = 'USER-DEFINED' AND (select COUNT(*) from pg_type t inner join pg_enum e ON e.enumtypid = t.oid where pp.udt_name = t.typname AND pp.udt_schema = n.nspname and t.typtype = 'e') != 0 then 'varchar' else pp.udt_name end as type, \n"
                + " case when pp.data_type = 'USER-DEFINED' then (select MAX(LENGTH(e.enumlabel)) from pg_type t inner join pg_enum e ON e.enumtypid = t.oid where pp.udt_name = t.typname and t.typtype = 'e') else pp.character_maximum_length end as length, \n"
                + " pp.numeric_precision as precision, \n"
                + " pp.numeric_scale as scale, \n"
                + " null as defaultvalue \n"
                + "FROM information_schema.routines r \n"
                + "inner JOIN information_schema.parameters pp ON r.specific_name=pp.specific_name \n"
                + "inner join pg_catalog.pg_proc p on p.proname = r.routine_name \n"
                + "inner JOIN pg_catalog.pg_namespace n ON n.oid = p.pronamespace \n"
                + "WHERE \n"
                + filterByName(function)
                + filterBySchema(function)
                + "ORDER BY pp.ordinal_position";
    }

    private String filterByName(final FunctionBase function) {
        return " r.routine_name = '" + function.getName() + "' \n";
    }

    private String filterBySchema(final FunctionBase function) {

        if (StringUtils.isEmpty(function.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND r.routine_schema = '" + function.getSchema() + "' \n";
    }

}
