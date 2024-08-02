/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.columns;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseTableColumnsQueryNotSupportException extends RuntimeException {
    public DataBaseTableColumnsQueryNotSupportException(final String message) {
        super(message);
    }
}
