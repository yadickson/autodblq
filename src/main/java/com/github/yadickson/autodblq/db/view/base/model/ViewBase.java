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

    private final String schema;
    private final String name;
    private final String content;

    private final String newSchema;
    private final String newName;

    private final String fullName;
    private final String newFullName;

    public ViewBase(ViewBase viewBase, String newSchema, String newName) {
        this(viewBase.getSchema(), viewBase.getName(), viewBase.getContent(), newName, newSchema);
    }

    public ViewBase(String schema, String name, String content, String newSchema, String newName) {
        this.schema = schema;
        this.name = name;
        this.content = content;
        this.newSchema = newSchema;
        this.newName = newName;
        this.fullName = StringUtils.isEmpty(schema) ? name : schema + "." + name;
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
