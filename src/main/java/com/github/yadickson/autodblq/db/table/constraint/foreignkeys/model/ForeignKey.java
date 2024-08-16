/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.foreignkeys.model;

import com.github.yadickson.autodblq.db.property.DataBaseProperty;

/**
 *
 * @author Yadickson Soto
 */
public final class ForeignKey extends DataBaseProperty {

    private final String realColumns;
    private final String columns;
    private final String referenceRealSchema;
    private final String referenceSchema;
    private final String referenceRealName;
    private final String referenceName;
    private final String referenceRealColumns;
    private final String referenceColumns;

    public ForeignKey(String realName, String name, String newName, String realColumns, String columns, String referenceRealSchema, String referenceSchema, String referenceRealName, String referenceName, String referenceRealColumns, String referenceColumns) {
        super(realName, name, newName);
        this.realColumns = realColumns;
        this.columns = columns;
        this.referenceRealSchema = referenceRealSchema;
        this.referenceSchema = referenceSchema;
        this.referenceRealName = referenceRealName;
        this.referenceName = referenceName;
        this.referenceRealColumns = referenceRealColumns;
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

    public String getRealColumns() {
        return realColumns;
    }

    public String getReferenceRealSchema() {
        return referenceRealSchema;
    }

    public String getReferenceRealName() {
        return referenceRealName;
    }

    public String getReferenceRealColumns() {
        return referenceRealColumns;
    }
}
