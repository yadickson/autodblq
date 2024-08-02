/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.foreignkeys.model;

import com.github.yadickson.autodblq.db.table.property.DataBaseTableProperty;

/**
 *
 * @author Yadickson Soto
 */
public final class TableForeignKey extends DataBaseTableProperty {

    private final String columns;
    private final String referenceSchema;
    private final String referenceName;
    private final String referenceColumns;

    public TableForeignKey(String name, String columns, String referenceSchema, String referenceName, String referenceColumns) {
        super(name);
        this.columns = columns;
        this.referenceSchema = referenceSchema;
        this.referenceName = referenceName;
        this.referenceColumns = referenceColumns;
    }

    public String getColumns() {
        return columns;
    }

    public String getReferenceSchema() {
        return referenceSchema;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public String getReferenceColumns() {
        return referenceColumns;
    }
}
