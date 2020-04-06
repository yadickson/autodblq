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

import com.github.yadickson.autoplsp.db.bean.FunctionBean;
import com.github.yadickson.autoplsp.db.bean.ContentBean;
import com.github.yadickson.autoplsp.handler.BusinessException;

/**
 *
 * @author Yadickson Soto
 */
public class FindProcedureImpl implements FindProcedure {

    @Override
    public List<FunctionBean> getProcedures(Connection connection, String sql) throws BusinessException {

        List<FunctionBean> list = new ArrayList<FunctionBean>();

        if (connection == null) {
            return list;
        }

        QueryRunner run = new QueryRunner();
        ResultSetHandler<List<FunctionBean>> h = new BeanListHandler<FunctionBean>(FunctionBean.class);

        try {
            list = run.query(connection, sql, h);
        } catch (SQLException ex) {
            throw new BusinessException("[FindProcedureImpl] Error find attributes", ex);
        }

        return list;
    }

    @Override
    public List<ContentBean> getText(Connection connection, String sql) throws BusinessException {

        List<ContentBean> list = new ArrayList<ContentBean>();

        if (connection == null) {
            return list;
        }

        QueryRunner run = new QueryRunner();
        ResultSetHandler<List<ContentBean>> h = new BeanListHandler<ContentBean>(ContentBean.class);

        try {
            list = run.query(connection, sql, h);
        } catch (SQLException ex) {
            throw new BusinessException("[FindProcedureImpl] Error find attributes", ex);
        }

        return list;
    }

}
