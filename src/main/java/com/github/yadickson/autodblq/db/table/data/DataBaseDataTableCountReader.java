/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.data;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.data.model.CountBean;
import com.github.yadickson.autodblq.db.util.SqlExecuteToGetObject;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseDataTableCountReader {

    private static final Logger LOGGER = Logger.getLogger(DataBaseDataTableCountReader.class);

    private final SqlExecuteToGetObject sqlExecuteToGetObject;

    private String sqlQuery;

    @Inject
    public DataBaseDataTableCountReader(SqlExecuteToGetObject sqlExecuteToGetObject) {
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
        LOGGER.debug("[DataBaseDataTableCountReader] SQL: " + sqlQuery);
    }

    private Long processCount(final DriverConnection driverConnection) {
        final CountBean countBean = sqlExecuteToGetObject.execute(driverConnection, sqlQuery, CountBean.class);
        final String count = countBean.getCount();
        LOGGER.debug("[DataBaseDataTableCountReader] Count: " + count);
        return Long.valueOf(count);
    }

}
