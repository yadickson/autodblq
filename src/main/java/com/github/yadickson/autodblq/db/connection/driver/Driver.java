/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.connection.driver;

/**
 *
 * @author Yadickson Soto
 */
public enum Driver {

    DB2("db2"),
    ORACLE("oracle"),
    POSTGRESQL("postgresql"),
    MSSQL("mssql"),
    OTHER("other");

    private final String message;

    private Driver(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
