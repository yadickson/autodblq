/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.connection;

/**
 *
 * @author Yadickson Soto
 */
public class DriverConnectionException extends RuntimeException {
    public DriverConnectionException(final Throwable ex) {
        super(ex);
    }
}
