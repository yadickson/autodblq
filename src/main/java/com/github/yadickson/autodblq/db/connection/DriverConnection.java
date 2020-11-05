/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.connection;

import java.sql.Connection;
import java.sql.SQLException;

import com.github.yadickson.autodblq.db.connection.driver.Driver;

/**
 *
 * @author Yadickson Soto
 */
public class DriverConnection {

    private final Driver driver;
    private final Connection connection;
    private final String name;
    private final String version;

    protected DriverConnection(final Driver driver, final Connection connection) throws SQLException {
        this.driver = driver;
        this.connection = connection;
        this.name = connection.getMetaData().getDriverName();
        this.version = String.valueOf(connection.getMetaData().getDriverMajorVersion());
    }

    public Driver getDriver() {
        return driver;
    }

    public Connection getConnection() {
        return connection;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
}
