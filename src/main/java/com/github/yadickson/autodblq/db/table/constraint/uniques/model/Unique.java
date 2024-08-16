/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.uniques.model;

import com.github.yadickson.autodblq.db.property.DataBaseProperty;

/**
 *
 * @author Yadickson Soto
 */
public final class Unique extends DataBaseProperty {

    private final String realColumns;
    private final String columns;

    public Unique(String realName, String name, String newName, String realColumns, String columns) {
        super(realName, name, newName);
        this.realColumns = realColumns;
        this.columns = columns;
    }

    public String getRealColumns() {
        return realColumns;
    }

    public String getColumns() {
        return columns;
    }
}
