/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.function.base.model;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Yadickson Soto
 */
public final class FunctionBase {

    private final String schema;
    private final String name;
    private final String content;
    private final Boolean isFunction;
    private final String fullName;

    public FunctionBase(FunctionBase functionBase) {
        this(functionBase.getSchema(), functionBase.getName(), functionBase.getContent(), functionBase.getIsFunction());
    }

    public FunctionBase(String schema, String name, String content, Boolean isFunction) {
        this.schema = schema;
        this.name = name;
        this.content = content;
        this.isFunction = isFunction;
        this.fullName = StringUtils.isEmpty(schema) ? name : schema + "." + name;
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

    public String getFullName() {
        return fullName;
    }

}
