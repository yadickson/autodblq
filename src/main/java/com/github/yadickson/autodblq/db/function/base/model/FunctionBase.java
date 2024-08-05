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

    private final String schema;
    private final String name;
    private final String content;
    private final Boolean isFunction;
    private final String newSchema;
    private final String newName;

    private final String fullName;
    private final String newFullName;

    public FunctionBase(FunctionBase functionBase) {
        this(functionBase.getSchema(), functionBase.getName(), functionBase.getContent(), functionBase.getIsFunction(), functionBase.getNewSchema(), functionBase.getNewName());
    }

    public FunctionBase(String schema, String name, String content, Boolean isFunction, final String newSchema, final String newName) {
        this.schema = schema;
        this.name = name;
        this.content = content;
        this.isFunction = isFunction;
        this.newSchema = newSchema;
        this.newName = newName;

        this.fullName = org.apache.commons.lang3.StringUtils.isEmpty(schema) ? name : schema + "." + name;
        this.newFullName = StringUtils.isEmpty(newSchema) ? newName : newSchema + "." + newName;
    }

    public String getSchema() {
        return schema;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
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
