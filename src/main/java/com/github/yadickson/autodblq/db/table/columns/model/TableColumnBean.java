/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.columns.model;

/**
 *
 * @author Yadickson Soto
 */
public final class TableColumnBean {

    private String position;
    private String name;
    private String type;
    private String length;
    private String nullable;
    private String precision;
    private String scale;
    private String identity;
    private String remarks;
    private String startwith;
    private String incrementby;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getStartwith() {
        return startwith;
    }

    public void setStartwith(String startwith) {
        this.startwith = startwith;
    }

    public String getIncrementby() {
        return incrementby;
    }

    public void setIncrementby(String incrementby) {
        this.incrementby = incrementby;
    }
}
