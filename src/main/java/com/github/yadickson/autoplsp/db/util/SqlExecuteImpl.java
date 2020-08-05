/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yadickson.autoplsp.db.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.github.yadickson.autoplsp.handler.BusinessException;

/**
 * Sql execute implementation.
 *
 * @author Yadickson Soto
 */
public final class SqlExecuteImpl implements SqlExecute {

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> List<T> execute(
            final Connection connection,
            final String sql,
            final Class<T> clazz
    ) throws BusinessException {

        List<T> list = new ArrayList<T>();

        if (connection == null) {
            return list;
        }

        QueryRunner run = new QueryRunner();
        ResultSetHandler<List<T>> h = new BeanListHandler<T>(clazz);

        try {
            list = run.query(connection, sql, h);
        } catch (SQLException ex) {
            throw new BusinessException("[SqlExecute] Error to execute sql", ex);
        }

        return list;
    }

}
