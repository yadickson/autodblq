/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.util;

/**
 *
 * @author Yadickson Soto
 */
public class SqlExecuteToGetObjectException extends RuntimeException {
    public SqlExecuteToGetObjectException(final Throwable ex) {
        super(ex);
    }
}
