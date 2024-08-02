/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.columns.model;

import com.github.yadickson.autodblq.db.table.property.DataBaseTableProperty;

/**
 *
 * @author Yadickson Soto
 */
public class TableColumn extends DataBaseTableProperty {

    private final Integer position;
    private final Boolean nullable;
    private final String precision;
    private final String scale;
    private final String remarks;

    public TableColumn(final String name, final String type, final Integer position, final Integer length, final String precision, final String scale, final String remarks, final Boolean nullable) {
        super(name, type, length);
        this.position = position;
        this.precision = precision;
        this.scale = scale;
        this.nullable = nullable;
        this.remarks = remarks;
    }

    public Integer getPosition() {
        return position;
    }

    public String getScale() {
        return scale;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getPrecision() {
        return precision;
    }
}
