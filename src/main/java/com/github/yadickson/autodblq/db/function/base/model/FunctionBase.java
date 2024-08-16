/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.function.base.model;

import com.github.yadickson.autodblq.db.property.DataBaseProperty;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Yadickson Soto
 */
public class FunctionBase extends DataBaseProperty {

    private final String realSchema;
    private final String schema;
    private final String realContent;
    private final String newContent;
    private final Boolean isFunction;
    private final String newSchema;
    private final String fullName;
    private final String newFullName;

    public FunctionBase(FunctionBase functionBase) {
        this(functionBase.getRealSchema(), functionBase.getSchema(), functionBase.getRealName(), functionBase.getName(), functionBase.getRealContent(), functionBase.getNewContent(), functionBase.getIsFunction(), functionBase.getNewSchema(), functionBase.getNewName(), functionBase.getType());
    }

    public FunctionBase(String realSchema, String schema, String realName, String name, String realContent, String newContent, Boolean isFunction, final String newSchema, final String newName, final String returnType) {
        super(realName, name, newName, returnType);
        this.realSchema = realSchema;
        this.schema = schema;
        this.realContent = realContent;
        this.newContent = newContent;
        this.isFunction = isFunction;
        this.newSchema = newSchema;

        this.fullName = org.apache.commons.lang3.StringUtils.isEmpty(realSchema) ? realName : realSchema + "." + realName;
        this.newFullName = StringUtils.isEmpty(newSchema) ? newName : newSchema + "." + newName;
    }

    public String getRealSchema() {
        return realSchema;
    }

    public String getRealContent() {
        return realContent;
    }

    public String getSchema() {
        return schema;
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

    public String getFullName() {
        return fullName;
    }

    public String getNewFullName() {
        return newFullName;
    }
}
