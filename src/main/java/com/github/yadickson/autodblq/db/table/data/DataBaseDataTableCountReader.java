/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.data;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetObject;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.data.model.CountBean;
import com.github.yadickson.autodblq.logger.LoggerManager;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseDataTableCountReader {

    private final LoggerManager loggerManager;
    private final SqlExecuteToGetObject sqlExecuteToGetObject;

    private String sqlQuery;

    @Inject
    public DataBaseDataTableCountReader(LoggerManager loggerManager, SqlExecuteToGetObject sqlExecuteToGetObject) {
        this.loggerManager = loggerManager;
        this.sqlExecuteToGetObject = sqlExecuteToGetObject;
    }

    public Long execute(final DriverConnection driverConnection, final TableBase table) {

        try {

            makeSqlQuery(table);
            return processCount(driverConnection);

        } catch (RuntimeException ex) {
            throw new DataBaseDataTableCountReaderException(ex);
        }
    }

    private void makeSqlQuery(final TableBase table) {
        sqlQuery = "SELECT COUNT(1) AS COUNT FROM " + table.getFullName();
        loggerManager.debug("[DataBaseDataTableCountReader] SQL: " + sqlQuery);
    }

    private Long processCount(final DriverConnection driverConnection) {
        final CountBean countBean = sqlExecuteToGetObject.execute(driverConnection, sqlQuery, CountBean.class);
        final String count = countBean.getCount();
        loggerManager.debug("[DataBaseDataTableCountReader] Count: " + count);
        return Long.valueOf(count);
    }

}
