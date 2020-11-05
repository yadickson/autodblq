/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.writer.util;

/**
 *
 * @author Yadickson Soto
 */
public final class TableColumnTypeUtil {
    
    public Boolean isString(final String string) {
        return string.contains("CHAR");
    }

    public Boolean isNumeric(final String string) {
        return string.contains("INT") || string.contains("FLOAT") || string.contains("REAL");
    }

    public Boolean isDate(final String string) {
        return string.contains("DATE") || string.contains("TIMESTAMP");
    }

}
