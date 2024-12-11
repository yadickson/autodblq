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

import com.github.yadickson.autodblq.ParametersPlugin;
import com.github.yadickson.autodblq.db.DataBaseGeneratorType;
import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.sqlquery.SqlExecuteToGetList;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;

/**
 *
 * @author Yadickson Soto
 */
public abstract class DataBaseTableConstraintReader<T> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableConstraintReader.class);

    private final ParametersPlugin parametersPlugin;
    private final DataBaseGeneratorType type;
    private final DataBaseTableConstraintQueryFactory dataBaseTableConstraintQueryFactory;
    private final SqlExecuteToGetList sqlExecuteToGetList;
    private final DataBaseTableConstraintMapper<T> constraintMapper;

    private String sqlQuery;

    public DataBaseTableConstraintReader(
            final DataBaseGeneratorType type,
            final ParametersPlugin parametersPlugin,
            final DataBaseTableConstraintQueryFactory dataBaseTableConstraintQuery,
            final SqlExecuteToGetList sqlExecuteToGetList,
            final DataBaseTableConstraintMapper<T> constraintMapper
    ) {
        this.type = type;
        this.parametersPlugin = parametersPlugin;
        this.dataBaseTableConstraintQueryFactory = dataBaseTableConstraintQuery;
        this.sqlExecuteToGetList = sqlExecuteToGetList;
        this.constraintMapper = constraintMapper;
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
            elements.add(processTable(driverConnection, table));
        }

        return elements;
    }

    protected TableBase processTable(
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        findSqlQuery(driverConnection, table);
        return findElements(driverConnection, table);
    }

    private void findSqlQuery(
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        final Driver driver = driverConnection.getDriver();
        final DataBaseTableConstraintQuery query = dataBaseTableConstraintQueryFactory.apply(driver);
        sqlQuery = query.get(table, parametersPlugin.getKeepTypes());
        LOGGER.debug("[DataBaseTableConstraintReader] SQL " + type.getMessage() + ": " + sqlQuery);
    }

    private TableBase findElements(
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        LOGGER.debug("[DataBaseTableConstraintReader] Starting " + type.getMessage());
        List<T> elements = sqlExecuteToGetList.execute(driverConnection, sqlQuery, getTypeClass());
        LOGGER.debug("[DataBaseTableConstraintReader] Total " + type.getMessage() + ": " + elements.size());
        return constraintMapper.apply(table, elements);
    }

    protected abstract Class<T> getTypeClass();
}
