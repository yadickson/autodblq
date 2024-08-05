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
public class Db2DataBaseTablePrimaryKeyQuery implements DataBaseTableConstraintQuery {

    @Override
    public String get(final TableBase table) {
        return "SELECT \n"
                + " t.name, \n"
                + " listagg(t.COLNAME, ',') columns \n"
                + "from \n"
                + "(\n"
                + " SELECT DISTINCT \n"
                + "   K.CONSTNAME name, \n"
                + "  TRIM(K.COLNAME) COLNAME \n"
                + " FROM syscat.keycoluse K \n"
                + " INNER JOIN syscat.tabconst c on c.tabschema = k.tabschema AND c.CONSTNAME = k.CONSTNAME AND c.TYPE = 'P' \n"
                + " WHERE  \n"
                + filterByName(table)
                + filterBySchema(table)
                + ") t\n"
                + "group BY t.name\n"
                + "order BY t.name asc";
    }

    private String filterByName(final TableBase table) {
        return "  LOWER(k.tabname) = '" + table.getName() + "' \n";
    }

    private String filterBySchema(final TableBase table) {
        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return "  AND LOWER(k.tabschema) = '" + table.getSchema() + "' \n";
    }

}
