/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.uniques.model;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;

/**
 *
 * @author Yadickson Soto
 */
public final class TableUniqueWrapper extends TableBase {

    private final String constraintName;
    private final String columnNames;

    public TableUniqueWrapper(final TableBase table, final String constraintName, final String columnNames) {
        super(table);
        this.constraintName = constraintName;
        this.columnNames = columnNames;
    }

    public String getConstraintName() {
        return constraintName;
    }

    public String getColumnNames() {
        return columnNames;
    }

}
