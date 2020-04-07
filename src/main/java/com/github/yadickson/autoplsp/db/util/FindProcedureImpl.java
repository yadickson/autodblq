/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yadickson.autoplsp.db.util;

import java.sql.Connection;
import java.util.List;


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
        return new FindImpl<FunctionBean>().getList(connection, sql, FunctionBean.class);
    }

    @Override
    public List<ContentBean> getText(Connection connection, String sql) throws BusinessException {
        return new FindImpl<ContentBean>().getList(connection, sql, ContentBean.class);
    }

}
