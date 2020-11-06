/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.version.base;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetObject;
import com.github.yadickson.autodblq.db.version.base.model.VersionBean;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseVersionReader {

    private static final Logger LOGGER = Logger.getLogger(DataBaseVersionReader.class);

    private final DataBaseVersionQueryFactory dataBaseVersionQueryFactory;
    private final SqlExecuteToGetObject sqlExecuteToGetObject;

    private String sqlQuery;

    @Inject
    public DataBaseVersionReader(
            final DataBaseVersionQueryFactory dataBaseVersionQueryFactory,
            final SqlExecuteToGetObject sqlExecuteToGetObject
    ) {
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
        LOGGER.debug("[DataBaseVersionReader] SQL: " + sqlQuery);
    }

    private String findVersion(final DriverConnection driverConnection) {
        LOGGER.info("[DataBaseVersionReader] Starting");
        final String version = sqlExecuteToGetObject.execute(driverConnection, sqlQuery, VersionBean.class).getVersion();
        LOGGER.info("[DataBaseVersionReader] Version: " + version);
        return version;
    }

}
