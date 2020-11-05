/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.util;

import java.sql.SQLException;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.github.yadickson.autodblq.db.connection.DriverConnection;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class SqlExecuteToGetObject {

    public <T> T execute(
            final DriverConnection driverConnection,
            final String sql,
            final Class<T> clazz
    ) {

        try {

            QueryRunner run = new QueryRunner();
            ResultSetHandler<T> h = new BeanHandler<>(clazz);
            return run.query(driverConnection.getConnection(), sql, h);

        } catch (SQLException | RuntimeException ex) {
            throw new SqlExecuteToGetObjectException(ex);
        }
    }
}
