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
    private final String newName;
    private final String type;
    private final Integer length;
    private String defaultType;
    private String defaultValue;
    private String propertyType;

    public DataBaseTableProperty(final String name, String newName) {
        this(name, newName, null);
    }

    public DataBaseTableProperty(final String name, String newName, final String type) {
        this(name, newName, type, 0);
    }

    public DataBaseTableProperty(final String name, String newName, final String type, final Integer length) {
        this.name = name;
        this.newName = newName;
        this.type = type;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public String getNewName() {
        return newName;
    }

    public String getType() {
        return type;
    }

    public Integer getLength() {
        return length;
    }

    public String getDefaultType() {
        return defaultType;
    }

    public void setDefaultType(String defaultType) {
        this.defaultType = defaultType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }
}
