/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.defaults;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseTableDefaultQueryNotSupportException extends RuntimeException {
    public DataBaseTableDefaultQueryNotSupportException(final String message) {
        super(message);
    }
}
