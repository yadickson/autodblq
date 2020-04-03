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

    private String name;

    private String schema;

    private String remarks;

    private List<TableField> fields = new ArrayList<TableField>();

    private List<TablePk> pkFields = new ArrayList<TablePk>();

    private List<TableFk> fkFields = new ArrayList<TableFk>();

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
        return schema + "." + name;
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
     * @return the schema
     */
    public String getSchema() {
        return schema;
    }

    /**
     * @param schema the schema to set
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the fields
     */
    public List<TableField> getFields() {
        return fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(List<TableField> fields) {
        this.fields = fields;
    }

    /**
     * @return the fields
     */
    public List<TablePk> getPkFields() {
        return pkFields;
    }

    /**
     * @param fields the fields to set
     */
    public void setPkFields(List<TablePk> fields) {
        this.pkFields = fields;
    }

    /**
     * @return the fields
     */
    public List<TableFk> getFkFields() {
        return fkFields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFkFields(List<TableFk> fields) {
        this.fkFields = fields;
    }

}
