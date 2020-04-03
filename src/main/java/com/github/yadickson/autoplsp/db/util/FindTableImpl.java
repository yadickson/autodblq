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

import com.github.yadickson.autoplsp.db.bean.TableBean;
import com.github.yadickson.autoplsp.db.bean.TableFieldBean;
import com.github.yadickson.autoplsp.db.bean.TableFkBean;
import com.github.yadickson.autoplsp.db.bean.TablePkBean;
import com.github.yadickson.autoplsp.handler.BusinessException;

/**
 *
 * @author Yadickson Soto
 */
public class FindTableImpl implements FindTable {

    @Override
    public List<TableBean> getTables(Connection connection, String sql) throws BusinessException {

        List<TableBean> list = new ArrayList<TableBean>();

        if (connection == null) {
            return list;
        }

        QueryRunner run = new QueryRunner();
        ResultSetHandler<List<TableBean>> h = new BeanListHandler<TableBean>(TableBean.class);

        try {
            list = run.query(connection, sql, h);
        } catch (SQLException ex) {
            throw new BusinessException("[FindTableImpl] Error find attributes", ex);
        }

        return list;
    }

    @Override
    public List<TableFieldBean> getColumns(Connection connection, String sql) throws BusinessException {

        List<TableFieldBean> list = new ArrayList<TableFieldBean>();

        if (connection == null) {
            return list;
        }

        QueryRunner run = new QueryRunner();
        ResultSetHandler<List<TableFieldBean>> h = new BeanListHandler<TableFieldBean>(TableFieldBean.class);

        try {
            list = run.query(connection, sql, h);
        } catch (SQLException ex) {
            throw new BusinessException("[FindTableImpl] Error find attributes", ex);
        }

        return list;
    }

    @Override
    public List<TablePkBean> getPkConstraints(Connection connection, String sql) throws BusinessException {

        List<TablePkBean> list = new ArrayList<TablePkBean>();

        if (connection == null) {
            return list;
        }

        QueryRunner run = new QueryRunner();
        ResultSetHandler<List<TablePkBean>> h = new BeanListHandler<TablePkBean>(TablePkBean.class);

        try {
            list = run.query(connection, sql, h);
        } catch (SQLException ex) {
            throw new BusinessException("[FindTableImpl] Error find pks", ex);
        }

        return list;
    }

    @Override
    public List<TableFkBean> getFkConstraints(Connection connection, String sql) throws BusinessException {

        List<TableFkBean> list = new ArrayList<TableFkBean>();

        if (connection == null) {
            return list;
        }

        QueryRunner run = new QueryRunner();
        ResultSetHandler<List<TableFkBean>> h = new BeanListHandler<TableFkBean>(TableFkBean.class);

        try {
            list = run.query(connection, sql, h);
        } catch (SQLException ex) {
            throw new BusinessException("[FindTableImpl] Error find fks", ex);
        }

        return list;
    }
}
