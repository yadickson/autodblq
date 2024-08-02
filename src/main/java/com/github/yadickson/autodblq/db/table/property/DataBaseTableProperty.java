/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.property;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseTableProperty {

    private final String name;
    private final String type;
    private final Integer length;
    private String propertyType;

    public DataBaseTableProperty(final String name) {
        this(name, null);
    }

    public DataBaseTableProperty(final String name, final String type) {
        this(name, type, 0);
    }

    public DataBaseTableProperty(final String name, final String type, final Integer length) {
        this.name = name;
        this.type = type;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Integer getLength() {
        return length;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }
}
