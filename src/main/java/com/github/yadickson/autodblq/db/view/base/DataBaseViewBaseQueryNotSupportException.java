/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.view.base;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseViewBaseQueryNotSupportException extends RuntimeException {
    public DataBaseViewBaseQueryNotSupportException(final String message) {
        super(message);
    }
}
