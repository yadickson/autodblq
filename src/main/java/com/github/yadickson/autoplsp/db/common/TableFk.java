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
public final class TableFk implements Serializable {

    static final long serialVersionUID = 1;

    private String name;

    private String columns;

    private String tschema;

    private String tname;

    private String tcolumns;

    /**
     * Class constructor.
     *
     * @param name The name
     * @param columns The columns
     * @param tschema The ref table schema
     * @param tname The ref table name
     * @param tcolumns The ref table columns
     */
    public TableFk(final String name, final String columns, final String tschema, final String tname, final String tcolumns) {
        this.name = name;
        this.columns = columns;
        this.tschema = tschema;
        this.tname = tname;
        this.tcolumns = tcolumns;

    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the columns
     */
    public String getColumns() {
        return columns;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns(String columns) {
        this.columns = columns;
    }

    /**
     * @return the tschema
     */
    public String getTschema() {
        return tschema;
    }

    /**
     * @param tschema the tschema to set
     */
    public void setTschema(String tschema) {
        this.tschema = tschema;
    }

    /**
     * @return the tname
     */
    public String getTname() {
        return tname;
    }

    /**
     * @param tname the tname to set
     */
    public void setTname(String tname) {
        this.tname = tname;
    }

    /**
     * @return the tcolumns
     */
    public String getTcolumns() {
        return tcolumns;
    }

    /**
     * @param tcolumns the tcolumns to set
     */
    public void setTcolumns(String tcolumns) {
        this.tcolumns = tcolumns;
    }

}
