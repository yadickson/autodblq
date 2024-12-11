/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.function.base;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;

import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.function.base.model.FunctionBase;
import com.github.yadickson.autodblq.db.function.base.model.FunctionBaseBean;
import com.github.yadickson.autodblq.db.function.parameters.DataBaseFunctionParametersReader;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetList;
import com.github.yadickson.autodblq.logger.LoggerManager;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseFunctionBaseReader {

    private final LoggerManager loggerManager;
    private final DataBaseFunctionBaseQueryFactory dataBaseFunctionQueryFactory;
    private final SqlExecuteToGetList sqlExecuteToGetList;
    private final DataBaseFunctionBaseMapper dataBaseFunctionMapper;
    private final DataBaseFunctionParametersReader dataBaseFunctionParametersReader;

    private String sqlQuery;
    private final List<FunctionBaseBean> allFunctions = new ArrayList<>();

    @Inject
    public DataBaseFunctionBaseReader(
            LoggerManager loggerManager, final DataBaseFunctionBaseQueryFactory dataBaseFunctionQueryFactory,
            final SqlExecuteToGetList sqlExecuteToGetList,
            final DataBaseFunctionBaseMapper dataBaseFunctionMapper,
            final DataBaseFunctionParametersReader dataBaseFunctionParametersReader
    ) {
        this.loggerManager = loggerManager;
        this.dataBaseFunctionQueryFactory = dataBaseFunctionQueryFactory;
        this.sqlExecuteToGetList = sqlExecuteToGetList;
        this.dataBaseFunctionMapper = dataBaseFunctionMapper;
        this.dataBaseFunctionParametersReader = dataBaseFunctionParametersReader;
    }

    public List<FunctionBase> execute(
            final List<String> filter,
            final DriverConnection driverConnection
    ) {

        try {

            allFunctions.clear();

            if (CollectionUtils.isEmpty(filter)) {
                return new ArrayList<>();
            }

            findFunctions(filter, driverConnection);
            return processFunctions(driverConnection, filter);

        } catch (RuntimeException ex) {
            throw new DataBaseFunctionBaseReaderException(ex);
        }
    }

    private void findFunctions(
            final List<String> filter,
            final DriverConnection driverConnection
    ) {
        findSqlFunctionsQuery(filter, driverConnection);
        allFunctions.addAll(findFunctions(driverConnection));
    }

    private void findSqlFunctionsQuery(
            final List<String> filter,
            final DriverConnection driverConnection
    ) {
        final Driver driver = driverConnection.getDriver();
        final DataBaseFunctionBaseQuery query = dataBaseFunctionQueryFactory.apply(driver);
        sqlQuery = query.getFunctions(filter);
        loggerManager.debug("[DataBaseFunctionBaseReader] SQL: " + sqlQuery);
    }

    private List<FunctionBaseBean> findFunctions(final DriverConnection driverConnection) {
        loggerManager.info("[DataBaseFunctionBaseReader] Starting");
        final List<FunctionBaseBean> functions = sqlExecuteToGetList.execute(driverConnection, sqlQuery, FunctionBaseBean.class);
        loggerManager.info("[DataBaseFunctionBaseReader] Total Functions: " + functions.size());
        return functions;
    }

    private List<FunctionBase> processFunctions(final DriverConnection driverConnection, final List<String> filter) {
        List<FunctionBase> results = dataBaseFunctionMapper.apply(allFunctions);
        List<FunctionBase> functions = dataBaseFunctionParametersReader.execute(driverConnection, results);
        functions.sort(new DataBaseFunctionBaseSort(filter));
        return functions;
    }

}
