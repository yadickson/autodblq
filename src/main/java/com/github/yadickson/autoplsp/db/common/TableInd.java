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
public final class TableInd implements Serializable {

    static final long serialVersionUID = 1;

    private final String name;

    private final String columns;

    private final Boolean isUnique;

    /**
     * Class constructor.
     *
     * @param name The name
     * @param columns The columns
     */
    public TableInd(final String name, final String columns, final String isUnique) {
        this.name = name;
        this.columns = columns;
        this.isUnique = "Y".equalsIgnoreCase(isUnique);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the columns
     */
    public String getColumns() {
        return columns;
    }

    /**
     * @return the isUnique
     */
    public Boolean getIsUnique() {
        return isUnique;
    }
}
