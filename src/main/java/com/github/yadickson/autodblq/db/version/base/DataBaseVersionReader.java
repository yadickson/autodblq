/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.version.base;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetObject;
import com.github.yadickson.autodblq.db.version.base.model.VersionBean;
import com.github.yadickson.autodblq.logger.LoggerManager;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseVersionReader {

    private final LoggerManager loggerManager;
    private final DataBaseVersionQueryFactory dataBaseVersionQueryFactory;
    private final SqlExecuteToGetObject sqlExecuteToGetObject;

    private String sqlQuery;

    @Inject
    public DataBaseVersionReader(
            LoggerManager loggerManager, final DataBaseVersionQueryFactory dataBaseVersionQueryFactory,
            final SqlExecuteToGetObject sqlExecuteToGetObject
    ) {
        this.loggerManager = loggerManager;
        this.dataBaseVersionQueryFactory = dataBaseVersionQueryFactory;
        this.sqlExecuteToGetObject = sqlExecuteToGetObject;
    }

    public String execute(final DriverConnection driverConnection) {

        try {

            findSqlQuery(driverConnection);
            return findVersion(driverConnection);

        } catch (RuntimeException ex) {
            throw new DataBaseVersionReaderException(ex);
        }
    }

    private void findSqlQuery(final DriverConnection driverConnection) {
        final Driver driver = driverConnection.getDriver();
        final DataBaseVersionQuery query = dataBaseVersionQueryFactory.apply(driver);
        sqlQuery = query.get();
        loggerManager.debug("[DataBaseVersionReader] SQL: " + sqlQuery);
    }

    private String findVersion(final DriverConnection driverConnection) {
        loggerManager.info(String.format("[DataBaseVersionReader] Starting [%s]", driverConnection.getDriver().getMessage()));
        final String version = sqlExecuteToGetObject.execute(driverConnection, sqlQuery, VersionBean.class).getVersion();
        loggerManager.info("[DataBaseVersionReader] Version: " + version);
        return version;
    }

}
