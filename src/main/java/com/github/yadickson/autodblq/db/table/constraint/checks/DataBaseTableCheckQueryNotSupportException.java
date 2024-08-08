/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.checks;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseTableCheckQueryNotSupportException extends RuntimeException {
    public DataBaseTableCheckQueryNotSupportException(final String message) {
        super(message);
    }
}
