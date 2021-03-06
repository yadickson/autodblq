/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.view.base;

import com.github.yadickson.autodblq.db.version.base.*;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseViewBaseReaderException extends RuntimeException {
    public DataBaseViewBaseReaderException(final Throwable ex) {
        super(ex);
    }
}
