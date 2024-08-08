/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.checks.model;

import com.github.yadickson.autodblq.db.table.property.DataBaseTableProperty;

/**
 *
 * @author Yadickson Soto
 */
public final class TableCheck extends DataBaseTableProperty {

    private final String realColumnName;
    private final String newColumnName;
    private final String value;

    public TableCheck(String realName, String name, String newName, String realColumnName, String newColumnName, String columnValue)
    {
        super(realName, name, newName);
        this.realColumnName = realColumnName;
        this.newColumnName = newColumnName;
        this.value = columnValue;
    }

    public String getRealColumnName() {
        return realColumnName;
    }

    public String getNewColumnName() {
        return newColumnName;
    }

    public String getValue() {
        return value;
    }
}
