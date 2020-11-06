/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.increments.model;

/**
 *
 * @author Yadickson Soto
 */
public final class TableIncrementBean {

    private String column;
    private String type;
    private String incrementby;
    private String startwith;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIncrementby() {
        return incrementby;
    }

    public void setIncrementby(String incrementby) {
        this.incrementby = incrementby;
    }

    public String getStartwith() {
        return startwith;
    }

    public void setStartwith(String startwith) {
        this.startwith = startwith;
    }

}
