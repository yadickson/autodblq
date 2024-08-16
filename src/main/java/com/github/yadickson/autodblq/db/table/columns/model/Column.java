/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.columns.model;

import com.github.yadickson.autodblq.db.property.DataBaseProperty;

/**
 *
 * @author Yadickson Soto
 */
public class Column extends DataBaseProperty {

    private final Integer position;
    private final Boolean nullable;
    private final String precision;
    private final String scale;
    private final String remarks;
    private final Boolean identity;
    private final Integer startWith;
    private final Integer incrementBy;

    public Column(final String realName, final String name, String newName, final String type, final Integer position, final Integer length, final String precision, final String scale, final String remarks, final Boolean nullable, Boolean identity, Integer startWith, Integer incrementBy) {
        super(realName, name, newName, type, length);
        this.position = position;
        this.precision = precision;
        this.scale = scale;
        this.nullable = nullable;
        this.remarks = remarks;
        this.identity = identity;
        this.startWith = startWith;
        this.incrementBy = incrementBy;
    }

    public Integer getPosition() {
        return position;
    }

    public String getScale() {
        return scale;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getPrecision() {
        return precision;
    }

    public Boolean getIdentity() {
        return identity;
    }

    public Integer getStartWith() {
        return startWith;
    }

    public Integer getIncrementBy() {
        return incrementBy;
    }
}
