/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.foreignkeys;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseTableForeignKeyQueryNotSupportException extends RuntimeException {
    public DataBaseTableForeignKeyQueryNotSupportException(final String message) {
        super(message);
    }
}
