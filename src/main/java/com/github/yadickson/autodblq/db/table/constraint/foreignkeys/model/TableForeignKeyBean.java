/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.foreignkeys.model;

/**
 *
 * @author Yadickson Soto
 */
public final class TableForeignKeyBean {

    private String name;
    private String columns;
    private String refschema;
    private String refname;
    private String refcolumns;
    private String deleterule;
    private String updaterule;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getRefschema() {
        return refschema;
    }

    public void setRefschema(String refschema) {
        this.refschema = refschema;
    }

    public String getRefname() {
        return refname;
    }

    public void setRefname(String refname) {
        this.refname = refname;
    }

    public String getRefcolumns() {
        return refcolumns;
    }

    public void setRefcolumns(String refcolumns) {
        this.refcolumns = refcolumns;
    }

    public String getDeleterule() {
        return deleterule;
    }

    public void setDeleterule(String deleterule) {
        this.deleterule = deleterule;
    }

    public String getUpdaterule() {
        return updaterule;
    }

    public void setUpdaterule(String updaterule) {
        this.updaterule = updaterule;
    }
}
