/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.function.base.model;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Yadickson Soto
 */
public final class FunctionBase {

    private final String realSchema;
    private final String schema;
    private final String realName;
    private final String name;
    private final String realContent;
    private final String newContent;
    private final Boolean isFunction;
    private final String newSchema;
    private final String newName;

    private final String fullName;
    private final String newFullName;

    public FunctionBase(FunctionBase functionBase) {
        this(functionBase.getRealSchema(), functionBase.getSchema(), functionBase.getRealName(), functionBase.getName(), functionBase.getRealContent(), functionBase.getNewContent(), functionBase.getIsFunction(), functionBase.getNewSchema(), functionBase.getNewName());
    }

    public FunctionBase(String realSchema, String schema, String realName, String name, String realContent, String newContent, Boolean isFunction, final String newSchema, final String newName) {
        this.realSchema = realSchema;
        this.schema = schema;
        this.realName = realName;
        this.name = name;
        this.realContent = realContent;
        this.newContent = newContent;
        this.isFunction = isFunction;
        this.newSchema = newSchema;
        this.newName = newName;

        this.fullName = org.apache.commons.lang3.StringUtils.isEmpty(realSchema) ? realName : realSchema + "." + realName;
        this.newFullName = StringUtils.isEmpty(newSchema) ? newName : newSchema + "." + newName;
    }

    public String getRealSchema() {
        return realSchema;
    }

    public String getRealName() {
        return realName;
    }

    public String getRealContent() {
        return realContent;
    }

    public String getSchema() {
        return schema;
    }

    public String getName() {
        return name;
    }

    public String getNewContent() {
        return newContent;
    }

    public Boolean getIsFunction() {
        return isFunction;
    }

    public String getNewSchema() {
        return newSchema;
    }

    public String getNewName() {
        return newName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getNewFullName() {
        return newFullName;
    }

}
