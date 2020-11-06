/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.primarykeys;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseTablePrimaryKeyQueryNotSupportException extends RuntimeException {
    public DataBaseTablePrimaryKeyQueryNotSupportException(final String message) {
        super(message);
    }
}
