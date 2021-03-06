/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.indexes;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseTableIndexQueryNotSupportException extends RuntimeException {
    public DataBaseTableIndexQueryNotSupportException(final String message) {
        super(message);
    }
}
