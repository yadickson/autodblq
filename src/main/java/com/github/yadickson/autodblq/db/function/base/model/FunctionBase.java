/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.function.base.model;

/**
 *
 * @author Yadickson Soto
 */
public final class FunctionBase {

    private final String schema;
    private final String name;
    private final String content;
    private final Boolean function;

    public FunctionBase(FunctionBase functionBase) {
        this(functionBase.getSchema(), functionBase.getName(), functionBase.getContent(), functionBase.getFunction());
    }

    public FunctionBase(String schema, String name, String content, Boolean function) {
        this.schema = schema;
        this.name = name;
        this.content = content;
        this.function = function;
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

    public Boolean getFunction() {
        return function;
    }

}
