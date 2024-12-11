/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.type.base.model;

import com.github.yadickson.autodblq.db.property.DataBaseProperty;

/**
 *
 * @author Yadickson Soto
 */
public class TypeBase extends DataBaseProperty {

    private final String realSchema;
    private final String schema;
    private final String newSchema;

    private final String type;

    private final String realContent;
    private final String newContent;

    public TypeBase(final TypeBase type) {
        this(type.getRealSchema(), type.getRealName(), type.getSchema(), type.getName(), type.getNewSchema(), type.getNewName(), type.getType(), type.getRealContent(), type.getNewContent());
    }

    public TypeBase(final String realSchema, final String realName, final String schema, final String name, final String newSchema, final String newName, final String type, final String realContent, final String newContent) {
        super(realName, name, newName);
        this.realSchema = realSchema;
        this.schema = schema;
        this.newSchema = newSchema;
        this.type = type;
        this.realContent = realContent;
        this.newContent = newContent;
    }

    public String getRealSchema() {
        return realSchema;
    }

    public String getSchema() {
        return schema;
    }

    public String getNewSchema() {
        return newSchema;
    }

    @Override
    public String getType() {
        return type;
    }

    public String getRealContent() {
        return realContent;
    }

    public String getNewContent() {
        return newContent;
    }
}
