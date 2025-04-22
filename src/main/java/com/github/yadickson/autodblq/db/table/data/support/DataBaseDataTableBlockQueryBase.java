/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.data.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.property.DataBaseProperty;
import com.github.yadickson.autodblq.db.table.columns.DataBaseTableColumnsWrapper;
import com.github.yadickson.autodblq.db.table.data.DataBaseDataTableBlockQuery;

/**
 *
 * @author Yadickson Soto
 */
public abstract class DataBaseDataTableBlockQueryBase implements DataBaseDataTableBlockQuery {

    private static final String ROW_NUMBER = "ROW_NUMBER";

    @Override
    public String get(DataBaseTableColumnsWrapper table, Long page, Long blocks) {

        StringBuilder sql = new StringBuilder("SELECT ");

        List<String> mlist = new ArrayList<>();
        List<String> ilist = new ArrayList<>();

        for (DataBaseProperty field : table.getColumns()) {
            mlist.add("m." + field.getName());
            ilist.add(field.getName());
        }

        StringBuilder rowName = new StringBuilder(ROW_NUMBER);

        while (ilist.contains(ROW_NUMBER)) {
            rowName.append("_");
        }

        Long init = page * blocks;

        sql.append(StringUtils.join(mlist, ", "));
        sql.append(" FROM ( SELECT ROW_NUMBER() OVER (ORDER BY ");
        sql.append(ilist.get(0));
        sql.append(" ) AS ");
        sql.append(rowName);
        sql.append(", ");
        sql.append(StringUtils.join(ilist, ", "));
        sql.append(" FROM ");
        sql.append(table.getFullName());
        sql.append(" ) m");
        sql.append(" WHERE m.");
        sql.append(rowName);
        sql.append(" >= ");
        sql.append(init + 1);
        sql.append(" AND m.");
        sql.append(rowName);
        sql.append(" < ");
        sql.append(init + blocks + 1);
        sql.append(" order by ");
        sql.append(ilist.get(0));

        return sql.toString();
    }

}
