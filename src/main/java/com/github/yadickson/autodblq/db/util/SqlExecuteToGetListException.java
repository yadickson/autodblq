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
public class SqlExecuteToGetListException extends RuntimeException {
    public SqlExecuteToGetListException(final Throwable ex) {
        super(ex);
    }
}
