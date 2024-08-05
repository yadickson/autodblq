/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.base.model;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Yadickson Soto
 */
public class TableBase {

    private final String schema;
    private final String name;
    private final String remarks;
    private final String newSchema;
    private final String newName;

    private final String fullName;
    private final String newFullName;

    public TableBase(final TableBase table) {
        this(table.getSchema(), table.getName(), table.getRemarks(), table.getNewSchema(), table.getNewName());
    }

    public TableBase(final String schema, final String name, final String remarks, final String newSchema, final String newName) {
        this.schema = schema;
        this.name = name;
        this.remarks = remarks;
        this.newSchema = newSchema;
        this.newName = newName;
        this.fullName = StringUtils.isEmpty(schema) ? name : schema + "." + name;
        this.newFullName = StringUtils.isEmpty(newSchema) ? newName : newSchema + "." + newName;
    }

    public String getName() {
        return name;
    }

    public String getSchema() {
        return schema;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getNewName() {
        return newName;
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
