/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.view.base.model;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Yadickson Soto
 */
public final class ViewBase {

    private final String realSchema;
    private final String realName;
    private final String realContent;

    private final String schema;
    private final String name;
    private final String newContent;

    private final String newSchema;
    private final String newName;

    private final String fullName;
    private final String newFullName;

    public ViewBase(ViewBase viewBase, String newSchema, String newName) {
        this(viewBase.getRealSchema(), viewBase.getSchema(), viewBase.getRealName(), viewBase.getName(), viewBase.getRealContent(), viewBase.getNewContent(), newName, newSchema);
    }

    public ViewBase(String realSchema, String schema, String realName, String name, String realContent, String newContent, String newSchema, String newName) {
        this.realSchema = realSchema;
        this.schema = schema;
        this.realName = realName;
        this.name = name;
        this.realContent = realContent;
        this.newContent = newContent;
        this.newSchema = newSchema;
        this.newName = newName;
        this.fullName = StringUtils.isEmpty(schema) ? name : schema + "." + name;
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
