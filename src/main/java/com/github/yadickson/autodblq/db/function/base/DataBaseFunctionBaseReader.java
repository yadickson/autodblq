/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.function.base;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.function.base.model.FunctionBase;
import com.github.yadickson.autodblq.db.function.base.model.FunctionBaseBean;
import com.github.yadickson.autodblq.db.util.SqlExecuteToGetList;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseFunctionBaseReader {

    private static final Logger LOGGER = Logger.getLogger(DataBaseFunctionBaseReader.class);

    private final DataBaseFunctionBaseQueryFactory dataBaseFunctionQueryFactory;
    private final SqlExecuteToGetList sqlExecuteToGetList;
    private final DataBaseFunctionBaseMapper dataBaseFunctionMapper;

    private String sqlQuery;
    private List<FunctionBaseBean> tables;

    @Inject
    public DataBaseFunctionBaseReader(
            final DataBaseFunctionBaseQueryFactory dataBaseFunctionQueryFactory,
            final SqlExecuteToGetList sqlExecuteToGetList,
            final DataBaseFunctionBaseMapper dataBaseFunctionMapper
    ) {
        this.dataBaseFunctionQueryFactory = dataBaseFunctionQueryFactory;
        this.sqlExecuteToGetList = sqlExecuteToGetList;
        this.dataBaseFunctionMapper = dataBaseFunctionMapper;
    }

    public List<FunctionBase> execute(
            final List<String> filter,
            final DriverConnection driverConnection
    ) {

        try {

            if (CollectionUtils.isEmpty(filter)) {
                return Collections.EMPTY_LIST;
            }

            findSqlQuery(filter, driverConnection);
            findFunctions(driverConnection);
            return processFunctions();

        } catch (RuntimeException ex) {
            throw new DataBaseFunctionBaseReaderException(ex);
        }
    }

    private void findSqlQuery(
            final List<String> filter,
            final DriverConnection driverConnection
    ) {
        final Driver driver = driverConnection.getDriver();
        final DataBaseFunctionBaseQuery query = dataBaseFunctionQueryFactory.apply(driver);
        sqlQuery = query.get(filter);
        LOGGER.debug("[DataBaseFunctionBaseReader] SQL: " + sqlQuery);
    }

    private void findFunctions(final DriverConnection driverConnection) {
        LOGGER.info("[DataBaseFunctionBaseReader] Starting");
        tables = sqlExecuteToGetList.execute(driverConnection, sqlQuery, FunctionBaseBean.class);
        LOGGER.info("[DataBaseFunctionBaseReader] Total: " + tables.size());
    }

    private List<FunctionBase> processFunctions() {
        return dataBaseFunctionMapper.apply(tables);
    }

}
