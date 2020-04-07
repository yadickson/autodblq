/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yadickson.autoplsp.db.util;

import java.sql.Connection;
import java.util.List;


import com.github.yadickson.autoplsp.db.bean.ViewBean;
import com.github.yadickson.autoplsp.db.bean.ContentBean;
import com.github.yadickson.autoplsp.handler.BusinessException;

/**
 *
 * @author Yadickson Soto
 */
public class FindViewImpl implements FindView {

    @Override
    public List<ViewBean> getViews(Connection connection, String sql) throws BusinessException {
        return new FindImpl<ViewBean>().getList(connection, sql, ViewBean.class);
    }

    @Override
    public List<ContentBean> getText(Connection connection, String sql) throws BusinessException {
        return new FindImpl<ContentBean>().getList(connection, sql, ContentBean.class);
    }

}
