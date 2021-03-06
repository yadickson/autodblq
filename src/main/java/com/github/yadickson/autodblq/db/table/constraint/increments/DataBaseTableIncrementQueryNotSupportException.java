/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.increments;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseTableIncrementQueryNotSupportException extends RuntimeException {
    public DataBaseTableIncrementQueryNotSupportException(final String message) {
        super(message);
    }
}
