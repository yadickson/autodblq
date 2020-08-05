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
import java.util.ArrayList;
import java.util.List;

/**
 * Table class
 *
 * @author Yadickson Soto
 */
public final class Table implements Serializable {

    static final long serialVersionUID = 1;

    private final String name;

    private final String schema;

    private final String remarks;

    private final List<TableField> fields = new ArrayList<TableField>();

    private final List<TableInc> incFields = new ArrayList<TableInc>();

    private final List<TableDef> defFields = new ArrayList<TableDef>();

    private final List<TablePk> pkFields = new ArrayList<TablePk>();

    private final List<TableFk> fkFields = new ArrayList<TableFk>();

    private final List<TableUnq> unqFields = new ArrayList<TableUnq>();

    private final List<TableInd> indFields = new ArrayList<TableInd>();

    /**
     * Class constructor.
     *
     * @param schema The schema
     * @param name The name
     * @param remarks The remarks
     */
    public Table(final String schema, final String name, final String remarks) {
        this.schema = schema;
        this.name = name;
        this.remarks = remarks;
    }

    public String getFullName() {
         if (schema != null ) {
            return schema + "." + name;
        } else {
            return name;
        }
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
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @return the fields
     */
    public List<TableField> getFields() {
        return fields;
    }

    /**
     * @return the fields
     */
    public List<TablePk> getPkFields() {
        return pkFields;
    }

    /**
     * @return the fields
     */
    public List<TableFk> getFkFields() {
        return fkFields;
    }

    /**
     * @return the fields
     */
    public List<TableUnq> getUnqFields() {
        return unqFields;
    }

    /**
     * @return the fields
     */
    public List<TableInd> getIndFields() {
        return indFields;
    }

    /**
     * @return the autoIncFields
     */
    public List<TableInc> getIncFields() {
        return incFields;
    }

    /**
     * @return the defFields
     */
    public List<TableDef> getDefFields() {
        return defFields;
    }

}
