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
    private final String fullName;

    public TableBase(final TableBase table) {
        this(table.getSchema(), table.getName(), table.getRemarks());
    }

    public TableBase(final String schema, final String name, final String remarks) {
        this.schema = schema;
        this.name = name;
        this.remarks = remarks;
        this.fullName = StringUtils.isEmpty(schema) ? name : schema + "." + name;
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

    public String getFullName() {
        return fullName;
    }

}
