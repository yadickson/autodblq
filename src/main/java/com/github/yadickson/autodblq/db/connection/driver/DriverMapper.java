/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.connection.driver;

import java.util.Locale;
import java.util.function.Function;

/**
 *
 * @author Yadickson Soto
 */
public class DriverMapper implements Function<String, Driver> {

    @Override
    public Driver apply(final String driver) {

        final String name = driver.toLowerCase(Locale.US);

        if (name.contains("db2driver")) {
            return Driver.DB2;
        }

        if (name.contains("oracle")) {
            return Driver.ORACLE;
        }

        if (name.contains("postgresql")) {
            return Driver.POSTGRESQL;
        }

        if (name.contains("jtds")) {
            return Driver.MSSQL;
        }

        throw new DriverNotSupportException("Driver [" + driver + "] not supported");
    }

}
