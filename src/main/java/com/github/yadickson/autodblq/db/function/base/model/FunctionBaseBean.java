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
public final class FunctionBaseBean {

    private String name;
    private String schema;
    private String content;
    private String isfunction;
    private String returntype;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsfunction() {
        return isfunction;
    }

    public void setIsfunction(String isfunction) {
        this.isfunction = isfunction;
    }

    public String getReturntype() {
        return returntype;
    }

    public void setReturntype(String returntype) {
        this.returntype = returntype;
    }
}
