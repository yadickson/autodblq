/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint;

import com.github.yadickson.autodblq.db.*;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseTableConstraintChainException extends RuntimeException {
    public DataBaseTableConstraintChainException(final Throwable ex) {
        super(ex);
    }
}
