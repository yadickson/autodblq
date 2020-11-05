/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.indexes.model;

/**
 *
 * @author Yadickson Soto
 */
public final class TableIndexBean{

    private String name;
    private String columns;
    private String isunique;

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

    public String getIsunique() {
        return isunique;
    }

    public void setIsunique(String isunique) {
        this.isunique = isunique;
    }

}
