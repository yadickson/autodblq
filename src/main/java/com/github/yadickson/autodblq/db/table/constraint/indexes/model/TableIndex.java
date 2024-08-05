/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.indexes.model;

import com.github.yadickson.autodblq.db.table.property.DataBaseTableProperty;

/**
 *
 * @author Yadickson Soto
 */
public final class TableIndex extends DataBaseTableProperty {

    private final String columns;
    private final Boolean isUnique;

    public TableIndex(String name, String columns, Boolean isUnique) {
        super(name, name);
        this.columns = columns;
        this.isUnique = isUnique;
    }

    public String getColumns() {
        return columns;
    }

    public Boolean getIsUnique() {
        return isUnique;
    }
}
