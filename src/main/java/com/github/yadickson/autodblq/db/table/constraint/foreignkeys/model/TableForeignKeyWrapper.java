/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.foreignkeys.model;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;

/**
 *
 * @author Yadickson Soto
 */
public final class TableForeignKeyWrapper extends TableBase {

    private final String constraintName;
    private final String columnNames;
    private final String referenceSchema;
    private final String referenceName;
    private final String referenceColumnNames;

    public TableForeignKeyWrapper(final TableBase table, final String constraintName, final String columnNames, final String referenceSchema, final String referenceName, final String referenceColumnNames) {
        super(table);
        this.constraintName = constraintName;
        this.columnNames = columnNames;
        this.referenceSchema = referenceSchema;
        this.referenceName = referenceName;
        this.referenceColumnNames = referenceColumnNames;
    }

    public String getConstraintName() {
        return constraintName;
    }

    public String getColumnNames() {
        return columnNames;
    }

    public String getReferenceSchema() {
        return referenceSchema;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public String getReferenceColumnNames() {
        return referenceColumnNames;
    }

}
