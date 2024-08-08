/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.github.yadickson.autodblq.ParametersPlugin;

/**
 *
 * @author Yadickson Soto
 */
public class DriverConnectionBuilder {

    public Connection build(final ParametersPlugin parametersPlugin) {
        try {

            Class.forName(parametersPlugin.getDriver());

            return DriverManager.getConnection(
                    parametersPlugin.getUrl(),
                    parametersPlugin.getUsername(),
                    parametersPlugin.getPassword()
            );

        } catch (SQLException | ClassNotFoundException | RuntimeException ex) {
            throw new DriverConnectionException(ex);
        }

    }
}
