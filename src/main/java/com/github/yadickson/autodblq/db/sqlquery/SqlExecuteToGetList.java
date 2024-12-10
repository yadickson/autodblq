/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.sqlquery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

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
public class SqlExecuteToGetList {

    public <T> List<T> execute(
            final DriverConnection driverConnection,
            final String sql,
            final Class<T> type
    ) {

        try {

            if (StringUtils.isEmpty(sql)) {
                return new ArrayList<T>();
            }
            
            QueryRunner run = new QueryRunner();
            ResultSetHandler<List<T>> h = new BeanListHandler<>(type);
            return run.query(driverConnection.getConnection(), sql, h);

        } catch (SQLException | RuntimeException ex) {
            throw new SqlExecuteToGetListException(ex);
        }
    }

}
