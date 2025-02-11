/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.sequence.base;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseSequenceBaseQueryNotSupportException extends RuntimeException {
    public DataBaseSequenceBaseQueryNotSupportException(final String message) {
        super(message);
    }
}
