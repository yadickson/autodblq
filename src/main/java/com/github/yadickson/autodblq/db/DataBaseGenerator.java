/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.github.yadickson.autodblq.Parameters;
import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.function.base.DataBaseFunctionBaseReader;
import com.github.yadickson.autodblq.db.function.base.model.FunctionBase;
import com.github.yadickson.autodblq.db.table.DataBaseTableChain;
import com.github.yadickson.autodblq.db.table.base.DataBaseTableBaseReader;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.version.base.DataBaseVersionReader;
import com.github.yadickson.autodblq.db.view.base.DataBaseViewBaseReader;
import com.github.yadickson.autodblq.db.view.base.model.ViewBase;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseGenerator {

    private final DataBaseVersionReader dataBaseVersionHandler;
    private final DataBaseTableBaseReader dataBaseTableBaseReader;
    private final DataBaseTableChain dataBaseTableChain;
    private final DataBaseViewBaseReader dataBaseViewBaseReader;
    private final DataBaseFunctionBaseReader dataBaseFunctionBaseReader;

    private final Map<DataBaseGeneratorType, Object> result = new HashMap<>();

    @Inject
    public DataBaseGenerator(
            final DataBaseVersionReader dataBaseVersionHandler,
            final DataBaseTableBaseReader dataBaseTableBaseReader,
            final DataBaseTableChain dataBaseTableChain,
            final DataBaseViewBaseReader dataBaseViewBaseReader,
            final DataBaseFunctionBaseReader dataBaseFunctionBaseReader
    ) {
        this.dataBaseVersionHandler = dataBaseVersionHandler;
        this.dataBaseTableBaseReader = dataBaseTableBaseReader;
        this.dataBaseTableChain = dataBaseTableChain;
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
        final String version = dataBaseVersionHandler.execute(driverConnection);
        result.put(key, version);
    }

    private void findTables(final Parameters parameters, final DriverConnection driverConnection) {
        final DataBaseGeneratorType key = DataBaseGeneratorType.TABLE_DEFINITION;
        final List<TableBase> tables = dataBaseTableBaseReader.execute(parameters.getTables(), driverConnection);
        result.put(key, tables);

        final Map<DataBaseGeneratorType, List<TableBase>> response = dataBaseTableChain.execute(driverConnection, tables);
        result.putAll(response);
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
