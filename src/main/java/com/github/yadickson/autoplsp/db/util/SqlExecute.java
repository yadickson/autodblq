/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.yadickson.autoplsp.db.util;

import java.sql.Connection;
import java.util.List;

import com.github.yadickson.autoplsp.handler.BusinessException;

/**
 * Sql execute definition.
 *
 * @author Yadickson Soto
 */
public interface SqlExecute {

    /**
     * Method to execute sql and make output list bean.
     *
     * @param <T> template
     * @param connection database connection
     * @param sql sql to execute
     * @param clazz class definition bean
     * @return bean list
     * @throws BusinessException if error.
     */
    <T> List<T> execute(
            final Connection connection,
            final String sql,
            final Class<T> clazz
    ) throws BusinessException;

}
