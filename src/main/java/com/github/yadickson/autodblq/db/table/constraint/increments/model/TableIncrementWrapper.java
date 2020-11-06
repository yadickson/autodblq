/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.increments.model;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;

/**
 *
 * @author Yadickson Soto
 */
public final class TableIncrementWrapper extends TableBase {

    private final String columnName;
    private final String type;
    private final String incrementBy;
    private final String startWith;

    public TableIncrementWrapper(final TableBase table, final String columnName, final String type, final String incrementBy, final String startWith) {
        super(table);
        this.columnName = columnName;
        this.type = type;
        this.incrementBy = incrementBy;
        this.startWith = startWith;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getType() {
        return type;
    }

    public String getIncrementBy() {
        return incrementBy;
    }

    public String getStartWith() {
        return startWith;
    }

}
