/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.function.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.DataBaseGeneratorType;
import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.function.base.model.FunctionBase;
import com.github.yadickson.autodblq.db.function.base.model.FunctionBaseBean;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetList;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetListFactory;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseFunctionBaseReader {

    private static final Logger LOGGER = Logger.getLogger(DataBaseFunctionBaseReader.class);

    private final DataBaseFunctionBaseQueryFactory dataBaseFunctionQueryFactory;
    private final SqlExecuteToGetList sqlExecuteToGetList;
    private final DataBaseFunctionBaseMapper dataBaseFunctionMapper;

    private String sqlQuery;
    private final List<FunctionBaseBean> allFunctions = new ArrayList<>();

    @Inject
    public DataBaseFunctionBaseReader(
            final DataBaseFunctionBaseQueryFactory dataBaseFunctionQueryFactory,
            final SqlExecuteToGetListFactory sqlExecuteToGetListFactory,
            final DataBaseFunctionBaseMapper dataBaseFunctionMapper
    ) {
        this.dataBaseFunctionQueryFactory = dataBaseFunctionQueryFactory;
        this.sqlExecuteToGetList = sqlExecuteToGetListFactory.apply(DataBaseGeneratorType.FUNCTION_DEFINITION);
        this.dataBaseFunctionMapper = dataBaseFunctionMapper;
    }

    public List<FunctionBase> execute(
            final List<String> filter,
            final DriverConnection driverConnection
    ) {

        try {

            allFunctions.clear();

            if (CollectionUtils.isEmpty(filter)) {
                return Collections.EMPTY_LIST;
            }

            findAndAddFunctions(filter, driverConnection);
            findAndAddProcedures(filter, driverConnection);

            return processFunctions();

        } catch (RuntimeException ex) {
            throw new DataBaseFunctionBaseReaderException(ex);
        }
    }

    private void findAndAddFunctions(
            final List<String> filter,
            final DriverConnection driverConnection
    ) {
        findSqlFunctionsQuery(filter, driverConnection);
        allFunctions.addAll(findFunctions(driverConnection));
    }

    private void findAndAddProcedures(
            final List<String> filter,
            final DriverConnection driverConnection
    ) {
        findSqlProceduresQuery(filter, driverConnection);
        allFunctions.addAll(findProcedures(driverConnection));
    }

    private void findSqlFunctionsQuery(
            final List<String> filter,
            final DriverConnection driverConnection
    ) {
        final Driver driver = driverConnection.getDriver();
        final DataBaseFunctionBaseQuery query = dataBaseFunctionQueryFactory.apply(driver);
        sqlQuery = query.getFunctions(filter);
        LOGGER.debug("[DataBaseFunctionBaseReader] SQL: " + sqlQuery);
    }

    private List<FunctionBaseBean> findFunctions(final DriverConnection driverConnection) {
        LOGGER.info("[DataBaseFunctionBaseReader] Starting");
        final List<FunctionBaseBean> functions = sqlExecuteToGetList.execute(driverConnection, sqlQuery);
        LOGGER.info("[DataBaseFunctionBaseReader] Total Functions: " + functions.size());
        return functions;
    }

    private void findSqlProceduresQuery(
            final List<String> filter,
            final DriverConnection driverConnection
    ) {
        final Driver driver = driverConnection.getDriver();
        final DataBaseFunctionBaseQuery query = dataBaseFunctionQueryFactory.apply(driver);
        sqlQuery = query.getProcedures(filter);
        LOGGER.debug("[DataBaseFunctionBaseReader] SQL: " + sqlQuery);
    }

    private List<FunctionBaseBean> findProcedures(final DriverConnection driverConnection) {
        LOGGER.info("[DataBaseFunctionBaseReader] Starting");
        List<FunctionBaseBean> procedures = sqlExecuteToGetList.execute(driverConnection, sqlQuery);
        LOGGER.info("[DataBaseFunctionBaseReader] Total Procedures: " + procedures.size());
        return procedures;
    }

    private List<FunctionBase> processFunctions() {
        return dataBaseFunctionMapper.apply(allFunctions);
    }

}
