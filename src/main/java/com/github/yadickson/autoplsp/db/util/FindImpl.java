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
import com.github.yadickson.autoplsp.handler.BusinessException;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 *
 * @author Yadickson Soto
 * @param <T> template
 */
public final class FindImpl<T> {

    public List<T> getList(Connection connection, String sql, Class<T> t) throws BusinessException {

        List<T> list = new ArrayList<T>();

        if (connection == null) {
            return list;
        }

        QueryRunner run = new QueryRunner();
        ResultSetHandler<List<T>> h = new BeanListHandler<T>(t);

        try {
            list = run.query(connection, sql, h);
        } catch (SQLException ex) {
            throw new BusinessException("[FindImpl] Error find elements", ex);
        }

        return list;
    }

}
