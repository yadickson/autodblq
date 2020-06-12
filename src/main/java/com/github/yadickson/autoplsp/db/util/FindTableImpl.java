/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yadickson.autoplsp.db.util;

import com.github.yadickson.autoplsp.db.bean.ContentBean;
import java.sql.Connection;
import java.util.List;


import com.github.yadickson.autoplsp.db.bean.TableBean;
import com.github.yadickson.autoplsp.db.bean.TableDefBean;
import com.github.yadickson.autoplsp.db.bean.TableFieldBean;
import com.github.yadickson.autoplsp.db.bean.TableFkBean;
import com.github.yadickson.autoplsp.db.bean.TableIncBean;
import com.github.yadickson.autoplsp.db.bean.TableIndBean;
import com.github.yadickson.autoplsp.db.bean.TablePkBean;
import com.github.yadickson.autoplsp.db.bean.TableUnqBean;
import com.github.yadickson.autoplsp.handler.BusinessException;

/**
 *
 * @author Yadickson Soto
 */
public class FindTableImpl implements FindTable {

    @Override
    public List<TableBean> getTables(Connection connection, String sql) throws BusinessException {
        return new FindImpl<TableBean>().getList(connection, sql, TableBean.class);
    }

    @Override
    public List<TableFieldBean> getColumns(Connection connection, String sql) throws BusinessException {
        return new FindImpl<TableFieldBean>().getList(connection, sql, TableFieldBean.class);
    }

    @Override
    public List<TablePkBean> getPkConstraints(Connection connection, String sql) throws BusinessException {
        return new FindImpl<TablePkBean>().getList(connection, sql, TablePkBean.class);
    }

    @Override
    public List<TableFkBean> getFkConstraints(Connection connection, String sql) throws BusinessException {
        return new FindImpl<TableFkBean>().getList(connection, sql, TableFkBean.class);
    }

    @Override
    public List<TableUnqBean> getUniqueConstraints(Connection connection, String sql) throws BusinessException {
        return new FindImpl<TableUnqBean>().getList(connection, sql, TableUnqBean.class);
    }

    @Override
    public List<TableIndBean> getIndexConstraints(Connection connection, String sql) throws BusinessException {
        return new FindImpl<TableIndBean>().getList(connection, sql, TableIndBean.class);
    }

    @Override
    public List<TableDefBean> getDefConstraints(Connection connection, String sql) throws BusinessException {
        return new FindImpl<TableDefBean>().getList(connection, sql, TableDefBean.class);
    }

    @Override
    public List<TableIncBean> getIncrementConstraints(Connection connection, String sql) throws BusinessException {
        return new FindImpl<TableIncBean>().getList(connection, sql, TableIncBean.class);
    }

    @Override
    public List<ContentBean> getDataTable(Connection connection, String sql) throws BusinessException {
        return new FindImpl<ContentBean>().getList(connection, sql, ContentBean.class);
    }
}
