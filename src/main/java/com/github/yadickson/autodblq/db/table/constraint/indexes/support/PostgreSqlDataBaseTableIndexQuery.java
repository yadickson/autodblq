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
        return "select t.name, t.isunique, array_to_string(array_agg(t.\"column\"), ' ') as columns \n" +
                "from (\n" +
                "select\n" +
                "i.relname::regclass as name,\n" +
                "ix.indisunique as isunique,\n" +
                "a.attname as \"column\" \n" +
                "from pg_class t\n" +
                "inner join pg_namespace insp on insp.oid = t.relnamespace \n" +
                "inner join pg_index ix on t.oid = ix.indrelid\n" +
                "inner join pg_class i on i.oid = ix.indexrelid\n" +
                "inner join pg_attribute a on a.attrelid = t.oid and a.attnum = ANY(ix.indkey)\n" +
                "where t.relkind = 'r'\n" +
                "and ix.indisprimary = false \n" +
                filterByName(table) +
                filterBySchema(table) +
                "order by i.relname, ix.indisunique, ix.indkey\n" +
                ") t\n" +
                "group by t.name, t.isunique";
    }

    private String filterByName(final TableBase table) {
        return " AND t.relname = '" + table.getName() + "'\n ";
    }

    private String filterBySchema(final TableBase table) {
        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND insp.nspname = '" + table.getSchema() + "'\n ";
    }

}
