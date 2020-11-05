/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table;

import com.github.yadickson.autodblq.db.*;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseTableChainException extends RuntimeException {
    public DataBaseTableChainException(final Throwable ex) {
        super(ex);
    }
}
