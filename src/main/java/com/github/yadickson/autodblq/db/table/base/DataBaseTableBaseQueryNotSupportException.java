/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.base;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseTableBaseQueryNotSupportException extends RuntimeException {
    public DataBaseTableBaseQueryNotSupportException(final String message) {
        super(message);
    }
}
