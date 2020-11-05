/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.function.base;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseFunctionBaseQueryNotSupportException extends RuntimeException {
    public DataBaseFunctionBaseQueryNotSupportException(final String message) {
        super(message);
    }
}
