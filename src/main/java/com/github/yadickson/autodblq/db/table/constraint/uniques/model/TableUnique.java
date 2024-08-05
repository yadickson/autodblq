/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.uniques.model;

import com.github.yadickson.autodblq.db.table.property.DataBaseTableProperty;

/**
 *
 * @author Yadickson Soto
 */
public final class TableUnique extends DataBaseTableProperty {

    private final String columns;

    public TableUnique(String name, String columns) {
        super(name, name);
        this.columns = columns;
    }

    public String getColumns() {
        return columns;
    }
}
