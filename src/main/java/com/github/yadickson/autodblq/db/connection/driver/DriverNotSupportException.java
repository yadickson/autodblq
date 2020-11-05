/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.connection.driver;

/**
 *
 * @author Yadickson Soto
 */
public class DriverNotSupportException extends RuntimeException {
    public DriverNotSupportException(final String message) {
        super(message);
    }
}
