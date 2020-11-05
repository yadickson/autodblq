/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.definitions.model;

/**
 *
 * @author Yadickson Soto
 */
public final class TableColumn  {

    private final String name;
    private final String type;
    private final String position;
    private final String length;
    private final Boolean nullable;
    private final String scale;
    private final String remarks;

    public TableColumn(final String name, final String type, final String position, final String length, final String scale, final Boolean nullable, final String remarks) {
        this.name = name;
        this.type = type;
        this.position = position;
        this.length = length;
        this.scale = scale;
        this.nullable = nullable;
        this.remarks = remarks;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPosition() {
        return position;
    }

    public String getLength() {
        return length;
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

}
