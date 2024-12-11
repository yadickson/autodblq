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
public class PostgreSqlDataBaseTableIndexQuery implements DataBaseTableConstraintQuery {

    @Override
    public String get(final TableBase table, final boolean keepTypes) {
        return "select \n"
                + " i.indexrelid::regclass as name, \n"
                + " i.indisunique as isunique, \n"
                + " array_to_string(array_agg(a.attname), ' ') as columns \n"
                + "from information_schema.tables t \n"
                + "inner join pg_catalog.pg_class pgc on t.table_name=pgc.relname \n"
                + "inner join pg_catalog.pg_index i on pgc.oid = i.indrelid \n"
                + "inner join pg_catalog.pg_attribute a on a.attrelid = pgc.oid and a.attnum = ANY(i.indkey) \n"
                + "where \n"
                + " i.indisprimary = false \n"
                + " AND i.indisunique = false \n"
                + filterByName(table)
                + filterBySchema(table)
                + "GROUP BY i.indexrelid, i.indisunique \n"
                + "order BY 1 asc";
    }

    private String filterByName(final TableBase table) {
        return " AND t.table_name = '" + table.getName() + "'\n ";
    }

    private String filterBySchema(final TableBase table) {
        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND t.table_schema = '" + table.getSchema() + "'\n ";
    }

}
