/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.writer.util;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Yadickson Soto
 */
public class TableColumnTypeUtil {
    
    public Boolean isString(final String string) {
        return StringUtils.containsIgnoreCase(string, "char") || StringUtils.containsIgnoreCase(string, "uuid") || StringUtils.containsIgnoreCase(string, "uniqueidentifier") || StringUtils.containsIgnoreCase(string, "text");
    }

    public Boolean isNumeric(final String string) {
        return StringUtils.containsIgnoreCase(string, "INT") || StringUtils.containsIgnoreCase(string, "FLOAT") || StringUtils.containsIgnoreCase(string, "REAL");
    }

    public Boolean isDate(final String string) {
        return StringUtils.containsIgnoreCase(string, "DATE") || StringUtils.containsIgnoreCase(string, "TIMESTAMP");
    }

}
