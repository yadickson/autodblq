/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.uniques;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseTableUniqueQueryNotSupportException extends RuntimeException {
    public DataBaseTableUniqueQueryNotSupportException(final String message) {
        super(message);
    }
}
