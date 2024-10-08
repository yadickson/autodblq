/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.property;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseProperty {

    private final String realName;
    private final String name;
    private final String newName;
    private final String type;
    private final Integer length;
    private String defaultType;
    private String defaultValue;
    private String propertyType;

    public DataBaseProperty(final String realName, final String name, String newName) {
        this(realName, name, newName, null);
    }

    public DataBaseProperty(final String realName, final String name, String newName, final String type) {
        this(realName, name, newName, type, 0);
    }

    public DataBaseProperty(final String realName, final String name, String newName, final String type, final Integer length) {
        this.realName = realName;
        this.name = name;
        this.newName = newName;
        this.type = type;
        this.length = length;
    }

    public String getRealName() {
        return realName;
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

    public DataBaseProperty setDefaultType(String defaultType) {
        this.defaultType = defaultType;
        return this;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public DataBaseProperty setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public DataBaseProperty setPropertyType(String propertyType) {
        this.propertyType = propertyType;
        return this;
    }
}
