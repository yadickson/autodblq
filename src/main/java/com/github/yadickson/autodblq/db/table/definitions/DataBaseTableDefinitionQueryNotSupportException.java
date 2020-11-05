/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.definitions;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseTableDefinitionQueryNotSupportException extends RuntimeException {
    public DataBaseTableDefinitionQueryNotSupportException(final String message) {
        super(message);
    }
}
