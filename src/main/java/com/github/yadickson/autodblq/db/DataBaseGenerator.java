/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.ParametersPlugin;
import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.function.base.DataBaseFunctionBaseReader;
import com.github.yadickson.autodblq.db.function.base.model.FunctionBase;
import com.github.yadickson.autodblq.db.property.DataBaseProperty;
import com.github.yadickson.autodblq.db.table.base.DataBaseTableBaseReader;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintChain;
import com.github.yadickson.autodblq.db.property.DataBasePropertyManager;
import com.github.yadickson.autodblq.db.version.base.DataBaseVersionReader;
import com.github.yadickson.autodblq.db.view.base.DataBaseViewBaseReader;
import com.github.yadickson.autodblq.db.view.base.model.ViewBase;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseGenerator {

    private final ParametersPlugin parametersPlugin;
    private final DataBaseVersionReader dataBaseVersionReader;
    private final DataBaseTableBaseReader dataBaseTableBaseReader;
    private final DataBaseTableConstraintChain dataBaseTableConstraintReader;
    private final DataBaseViewBaseReader dataBaseViewBaseReader;
    private final DataBaseFunctionBaseReader dataBaseFunctionBaseReader;
    private final DataBasePropertyManager dataBasePropertyManager;

    private final Map<DataBaseGeneratorType, Object> result = new HashMap<>();

    @Inject
    public DataBaseGenerator(
            ParametersPlugin parametersPlugin, final DataBaseVersionReader dataBaseVersionReader,
            final DataBaseTableBaseReader dataBaseTableBaseReader,
            final DataBaseTableConstraintChain dataBaseTableConstraintReader,
            final DataBaseViewBaseReader dataBaseViewBaseReader,
            final DataBaseFunctionBaseReader dataBaseFunctionBaseReader,
            final DataBasePropertyManager dataBasePropertyManager
    ) {
        this.parametersPlugin = parametersPlugin;
        this.dataBaseVersionReader = dataBaseVersionReader;
        this.dataBaseTableBaseReader = dataBaseTableBaseReader;
        this.dataBaseTableConstraintReader = dataBaseTableConstraintReader;
        this.dataBaseViewBaseReader = dataBaseViewBaseReader;
        this.dataBaseFunctionBaseReader = dataBaseFunctionBaseReader;
        this.dataBasePropertyManager = dataBasePropertyManager;
    }

    public Map<DataBaseGeneratorType, Object> execute(final DriverConnection driverConnection) {

        try {

            findVersion(driverConnection);
            findTables(driverConnection);
            findDataTables(driverConnection);
            findViews(driverConnection);
            findFunctions(driverConnection);

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

    private void findTables(final DriverConnection driverConnection) {
        final DataBaseGeneratorType key = DataBaseGeneratorType.TABLE_DEFINITION;
        final List<TableBase> tables = dataBaseTableBaseReader.execute(parametersPlugin.getTables(), driverConnection);
        dataBasePropertyManager.accept(Collections.unmodifiableList(tables));
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

        dataBasePropertyManager.accept(Collections.unmodifiableList(allTables));
    }

    private void findDataTables(final DriverConnection driverConnection) {
        final DataBaseGeneratorType key = DataBaseGeneratorType.DATA_DEFINITION;
        final List<TableBase> tables = dataBaseTableBaseReader.execute(parametersPlugin.getDataTables(), driverConnection);
        result.put(key, tables);
    }

    private void findViews(final DriverConnection driverConnection) {
        final DataBaseGeneratorType key = DataBaseGeneratorType.VIEW_DEFINITION;
        final List<ViewBase> views = dataBaseViewBaseReader.execute(parametersPlugin.getViews(), driverConnection);
        result.put(key, views);
    }

    private void findFunctions(DriverConnection driverConnection) {
        final DataBaseGeneratorType key = DataBaseGeneratorType.FUNCTION_DEFINITION;
        final List<FunctionBase> functions = dataBaseFunctionBaseReader.execute(parametersPlugin.getFunctions(), driverConnection);
        result.put(key, functions);

        if (!functions.isEmpty()) {
            dataBasePropertyManager.addFunctionDefinitions();
            dataBasePropertyManager.accept(Collections.unmodifiableList(functions));
        }
    }

}
