/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.DataBaseGeneratorType;
import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetList;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetListFactory;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.defaults.model.TableDefaultBean;

/**
 *
 * @author Yadickson Soto
 */
public abstract class DataBaseTableConstraintReader {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableConstraintReader.class);

    private final DataBaseGeneratorType type;
    private final DataBaseTableConstraintQueryFactory dataBaseTableConstraintQueryFactory;
    private final SqlExecuteToGetList sqlExecuteToGetList;
    private final DataBaseTableConstraintMapper mapper;

    private String sqlQuery;
    private List<TableBase> constraints;

    public DataBaseTableConstraintReader(
            final DataBaseGeneratorType type,
            final DataBaseTableConstraintQueryFactory dataBaseTableConstraintQuery,
            final SqlExecuteToGetListFactory sqlExecuteToGetListFactory,
            final DataBaseTableConstraintMapper mapper
    ) {
        this.type = type;
        this.dataBaseTableConstraintQueryFactory = dataBaseTableConstraintQuery;
        this.sqlExecuteToGetList = sqlExecuteToGetListFactory.apply(type);
        this.mapper = mapper;
    }

    public Pair<DataBaseGeneratorType, List<TableBase>> execute(final DriverConnection driverConnection, final List<TableBase> tables) {

        try {

            return new ImmutablePair<>(type, processTables(driverConnection, tables));

        } catch (RuntimeException ex) {
            throw new DataBaseTableConstraintReaderException(ex);
        }

    }

    private List<TableBase> processTables(
            final DriverConnection driverConnection,
            final List<TableBase> tables
    ) {
        List<TableBase> elements = new ArrayList<>();

        for (TableBase table : tables) {
            processTable(driverConnection, table);
            elements.addAll(constraints);
        }

        return elements;
    }

    protected void processTable(
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        findSqlQuery(driverConnection, table);
        findElements(driverConnection, table);
    }

    private void findSqlQuery(
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        final Driver driver = driverConnection.getDriver();
        final DataBaseTableConstraintQuery query = dataBaseTableConstraintQueryFactory.apply(driver);
        sqlQuery = query.get(table);
        LOGGER.debug("[DataBaseTableConstraintReader] SQL " + type.getMessage() + ": " + sqlQuery);
    }

    private void findElements(
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        LOGGER.debug("[DataBaseTableConstraintReader] Starting " + type.getMessage());
        List<TableDefaultBean> elements = sqlExecuteToGetList.execute(driverConnection, sqlQuery);
        LOGGER.debug("[DataBaseTableConstraintReader] Total " + type.getMessage() + ": " + elements.size());
        constraints = mapper.apply(table, elements);
    }

}
