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
public final class Function implements Serializable {

    static final long serialVersionUID = 1;

    private final String name;

    private final String schema;

    private final Boolean isFunction;
    
    private String text;

    /**
     * Class constructor.
     *
     * @param schema The schema
     * @param name The name
     */
    public Function(final String schema, final String name, final String isFunction) {
        this.schema = schema;
        this.name = name;
        this.isFunction = "Y".equalsIgnoreCase(isFunction);
    }

    public String getFullName() {
        return schema + "." + name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the schema
     */
    public String getSchema() {
        return schema;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the isFunction
     */
    public Boolean getIsFunction() {
        return isFunction;
    }
}
