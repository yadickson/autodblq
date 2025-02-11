/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.sequence.base;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;

import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.sequence.base.model.SequenceBase;
import com.github.yadickson.autodblq.db.sequence.base.model.SequenceBaseBean;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetList;
import com.github.yadickson.autodblq.logger.LoggerManager;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseSequenceBaseReader {

    private final LoggerManager loggerManager;
    private final DataBaseSequenceBaseQueryFactory dataBaseTypeQueryFactory;
    private final SqlExecuteToGetList sqlExecuteToGetList;
    private final DataBaseSequenceBaseMapper dataBaseTypeMapper;

    private String sqlQuery;
    private List<SequenceBaseBean> allTypes;

    @Inject
    public DataBaseSequenceBaseReader(
            LoggerManager loggerManager, final DataBaseSequenceBaseQueryFactory dataBaseTypeQueryFactory,
            final SqlExecuteToGetList sqlExecuteToGetList,
            final DataBaseSequenceBaseMapper dataBaseTypeMapper
    ) {
        this.loggerManager = loggerManager;
        this.dataBaseTypeQueryFactory = dataBaseTypeQueryFactory;
        this.sqlExecuteToGetList = sqlExecuteToGetList;
        this.dataBaseTypeMapper = dataBaseTypeMapper;
    }

    public List<SequenceBase> execute(
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
            throw new DataBaseSequenceBaseReaderException(ex);
        }
    }

    private void findSqlQuery(
            final List<String> filter,
            final DriverConnection driverConnection
    ) {
        final Driver driver = driverConnection.getDriver();
        final DataBaseSequenceBaseQuery query = dataBaseTypeQueryFactory.apply(driver);
        sqlQuery = query.get(filter);
        loggerManager.debug("[DataBaseSequenceBaseReader] SQL: " + sqlQuery);
    }

    private void findTypes(final DriverConnection driverConnection) {
        loggerManager.info("[DataBaseSequenceBaseReader] Starting");
        allTypes = sqlExecuteToGetList.execute(driverConnection, sqlQuery, SequenceBaseBean.class);
        loggerManager.info("[DataBaseSequenceBaseReader] Total Sequences: " + allTypes.size());
    }

    private List<SequenceBase> processTypes(final DriverConnection driverConnection, final List<String> filter) {
        List<SequenceBase> types = dataBaseTypeMapper.apply(allTypes);
        types.sort(new DataBaseSequenceBaseSort(filter));
        return types;
    }

}
