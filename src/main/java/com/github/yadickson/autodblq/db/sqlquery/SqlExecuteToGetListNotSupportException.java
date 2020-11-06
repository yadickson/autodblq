/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.sqlquery;

/**
 *
 * @author Yadickson Soto
 */
public class SqlExecuteToGetListNotSupportException extends RuntimeException {
    public SqlExecuteToGetListNotSupportException(final String message) {
        super(message);
    }
}
