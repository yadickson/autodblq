/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.foreignkeys.support;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.foreignkeys.DataBaseTableForeignKeyQuery;

/**
 *
 * @author Yadickson Soto
 */
public class Db2DataBaseTableForeignKeyQuery implements DataBaseTableForeignKeyQuery {

    @Override
    public String get(final TableBase table) {
        return "select \n"
                + " ref.constname name, \n"
                + " TRIM(REF.FK_COLNAMES) columns, \n"
                + " ref.reftabschema refschema, \n"
                + " ref.reftabname refname, \n"
                + " TRIM(REF.PK_COLNAMES) refcolumns \n"
                + "from syscat.references REF \n"
                + " WHERE \n"
                + filterByName(table)
                + filterBySchema(table)
                + "order BY name asc";
    }

    private String filterByName(final TableBase table) {
        return " ref.tabname = '" + table.getName() + "' \n";
    }

    private String filterBySchema(final TableBase table) {

        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND ref.tabschema = '" + table.getSchema() + "' \n";
    }

}
