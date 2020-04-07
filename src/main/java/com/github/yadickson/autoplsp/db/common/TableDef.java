/*
 * Copyright (C) 2019 Yadickson Soto
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.yadickson.autoplsp.db.common;

import java.io.Serializable;

/**
 * Table class
 *
 * @author Yadickson Soto
 */
public final class TableDef implements Serializable {

    static final long serialVersionUID = 1;

    private final String column;

    private final String type;

    private final String value;

    /**
     * Class constructor.
     *
     * @param column The column name
     * @param type The type
     * @param value The value
     */
    public TableDef(final String column, final String type, final String value) {
        this.column = column;
        this.type = type;
        this.value = value;
    }

    /**
     * @return the column
     */
    public String getColumn() {
        return column;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }
}
