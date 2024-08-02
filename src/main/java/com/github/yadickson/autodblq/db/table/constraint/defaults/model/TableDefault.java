/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.defaults.model;

import com.github.yadickson.autodblq.db.table.property.DataBaseTableProperty;

/**
 *
 * @author Yadickson Soto
 */
public final class TableDefault extends DataBaseTableProperty {

    private final String value;

    public TableDefault(String name, String value)
    {
        super(name, value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
