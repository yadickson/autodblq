/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.definitions.model;

/**
 *
 * @author Yadickson Soto
 */
public final class TableColumn  {

    private final String name;
    private final String type;
    private final Integer position;
    private final Integer length;
    private final Boolean nullable;
    private final String precision;
    private final String scale;
    private final String remarks;
    private final String defaultValue;

    private String propertyType;

    public TableColumn(final String name, final String type, final Integer position, final Integer length, final String precision, final String scale, final Boolean nullable, final String remarks, final String defaultValue) {
        this.name = name;
        this.type = type;
        this.position = position;
        this.length = length;
        this.precision = precision;
        this.scale = scale;
        this.nullable = nullable;
        this.remarks = remarks;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Integer getPosition() {
        return position;
    }

    public Integer getLength() {
        return length;
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

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }
}
