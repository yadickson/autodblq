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

    private final String realColumnNames;
    private final String newColumnNames;
    private final String value;

    public TableCheck(String realName, String name, String newName, String realColumnNames, String newColumnNames, String columnValue)
    {
        super(realName, name, newName);
        this.realColumnNames = realColumnNames;
        this.newColumnNames = newColumnNames;
        this.value = columnValue;
    }

    public String getRealColumnNames() {
        return realColumnNames;
    }

    public String getNewColumnNames() {
        return newColumnNames;
    }

    public String getValue() {
        return value;
    }
}
