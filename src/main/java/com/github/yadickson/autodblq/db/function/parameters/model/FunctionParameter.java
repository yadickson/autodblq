/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.function.parameters.model;

import com.github.yadickson.autodblq.db.property.DataBaseProperty;

/**
 *
 * @author Yadickson Soto
 */
public class FunctionParameter extends DataBaseProperty {

    private final Integer position;
    private final String precision;
    private final String scale;
    private final String mode;

    public FunctionParameter(final String realName, final String name, String newName, final String type, final Integer position, final Integer length, final String precision, final String scale, final String defaultValue, String mode) {
        super(realName, name, newName, type, length);
        this.position = position;
        this.precision = precision;
        this.scale = scale;
        this.mode = mode;
        this.setDefaultType(type);
        this.setDefaultValue(defaultValue);
    }

    public Integer getPosition() {
        return position;
    }

    public String getScale() {
        return scale;
    }

    public String getPrecision() {
        return precision;
    }

    public String getMode() {
        return mode;
    }
}
