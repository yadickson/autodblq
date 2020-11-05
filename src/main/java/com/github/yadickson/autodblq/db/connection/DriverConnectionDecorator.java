/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.connection;

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import com.github.yadickson.autodblq.Parameters;
import com.github.yadickson.autodblq.db.connection.driver.DriverMapper;

/**
 *
 * @author Yadickson Soto
 */
public class DriverConnectionDecorator extends DriverConnection implements Closeable {

    public DriverConnectionDecorator(final Parameters parameters) throws SQLException {
        super(new DriverMapper().apply(parameters.getDriver()), new DriverConnectionBuilder().build(parameters));
    }

    @Override
    public void close() throws IOException {
        try {
            DbUtils.close(getConnection());
        } catch (SQLException | RuntimeException ex) {
            throw new IOException(ex);
        }
    }
}
