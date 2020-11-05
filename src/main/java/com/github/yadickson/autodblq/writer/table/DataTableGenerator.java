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
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.github.yadickson.autodblq.Parameters;
import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.data.DataBaseDataTableCountReader;
import com.github.yadickson.autodblq.db.table.data.DataBaseDataTableReader;
import com.github.yadickson.autodblq.db.table.data.DataBaseDataTableReaderIterator;
import com.github.yadickson.autodblq.writer.DefinitionGeneratorType;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataTableGenerator {

    private final DataBaseDataTableCountReader dataBaseDataTableCountReader;
    private final DataBaseDataTableReader dataBaseDataTableReader;

    private String outputDirectory;

    private final String TABLE = "table";

    @Inject
    public DataTableGenerator(
            final DataBaseDataTableCountReader dataBaseDataTableCountReader,
            final DataBaseDataTableReader dataBaseDataTableBlockReader
    ) {
        this.dataBaseDataTableCountReader = dataBaseDataTableCountReader;
        this.dataBaseDataTableReader = dataBaseDataTableBlockReader;
    }

    public void execute(final Parameters parameters, final DriverConnection driverConnection, final List<TableBase> tables) {

        try {

            if (tables.isEmpty()) {
                return;
            }

            makeOutputDirectory(parameters);
            makeDataTables(parameters, driverConnection, tables);

        } catch (RuntimeException ex) {
            throw new DataTableGeneratorException(ex);
        }
    }

    private void makeOutputDirectory(final Parameters parameters) {
        outputDirectory = parameters.getOutputDirectory() + File.separatorChar + parameters.getVersion() + File.separatorChar;
    }

    private void makeDataTables(final Parameters parameters, final DriverConnection driverConnection, final List<TableBase> tables) {
        for (TableBase table : tables) {
            readTotalCount(driverConnection, table);
            makeDataTable(parameters, driverConnection, table);
        }
    }

    private void readTotalCount(final DriverConnection driverConnection, final TableBase table) {
        dataBaseDataTableCountReader.execute(driverConnection, table);
    }

    private void makeDataTable(final Parameters parameters, final DriverConnection driverConnection, TableBase table) {
        final DefinitionGeneratorType type = DefinitionGeneratorType.DATA_TABLE;
        final String filename = String.format(type.getFilename(), table.getName());
        final String path = outputDirectory + File.separatorChar + TABLE + File.separatorChar + filename;
        readerTable(parameters, driverConnection, table, path);
    }

    private void readerTable(final Parameters parameters, final DriverConnection driverConnection, final TableBase table, final String path) {
        DataBaseDataTableReaderIterator iterator = dataBaseDataTableReader.execute(parameters, driverConnection, table);

        while (iterator.nextBlock()) {
            List<String> lines = iterator.getBlock();
            writeFile(path, lines);
        }
    }

    private void writeFile(final String path, final List<String> lines) {

        try (Writer writer = new FileWriter(path, true); PrintWriter output = new PrintWriter(writer)) {

            for (String line : lines) {
                output.println(line);
            }

        } catch (IOException | RuntimeException ex) {
            throw new DataTableGeneratorException(ex);
        }
    }

}
