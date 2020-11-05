/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.github.yadickson.autodblq.Parameters;

/**
 *
 * @author Yadickson Soto
 */
public class DriverConnectionBuilder {

    public Connection build(final Parameters parameters) {
        try {

            Class.forName(parameters.getDriver());

            return DriverManager.getConnection(
                    parameters.getUrl(),
                    parameters.getUsername(),
                    parameters.getPassword()
            );

        } catch (SQLException | ClassNotFoundException | RuntimeException ex) {
            throw new DriverConnectionException(ex);
        }

    }
}
