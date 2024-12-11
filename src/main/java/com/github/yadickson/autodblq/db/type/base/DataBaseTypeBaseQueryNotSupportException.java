/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.type.base;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseTypeBaseQueryNotSupportException extends RuntimeException {
    public DataBaseTypeBaseQueryNotSupportException(final String message) {
        super(message);
    }
}
