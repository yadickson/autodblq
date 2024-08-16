/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.base.model;

import com.github.yadickson.autodblq.db.property.DataBaseProperty;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Yadickson Soto
 */
public class TableBase extends DataBaseProperty {

    private final String realSchema;
    private final String schema;
    private final String remarks;
    private final String newSchema;

    private final String fullName;
    private final String newFullName;

    public TableBase(final TableBase table) {
        this(table.getRealSchema(), table.getRealName(), table.getSchema(), table.getName(), table.getRemarks(), table.getNewSchema(), table.getNewName());
    }

    public TableBase(final String realSchema, final String realName, final String schema, final String name, final String remarks, final String newSchema, final String newName) {
        super(realName, name, newName);
        this.realSchema = realSchema;
        this.schema = schema;
        this.remarks = remarks;
        this.newSchema = newSchema;
        this.fullName = StringUtils.isEmpty(realSchema) ? realName : realSchema + "." + realName;
        this.newFullName = StringUtils.isEmpty(newSchema) ? newName : newSchema + "." + newName;
    }

    public String getRealSchema() {
        return realSchema;
    }

    public String getSchema() {
        return schema;
    }

    public String getRemarks() {
        return remarks;
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
