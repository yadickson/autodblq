/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.type.base;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;

import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetList;
import com.github.yadickson.autodblq.db.type.base.model.TypeBase;
import com.github.yadickson.autodblq.db.type.base.model.TypeBaseBean;
import com.github.yadickson.autodblq.logger.LoggerManager;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTypeBaseReader {

    private final LoggerManager loggerManager;
    private final DataBaseTypeBaseQueryFactory dataBaseTypeQueryFactory;
    private final SqlExecuteToGetList sqlExecuteToGetList;
    private final DataBaseTypeBaseMapper dataBaseTypeMapper;

    private String sqlQuery;
    private List<TypeBaseBean> allTypes;

    @Inject
    public DataBaseTypeBaseReader(
            LoggerManager loggerManager, final DataBaseTypeBaseQueryFactory dataBaseTypeQueryFactory,
            final SqlExecuteToGetList sqlExecuteToGetList,
            final DataBaseTypeBaseMapper dataBaseTypeMapper
    ) {
        this.loggerManager = loggerManager;
        this.dataBaseTypeQueryFactory = dataBaseTypeQueryFactory;
        this.sqlExecuteToGetList = sqlExecuteToGetList;
        this.dataBaseTypeMapper = dataBaseTypeMapper;
    }

    public List<TypeBase> execute(
            final List<String> filter,
            final DriverConnection driverConnection
    ) {

        try {

            if (CollectionUtils.isEmpty(filter)) {
                return new ArrayList<>();
            }

            findSqlQuery(filter, driverConnection);
            findTypes(driverConnection);
            return processTypes(driverConnection, filter);

        } catch (RuntimeException ex) {
            loggerManager.error(ex.getMessage(), ex);
            throw new DataBaseTypeBaseReaderException(ex);
        }
    }

    private void findSqlQuery(
            final List<String> filter,
            final DriverConnection driverConnection
    ) {
        final Driver driver = driverConnection.getDriver();
        final DataBaseTypeBaseQuery query = dataBaseTypeQueryFactory.apply(driver);
        sqlQuery = query.get(filter);
        loggerManager.debug("[DataBaseTypeBaseReader] SQL: " + sqlQuery);
    }

    private void findTypes(final DriverConnection driverConnection) {
        loggerManager.info("[DataBaseTypeBaseReader] Starting");
        allTypes = sqlExecuteToGetList.execute(driverConnection, sqlQuery, TypeBaseBean.class);
        loggerManager.info("[DataBaseTypeBaseReader] Total: " + allTypes.size());
    }

    private List<TypeBase> processTypes(final DriverConnection driverConnection, final List<String> filter) {
        List<TypeBase> types = dataBaseTypeMapper.apply(allTypes);
        types.sort(new DataBaseTypeBaseSort(filter));
        return types;
    }

}
