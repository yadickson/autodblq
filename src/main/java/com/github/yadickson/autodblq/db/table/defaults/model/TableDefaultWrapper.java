/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.defaults.model;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;

/**
 *
 * @author Yadickson Soto
 */
public final class TableDefaultWrapper extends TableBase {

    private final String columnName;
    private final String type;
    private final String value;

    public TableDefaultWrapper(final TableBase table, final String columnName, final String type, final String value) {
        super(table);
        this.columnName = columnName;
        this.type = type;
        this.value = value;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

}
