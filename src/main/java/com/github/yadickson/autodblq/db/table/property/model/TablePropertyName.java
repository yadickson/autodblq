/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.property.model;

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
    DATETIME_FUNCTION("datetime_function");

    private final String message;

    private TablePropertyName(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
