/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.property.model;

/**
 *
 * @author Yadickson Soto
 */
public enum TablePropertyName {

    UUID("uuid"),
    UUID_FUNCTION("uuid_function"),
    BOOLEAN("boolean"),
    BOOLEAN_TRUE("boolean_true"),
    BOOLEAN_FALSE("boolean_false"),
    INTEGER("integer"),
    DATETIME("datetime"),
    STRING("string"),
    INIT_FUNCTION("init_function"),
    END_FUNCTION("end_function"),
    PRE_IN_MODE("pre_in_mode"),
    PRE_OUT_MODE("pre_out_mode"),
    PRE_IN_OUT_MODE("pre_inout_mode"),
    POST_IN_MODE("post_in_mode"),
    POST_OUT_MODE("post_out_mode"),
    POST_IN_OUT_MODE("post_inout_mode"),
    PRE_VALUE("pre_value"),
    DATETIME_FUNCTION("datetime_function");

    private final String message;

    private TablePropertyName(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
