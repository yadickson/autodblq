/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.writer.table;

import java.io.File;
import java.util.*;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.ParametersPlugin;
import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.data.DataBaseDataTableCountReader;
import com.github.yadickson.autodblq.db.table.data.DataBaseDataTableReader;
import com.github.yadickson.autodblq.db.table.data.DataBaseDataTableReaderIterator;
import com.github.yadickson.autodblq.db.table.data.model.DataBaseTableDataWrapper;
import com.github.yadickson.autodblq.db.type.base.model.TypeBase;
import com.github.yadickson.autodblq.directory.DirectoryBuilder;
import com.github.yadickson.autodblq.logger.LoggerManager;
import com.github.yadickson.autodblq.util.StringToLongUtil;
import com.github.yadickson.autodblq.writer.DefinitionGeneratorType;
import com.github.yadickson.autodblq.writer.template.TemplateGenerator;
import com.github.yadickson.autodblq.writer.util.TableColumnTypeUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataTableGenerator {

    private final LoggerManager loggerManager;
    private final ParametersPlugin parametersPlugin;
    private final DataBaseDataTableCountReader dataBaseDataTableCountReader;
    private final DataBaseDataTableReader dataBaseDataTableReader;
    private final TemplateGenerator templateGenerator;
    private final DirectoryBuilder directoryBuilder;
    private final StringToLongUtil stringToLongUtil;

    private String outputDirectory;

    private final String TABLE = "table";
    private final String TABLES = "tables";
    private Long total = 0L;
    private static final String TYPE_UTIL = "typeUtil";

    @Inject
    public DataTableGenerator(
            LoggerManager loggerManager, ParametersPlugin parametersPlugin, final DataBaseDataTableCountReader dataBaseDataTableCountReader,
            final DataBaseDataTableReader dataBaseDataTableBlockReader,
            final TemplateGenerator templateGenerator, DirectoryBuilder directoryBuilder, StringToLongUtil stringToLongUtil
    ) {
        this.loggerManager = loggerManager;
        this.parametersPlugin = parametersPlugin;
        this.dataBaseDataTableCountReader = dataBaseDataTableCountReader;
        this.dataBaseDataTableReader = dataBaseDataTableBlockReader;
        this.templateGenerator = templateGenerator;
        this.directoryBuilder = directoryBuilder;
        this.stringToLongUtil = stringToLongUtil;
    }

    public List<TableBase> execute(final DriverConnection driverConnection, final List<TypeBase> types, final List<TableBase> tables) {

        try {

            if (tables.isEmpty()) {
                return Collections.emptyList();
            }

            makeOutputDirectory();
            return makeDataTables(driverConnection, types, tables);

        } catch (RuntimeException ex) {
            throw new DataTableGeneratorException(ex);
        }
    }

    private void makeOutputDirectory() {
        outputDirectory = parametersPlugin.getOutputDirectory() + File.separatorChar + parametersPlugin.getVersion() + File.separatorChar;
    }

    private List<TableBase> makeDataTables(final DriverConnection driverConnection, final List<TypeBase> types, final List<TableBase> tables) {

        List<TableBase> newTables = new ArrayList<>();

        for (TableBase table : tables) {
            readTotalCount(driverConnection, table);
            newTables.add(makeDataTable(driverConnection, types, table));
        }

        return newTables;
    }

    private void readTotalCount(final DriverConnection driverConnection, final TableBase table) {
        total = dataBaseDataTableCountReader.execute(driverConnection, table);
    }

    private TableBase makeDataTable(final DriverConnection driverConnection, final List<TypeBase> types, final TableBase table) {
        List<String> files = new ArrayList<>();

        DataBaseDataTableReaderIterator iterator = dataBaseDataTableReader.execute(driverConnection, table);
        final Long block = stringToLongUtil.apply(parametersPlugin.getOutputDatasetBlockSize());
        final long blocks = Math.round(Math.ceil(total / block.floatValue()));

        int count = 0;

        while (iterator.nextBlock(types)) {
            List<TableBase> tables = iterator.getBlock();

            files.add(getSourceFile(DefinitionGeneratorType.DATA_TABLE, table, ++count));

            loggerManager.info("[DataTableGenerator] Table: " + table.getFullName() + " Total [" + total + "] - Block [" + count + "/" + blocks + "]");

            makeDataTableFile(table, count);
            makeInsertDataTableFile(table, tables, count);
        }

        return new DataBaseTableDataWrapper(table, files);
    }

    private String getSourceFile(DefinitionGeneratorType type, final TableBase table, int count) {
        return getDirectoryPath(type, table, count) + getFilenamePath(type, table, count);
    }

    private String getDirectoryPath(final DefinitionGeneratorType type, final TableBase table, int count) {
        final Long block = stringToLongUtil.apply(parametersPlugin.getOutputDatasetBlockSize());
        final String name = parametersPlugin.getKeepNames() ? table.getName() : table.getNewName();
        return total >= block ? name + File.separatorChar : "";
    }

    private String getFilenamePath(final DefinitionGeneratorType type, final TableBase table, int count) {
        final String name = parametersPlugin.getKeepNames() ? table.getName() : table.getNewName();
        final Long block = stringToLongUtil.apply(parametersPlugin.getOutputDatasetBlockSize());
        return total < block ? name + ".sql" : String.format(type.getFilename(), count, name);
    }

    private String makeFilenamePath(final DefinitionGeneratorType type, final TableBase table, int count) {
        final String directory = getDirectoryPath(type, table, count);
        final String filename = getFilenamePath(type, table, count);
        final String fullPath = outputDirectory + File.separatorChar + parametersPlugin.getOutputDatasetsDirectory() + File.separatorChar + directory;
        directoryBuilder.execute(fullPath);
        return fullPath + filename;
    }

    private void makeDataTableFile(final TableBase table, int count) {
        final DefinitionGeneratorType type = DefinitionGeneratorType.DATA_TABLE;
        final String path = makeFilenamePath(type, table, count);

        Map<String, Object> values = new HashMap<>();

        values.put(TABLE, table);
        values.put(TYPE_UTIL, new TableColumnTypeUtil());

        templateGenerator.execute(type, values, path, false);
    }

    private void makeInsertDataTableFile(final TableBase table, final List<TableBase> tables, int count) {
        final DefinitionGeneratorType type = DefinitionGeneratorType.DATA_INSERT_TABLE;
        final String path = makeFilenamePath(type, table, count);

        Map<String, Object> values = new HashMap<>();

        values.put(TABLES, tables);
        values.put(TYPE_UTIL, new TableColumnTypeUtil());

        templateGenerator.execute(type, values, path, true);
    }
}
