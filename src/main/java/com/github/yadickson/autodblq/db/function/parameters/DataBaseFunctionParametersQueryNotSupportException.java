/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.function.parameters;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseFunctionParametersQueryNotSupportException extends RuntimeException {
    public DataBaseFunctionParametersQueryNotSupportException(final String message) {
        super(message);
    }
}
