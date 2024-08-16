/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.indexes.model;

import com.github.yadickson.autodblq.db.property.DataBaseProperty;

/**
 *
 * @author Yadickson Soto
 */
public final class Index extends DataBaseProperty {

    private final String realColumns;
    private final String columns;
    private final Boolean isUnique;

    public Index(String realName, String name, String newName, String columns, String realColumns, Boolean isUnique) {
        super(realName, name, newName);
        this.realColumns = realColumns;
        this.columns = columns;
        this.isUnique = isUnique;
    }

    public String getRealColumns() {
        return realColumns;
    }

    public String getColumns() {
        return columns;
    }

    public Boolean getIsUnique() {
        return isUnique;
    }
}
