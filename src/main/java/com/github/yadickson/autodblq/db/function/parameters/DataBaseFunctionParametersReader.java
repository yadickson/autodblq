/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.function.parameters;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.function.base.model.FunctionBase;
import com.github.yadickson.autodblq.db.function.parameters.model.FunctionParameterBean;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetList;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintReaderException;
import com.github.yadickson.autodblq.logger.LoggerManager;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseFunctionParametersReader {

    private final LoggerManager loggerManager;
    private final DataBaseFunctionParametersQueryFactory dataBaseFunctionParametersQueryFactory;
    private final SqlExecuteToGetList sqlExecuteToGetList;
    private final DataBaseFunctionParametersMapper dataBaseFunctionParametersMapper;

    private String sqlQuery;
    private List<FunctionParameterBean> parameters;

    @Inject
    public DataBaseFunctionParametersReader(
            LoggerManager loggerManager, final DataBaseFunctionParametersQueryFactory dataBaseFunctionParametersQueryFactory,
            final SqlExecuteToGetList sqlExecuteToGetList,
            final DataBaseFunctionParametersMapper dataBaseFunctionParametersMapper
    ) {
        this.loggerManager = loggerManager;
        this.dataBaseFunctionParametersQueryFactory = dataBaseFunctionParametersQueryFactory;
        this.sqlExecuteToGetList = sqlExecuteToGetList;
        this.dataBaseFunctionParametersMapper = dataBaseFunctionParametersMapper;
    }

    public List<FunctionBase> execute(final DriverConnection driverConnection, final List<FunctionBase> functions) {

        try {

            return processFunctions(driverConnection, functions);

        } catch (RuntimeException ex) {
            throw new DataBaseTableConstraintReaderException(ex);
        }

    }

    private List<FunctionBase> processFunctions(
            final DriverConnection driverConnection,
            final List<FunctionBase> functions
    ) {
        List<FunctionBase> functionParameters = new ArrayList<>();

        for (FunctionBase function : functions) {
            FunctionBase functionBase = processFunction(driverConnection, function);
            functionParameters.add(functionBase);
        }

        return functionParameters;
    }

    protected FunctionBase processFunction(
            final DriverConnection driverConnection,
            final FunctionBase function
    ) {
        findSqlQuery(driverConnection, function);
        findDefinitions(driverConnection);
        return fillDefinitions(function);
    }

    private void findSqlQuery(
            final DriverConnection driverConnection,
            final FunctionBase function
    ) {
        final Driver driver = driverConnection.getDriver();
        final DataBaseFunctionParametersQuery query = dataBaseFunctionParametersQueryFactory.apply(driver);
        sqlQuery = query.get(function);
        loggerManager.debug("[DataBaseFunctionParametersReader] SQL: " + sqlQuery);
    }

    private void findDefinitions(final DriverConnection driverConnection) {
        loggerManager.info("[DataBaseFunctionParametersReader] Starting");
        parameters = sqlExecuteToGetList.execute(driverConnection, sqlQuery, FunctionParameterBean.class);
        loggerManager.info("[DataBaseFunctionParametersReader] Total: " + parameters.size());
    }

    private FunctionBase fillDefinitions(final FunctionBase function) {
        return new DataBaseFunctionParametersWrapper(function, dataBaseFunctionParametersMapper.apply(parameters));
    }

}
