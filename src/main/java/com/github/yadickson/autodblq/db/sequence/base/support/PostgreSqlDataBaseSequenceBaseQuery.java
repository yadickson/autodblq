/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.sequence.base.support;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.sequence.base.DataBaseSequenceBaseQuery;
import com.github.yadickson.autodblq.db.support.SupportType;

/**
 *
 * @author Yadickson Soto
 */
public class PostgreSqlDataBaseSequenceBaseQuery extends SupportType implements DataBaseSequenceBaseQuery {

    private static final String SEPARATOR = "','";

    public PostgreSqlDataBaseSequenceBaseQuery() {
        super(Driver.POSTGRESQL);
    }

    @Override
    public String get(final List<String> filter) {
        return "select \n"
                + "s.sequence_schema as schema, \n"
                + "s.sequence_name as name, \n"
                + "s.data_type as type, \n"
                + "s.start_value as startvalue, \n"
                + "s.increment as incrementby, \n"
                + "case when s.cycle_option = 'NO' then 'false' else 'true' end as cycle \n"
                + "FROM information_schema.sequences s \n"
                + "where \n"
                + filterByNames(filter)
                + "order by 1 asc, 2 asc";
    }

    private String filterByNames(final List<String> filter) {
        return "s.sequence_schema not in('information_schema', 'pg_catalog') and s.sequence_name in ('" + StringUtils.join(filter, SEPARATOR) + "') \n";
    }

}
