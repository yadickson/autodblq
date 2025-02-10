/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.writer.table;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.ParametersPlugin;
import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.data.DataBaseDataTableCountReader;
import com.github.yadickson.autodblq.db.table.data.DataBaseDataTableReader;
import com.github.yadickson.autodblq.db.table.data.DataBaseDataTableReaderIterator;
import com.github.yadickson.autodblq.db.type.base.model.TypeBase;
import com.github.yadickson.autodblq.writer.DefinitionGeneratorType;
import com.github.yadickson.autodblq.writer.template.TemplateGenerator;
import com.github.yadickson.autodblq.writer.template.TemplateGeneratorManager;
import com.github.yadickson.autodblq.writer.util.TableColumnTypeUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataTableGenerator {

    private final ParametersPlugin parametersPlugin;
    private final DataBaseDataTableCountReader dataBaseDataTableCountReader;
    private final DataBaseDataTableReader dataBaseDataTableReader;
    private final TemplateGenerator templateGenerator;

    private String outputDirectory;

    private final String TABLES = "tables";
    private static final String TYPE_UTIL = "typeUtil";

    @Inject
    public DataTableGenerator(
            ParametersPlugin parametersPlugin, final DataBaseDataTableCountReader dataBaseDataTableCountReader,
            final DataBaseDataTableReader dataBaseDataTableBlockReader,
            final TemplateGenerator templateGenerator
    ) {
        this.parametersPlugin = parametersPlugin;
        this.dataBaseDataTableCountReader = dataBaseDataTableCountReader;
        this.dataBaseDataTableReader = dataBaseDataTableBlockReader;
        this.templateGenerator = templateGenerator;
    }

    public void execute(final DriverConnection driverConnection, final List<TypeBase> types, final List<TableBase> tables) {

        try {

            if (tables.isEmpty()) {
                return;
            }

            makeOutputDirectory();
            makeDataTables(driverConnection, types, tables);

        } catch (RuntimeException ex) {
            throw new DataTableGeneratorException(ex);
        }
    }

    private void makeOutputDirectory() {
        outputDirectory = parametersPlugin.getOutputDirectory() + File.separatorChar + parametersPlugin.getVersion() + File.separatorChar;
    }

    private void makeDataTables(final DriverConnection driverConnection, final List<TypeBase> types, final List<TableBase> tables) {
        for (TableBase table : tables) {
            readTotalCount(driverConnection, table);
            makeDataTable(driverConnection, types, table);
        }
    }

    private void readTotalCount(final DriverConnection driverConnection, final TableBase table) {
        dataBaseDataTableCountReader.execute(driverConnection, table);
    }

    private void makeDataTable(final DriverConnection driverConnection, final List<TypeBase> types, final TableBase table) {
        final DefinitionGeneratorType type = DefinitionGeneratorType.DATA_INSERT_TABLE;
        final String filename = String.format(type.getFilename(), table.getName());
        final String path = outputDirectory + File.separatorChar + parametersPlugin.getOutputDatasetsDirectory() + File.separatorChar + filename;
        readerTable(driverConnection, types, table, path);
    }

    private void readerTable(final DriverConnection driverConnection, final List<TypeBase> types, final TableBase table, final String path) {
        DataBaseDataTableReaderIterator iterator = dataBaseDataTableReader.execute(driverConnection, table);

        while (iterator.nextBlock(types)) {
            List<TableBase> tables = iterator.getBlock();
            writeFile(path, tables);
        }
    }

    private void writeFile(final String path, final List<TableBase> tables) {

        Map<String, Object> values = new HashMap<>();

        values.put(TABLES, tables);
        values.put(TYPE_UTIL, new TableColumnTypeUtil());

        templateGenerator.execute(DefinitionGeneratorType.DATA_INSERT_TABLE, values, path, true);
    }

}
