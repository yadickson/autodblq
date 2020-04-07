/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yadickson.autoplsp.db.util;

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
public interface FindTable {

    List<TableBean> getTables(Connection connection, String sql) throws BusinessException;

    List<TableFieldBean> getColumns(Connection connection, String sql) throws BusinessException;

    List<TablePkBean> getPkConstraints(Connection connection, String sql) throws BusinessException;

    List<TableFkBean> getFkConstraints(Connection connection, String sql) throws BusinessException;

    List<TableUnqBean> getUniqueConstraints(Connection connection, String sql) throws BusinessException;

    List<TableIndBean> getIndexConstraints(Connection connection, String sql) throws BusinessException;

    List<TableDefBean> getDefConstraints(Connection connection, String sql) throws BusinessException;

    List<TableIncBean> getIncrementConstraints(Connection connection, String sql) throws BusinessException;

}
