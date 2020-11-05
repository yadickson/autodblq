/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.connection.driver;

import java.util.function.Function;
import java.util.regex.Pattern;

/**
 *
 * @author Yadickson Soto
 */
public class DriverMapper implements Function<String, Driver> {

    @Override
    public Driver apply(final String driver) {

        if (Pattern.compile(".*DB2Driver.*", Pattern.CASE_INSENSITIVE).matcher(driver).matches()) {
            return Driver.DB2;
        }

        if (Pattern.compile(".*Oracle.*", Pattern.CASE_INSENSITIVE).matcher(driver).matches()) {
            return Driver.ORACLE;
        }

        if (Pattern.compile(".*PostgreSQL.*", Pattern.CASE_INSENSITIVE).matcher(driver).matches()) {
            return Driver.POSTGRESQL;
        }

        if (Pattern.compile(".*Jtds.*", Pattern.CASE_INSENSITIVE).matcher(driver).matches()) {
            return Driver.MSSQL;
        }

        throw new DriverNotSupportException("Driver [" + driver + "] not supported");
    }

}
