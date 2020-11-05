/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.util;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.connection.DriverConnection;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class SqlExecuteToGetList {

    public <T> List<T> execute(
            final DriverConnection driverConnection,
            final String sql,
            final Class<T> clazz
    ) {

        try {

            if (StringUtils.isEmpty(sql)) {
                return Collections.EMPTY_LIST;
            }
            
            QueryRunner run = new QueryRunner();
            ResultSetHandler<List<T>> h = new BeanListHandler<>(clazz);
            return run.query(driverConnection.getConnection(), sql, h);

        } catch (SQLException | RuntimeException ex) {
            throw new SqlExecuteToGetListException(ex);
        }
    }
}
