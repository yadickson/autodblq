/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.primarykeys.support;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintQuery;

/**
 *
 * @author Yadickson Soto
 */
public class PostgreSqlDataBaseTablePrimaryKeyQuery implements DataBaseTableConstraintQuery {

    @Override
    public String get(final TableBase table) {
        return "SELECT "
                + " c.conname as name, \n"
                + " array_to_string(array_agg(a.attname ORDER BY k.n), ' ') as columns \n"
                + "FROM pg_catalog.pg_constraint c \n"
                + "CROSS JOIN LATERAL unnest(c.conkey) WITH ORDINALITY AS k(c,n) \n"
                + "JOIN pg_attribute AS a ON a.attnum = k.c AND a.attrelid = c.conrelid \n"
                + "WHERE c.contype = 'p' \n"
                + filterByName(table)
                + filterBySchema(table)
                + "GROUP BY c.oid, c.conrelid, c.conname \n"
                + "ORDER BY 1 ";
    }

    private String filterByName(final TableBase table) {
        return " AND c.conrelid::regclass = '" + table.getName() + "'::regclass \n";
    }

    private String filterBySchema(final TableBase table) {

        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND c.connamespace::regnamespace = '" + table.getSchema() + "'::regnamespace \n";
    }

}
