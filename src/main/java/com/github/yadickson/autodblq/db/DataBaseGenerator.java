/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.Parameters;
import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.function.base.DataBaseFunctionBaseReader;
import com.github.yadickson.autodblq.db.function.base.model.FunctionBase;
import com.github.yadickson.autodblq.db.table.base.DataBaseTableBaseReader;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintChain;
import com.github.yadickson.autodblq.db.table.property.DataBaseTablePropertiesMapper;
import com.github.yadickson.autodblq.db.table.property.model.TablePropertyType;
import com.github.yadickson.autodblq.db.version.base.DataBaseVersionReader;
import com.github.yadickson.autodblq.db.view.base.DataBaseViewBaseReader;
import com.github.yadickson.autodblq.db.view.base.model.ViewBase;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseGenerator {

    private final DataBaseVersionReader dataBaseVersionReader;
    private final DataBaseTableBaseReader dataBaseTableBaseReader;
    private final DataBaseTablePropertiesMapper dataBaseTablePropertiesMapper;
    private final DataBaseTableConstraintChain dataBaseTableConstraintReader;
    private final DataBaseViewBaseReader dataBaseViewBaseReader;
    private final DataBaseFunctionBaseReader dataBaseFunctionBaseReader;

    private final Map<DataBaseGeneratorType, Object> result = new HashMap<>();

    @Inject
    public DataBaseGenerator(
            final DataBaseVersionReader dataBaseVersionReader,
            final DataBaseTableBaseReader dataBaseTableBaseReader,
            final DataBaseTablePropertiesMapper dataBaseTablePropertiesMapper,
            final DataBaseTableConstraintChain dataBaseTableConstraintReader,
            final DataBaseViewBaseReader dataBaseViewBaseReader,
            final DataBaseFunctionBaseReader dataBaseFunctionBaseReader
    ) {
        this.dataBaseVersionReader = dataBaseVersionReader;
        this.dataBaseTableBaseReader = dataBaseTableBaseReader;
        this.dataBaseTablePropertiesMapper = dataBaseTablePropertiesMapper;
        this.dataBaseTableConstraintReader = dataBaseTableConstraintReader;
        this.dataBaseViewBaseReader = dataBaseViewBaseReader;
        this.dataBaseFunctionBaseReader = dataBaseFunctionBaseReader;
    }

    public Map<DataBaseGeneratorType, Object> execute(final Parameters parameters, final DriverConnection driverConnection) {

        try {

            findVersion(driverConnection);
            findTables(parameters, driverConnection);
            findDataTables(parameters, driverConnection);
            findViews(parameters, driverConnection);
            findFunctions(parameters, driverConnection);

            return result;

        } catch (RuntimeException ex) {
            throw new DataBaseGeneratorException(ex);
        }

    }

    private void findVersion(final DriverConnection driverConnection) {
        final DataBaseGeneratorType key = DataBaseGeneratorType.VERSION;
        final String version = dataBaseVersionReader.execute(driverConnection);
        result.put(key, version);
    }

    private void findTables(final Parameters parameters, final DriverConnection driverConnection) {
        final DataBaseGeneratorType key = DataBaseGeneratorType.TABLE_DEFINITION;
        final List<TableBase> tables = dataBaseTableBaseReader.execute(parameters.getTables(), driverConnection);
        result.put(key, tables);
        findConstraints(driverConnection, tables);
    }

    private void findConstraints(final DriverConnection driverConnection, final List<TableBase> tables) {
        final Map<DataBaseGeneratorType, List<TableBase>> response = dataBaseTableConstraintReader.execute(driverConnection, tables);
        result.putAll(response);

        List<TableBase> allTables = response
                .values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        allTables.addAll(tables);

        findProperties(allTables);
    }

    private void findProperties(final List<TableBase> tables) {
        final DataBaseGeneratorType key = DataBaseGeneratorType.TABLE_PROPERTIES;
        Map<String, List<TablePropertyType>> properties = dataBaseTablePropertiesMapper.apply(tables);
        result.put(key, properties);
    }

    private void findDataTables(final Parameters parameters, final DriverConnection driverConnection) {
        final DataBaseGeneratorType key = DataBaseGeneratorType.DATA_DEFINITION;
        final List<TableBase> tables = dataBaseTableBaseReader.execute(parameters.getDataTables(), driverConnection);
        result.put(key, tables);
    }

    private void findViews(final Parameters parameters, final DriverConnection driverConnection) {
        final DataBaseGeneratorType key = DataBaseGeneratorType.VIEW_DEFINITION;
        final List<ViewBase> views = dataBaseViewBaseReader.execute(parameters.getViews(), driverConnection);
        result.put(key, views);
    }

    private void findFunctions(Parameters parameters, DriverConnection driverConnection) {
        final DataBaseGeneratorType key = DataBaseGeneratorType.FUNCTION_DEFINITION;
        final List<FunctionBase> functions = dataBaseFunctionBaseReader.execute(parameters.getFunctions(), driverConnection);
        result.put(key, functions);
    }

}
