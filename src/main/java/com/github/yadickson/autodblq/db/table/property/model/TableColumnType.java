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
public enum TableColumnType {

    UUID("uuid"),
    BOOLEAN("boolean"),
    INTEGER("integer"),
    DATETIME("datetime");

    private final String message;

    private TableColumnType(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
