/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yadickson.autoplsp.db.common;

import java.io.Serializable;
import java.util.Locale;

/**
 *
 * @author Yadickson Soto
 */
public final class TableField implements Serializable {

    static final long serialVersionUID = 1;

    private final String name;

    private final String type;

    private final String position;

    private final String length;

    private final Boolean nullable;

    private final String scale;

    private final String remarks;

    /**
     * Class constructor.
     *
     * @param name field name.
     * @param type field type.
     * @param position field position.
     * @param length length.
     * @param scale precision.
     * @param nullable null flag.
     * @param remarks the remarks
     */
    public TableField(
            final String name,
            final String type,
            final String position,
            final String length,
            final String scale,
            final String nullable,
            final String remarks
    ) {
        this.name = name;
        this.type = type.toUpperCase(Locale.ENGLISH);
        this.position = position;
        this.length = length;
        this.scale = scale;
        this.nullable = "Y".equalsIgnoreCase(nullable);
        this.remarks = remarks;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @return the length
     */
    public String getLength() {
        return length;
    }

    /**
     * @return the nullable
     */
    public Boolean getNullable() {
        return nullable;
    }

    /**
     * @return the scale
     */
    public String getScale() {
        return scale;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

}
