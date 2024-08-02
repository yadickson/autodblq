/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.writer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.table.property.model.TablePropertyType;
import org.apache.commons.io.FileUtils;

import com.github.yadickson.autodblq.Parameters;
import com.github.yadickson.autodblq.db.DataBaseGeneratorType;
import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.function.base.model.FunctionBase;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.view.base.model.ViewBase;
import com.github.yadickson.autodblq.directory.DirectoryBuilder;
import com.github.yadickson.autodblq.writer.table.DataTableGenerator;
import com.github.yadickson.autodblq.writer.template.TemplateGenerator;
import com.github.yadickson.autodblq.writer.util.TableColumnTypeUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
public final class DefinitionGenerator {

    private final TemplateGenerator templateGenerator;
    private final DataTableGenerator dataTableGenerator;
    private final DirectoryBuilder directoryBuilder;

    private String version;
    private List<TableBase> tables;
    private List<TableBase> defaults;
    private List<TableBase> increments;
    private List<TableBase> indexes;
    private List<TableBase> uniques;
    private List<TableBase> primaryKeys;
    private List<TableBase> foreignKeys;
    private List<TableBase> dataTables;
    private List<ViewBase> views;
    private List<FunctionBase> functions;
    private Map<Driver, List<TablePropertyType>> properties;

    private final Map<String, Object> values = new HashMap();
    private final List<String> filesGenerated = new ArrayList<>();

    private String outputDirectory;
    private Integer fileCounter = -1;

    private final String CHANGELOG = "changelog";
    private final String VIEW = "view";
    private final String TABLE = "table";
    private final String FUNCTION = "function";
    private final String PROCEDURE = "procedure";

    private static final String FILE_COUNTER = "file";
    private static final String VERSION = "version";
    private static final String AUTHOR = "author";

    private static final String DATA_BASE_VERSION = "dbversion";
    private static final String DATA_BASE_TABLES = "tables";
    private static final String DATA_BASE_PROPERTIES = "properties";
    private static final String DATA_BASE_DEFAULTS = "defaults";
    private static final String DATA_BASE_INCREMENTS = "increments";
    private static final String DATA_BASE_INDEXES = "indexes";
    private static final String DATA_BASE_UNIQUES = "uniques";
    private static final String DATA_BASE_PRIMARY_KEYS = "primaryKeys";
    private static final String DATA_BASE_FOREIGN_KEYS = "foreignKeys";
    private static final String DATA_BASE_DATA_TABLE = "dataTables";
    private static final String DATA_BASE_VIEWS = "views";
    private static final String DATA_BASE_FUNCTIONS = "functions";

    private static final String DRIVER_NAME = "driverName";
    private static final String LQ_VERSION = "lqversion";
    private static final String LQ_PRO = "lqpro";
    private static final String CHANGELOG_PATH = "changelogpath";
    private static final String FILES_GENERATED = "files";
    private static final String TYPE_UTIL = "typeUtil";
    private static final String ENCODE = "encode";
    private static final String CSV_QUOTCHAR = "csvQuotchar";
    private static final String CSV_SEPARATOR = "csvSeparator";
    private static final String CSV_COMMENT = "csvComment";
    private static final String ADD_DB_VERSION = "addDbVersion";
    private static final String ADD_SCHEMA = "addSchema";
    private static final String ADD_DBMS = "addDbms";
    private static final String ADD_NULLABLE = "addNullable";

    @Inject
    public DefinitionGenerator(
            final TemplateGenerator templateGenerator,
            final DataTableGenerator dataTableGenerator,
            final DirectoryBuilder directoryBuilder
    ) {
        this.templateGenerator = templateGenerator;
        this.dataTableGenerator = dataTableGenerator;
        this.directoryBuilder = directoryBuilder;
    }

    public void execute(final Parameters parameters, final DriverConnection driverConnection, final Map<DataBaseGeneratorType, Object> dataBaseGenerator) {

        try {

            processInputs(dataBaseGenerator);
            makeInputValues(parameters, driverConnection);
            cleanOutputDirectory(parameters);

            makeDefinitions();
            makeDataTables(parameters, driverConnection);
            makeMasterChangelog();

        } catch (IOException | RuntimeException ex) {
            throw new DefinitionGeneratorException(ex);
        }
    }

    private void processInputs(final Map<DataBaseGeneratorType, Object> dataBaseGenerator) {
        version = (String) dataBaseGenerator.get(DataBaseGeneratorType.VERSION);
        tables = (List) dataBaseGenerator.get(DataBaseGeneratorType.TABLE_DEFINITION);
        dataTables = (List) dataBaseGenerator.get(DataBaseGeneratorType.DATA_DEFINITION);
        defaults = (List) dataBaseGenerator.get(DataBaseGeneratorType.TABLE_DEFAULTS);
        increments = (List) dataBaseGenerator.get(DataBaseGeneratorType.TABLE_INCREMENTS);
        indexes = (List) dataBaseGenerator.get(DataBaseGeneratorType.TABLE_INDEXES);
        uniques = (List) dataBaseGenerator.get(DataBaseGeneratorType.TABLE_UNIQUES);
        primaryKeys = (List) dataBaseGenerator.get(DataBaseGeneratorType.TABLE_PRIMARY_KEYS);
        foreignKeys = (List) dataBaseGenerator.get(DataBaseGeneratorType.TABLE_FOREIGN_KEYS);
        views = (List) dataBaseGenerator.get(DataBaseGeneratorType.VIEW_DEFINITION);
        functions = (List) dataBaseGenerator.get(DataBaseGeneratorType.FUNCTION_DEFINITION);
        properties = (Map) dataBaseGenerator.get(DataBaseGeneratorType.TABLE_PROPERTIES);
    }

    private void makeInputValues(final Parameters parameters, final DriverConnection driverConnection) {

        values.put(VERSION, parameters.getVersion());
        values.put(AUTHOR, parameters.getAuthor());

        values.put(ENCODE, parameters.getEncode());
        values.put(CSV_QUOTCHAR, parameters.getCsvQuotchar());
        values.put(CSV_SEPARATOR, parameters.getCsvSeparator());
        values.put(CSV_COMMENT, parameters.getCsvComment());

        values.put(DATA_BASE_VERSION, version);
        values.put(DATA_BASE_TABLES, tables);
        values.put(DATA_BASE_DATA_TABLE, dataTables);
        values.put(DATA_BASE_DEFAULTS, defaults);
        values.put(DATA_BASE_INCREMENTS, increments);
        values.put(DATA_BASE_INDEXES, indexes);
        values.put(DATA_BASE_UNIQUES, uniques);
        values.put(DATA_BASE_PRIMARY_KEYS, primaryKeys);
        values.put(DATA_BASE_FOREIGN_KEYS, foreignKeys);
        values.put(DATA_BASE_VIEWS, views);
        values.put(DATA_BASE_FUNCTIONS, functions);
        values.put(DATA_BASE_PROPERTIES, properties);

        values.put(LQ_VERSION, parameters.getLiquibaseVersion());
        values.put(LQ_PRO, parameters.getLiquibaseProductionEnabled());

        values.put(DRIVER_NAME, driverConnection.getDriver().getMessage());

        values.put(CHANGELOG_PATH, CHANGELOG);
        values.put(TYPE_UTIL, new TableColumnTypeUtil());

        values.put(ADD_DB_VERSION, parameters.getAddDbVersion());
        values.put(ADD_SCHEMA, parameters.getAddSchema());
        values.put(ADD_DBMS, parameters.getAddDbms());
        values.put(ADD_NULLABLE, parameters.getAddNullable());
    }

    private void cleanOutputDirectory(final Parameters parameters) throws IOException {
        outputDirectory = parameters.getOutputDirectory() + File.separatorChar + parameters.getVersion() + File.separatorChar;
        FileUtils.deleteDirectory(new File(outputDirectory));
    }

    private void makeDefinitions() {
        makeProperties();
        makeTables();
        makeDefaults();
        makeIncrements();
        makeIndexes();
        makeUniques();
        makePrimaryKeys();
        makeForeignKeys();
        makeDataTables();
        makeViews();
        makeFunctions();
    }

    private void makeProperties() {
        if (!tables.isEmpty()) {
            addAndMakeFileBase(DefinitionGeneratorType.PROPERTIES);
        }
    }

    private void makeTables() {
        if (!tables.isEmpty()) {
            addAndMakeFileBase(DefinitionGeneratorType.TABLES);
        }
    }

    private void makeDefaults() {
        if (!defaults.isEmpty()) {
            addAndMakeFileBase(DefinitionGeneratorType.DEFAULTS);
        }
    }

    private void makeIncrements() {
        if (!increments.isEmpty()) {
            addAndMakeFileBase(DefinitionGeneratorType.INCREMENTS);
        }
    }

    private void makeIndexes() {
        if (!indexes.isEmpty()) {
            addAndMakeFileBase(DefinitionGeneratorType.INDEXES);
        }
    }

    private void makeUniques() {
        if (!uniques.isEmpty()) {
            addAndMakeFileBase(DefinitionGeneratorType.UNIQUES);
        }
    }

    private void makePrimaryKeys() {
        if (!primaryKeys.isEmpty()) {
            addAndMakeFileBase(DefinitionGeneratorType.PRIMARY_KEYS);
        }
    }

    private void makeForeignKeys() {
        if (!foreignKeys.isEmpty()) {
            addAndMakeFileBase(DefinitionGeneratorType.FOREIGN_KEYS);
        }
    }

    private void makeDataTables() {

        if (dataTables.isEmpty()) {
            return;
        }

        addAndMakeFileBase(DefinitionGeneratorType.DATA_TABLES);

        for (TableBase table : dataTables) {
            values.put(TABLE, table);
            makeDataTableFile(table);
        }
    }

    private void makeViews() {

        if (views.isEmpty()) {
            return;
        }

        addAndMakeFileBase(DefinitionGeneratorType.VIEWS);

        for (ViewBase view : views) {
            values.put(VIEW, view);
            makeViewFile(view);
        }
    }

    private void makeFunctions() {

        if (functions.isEmpty()) {
            return;
        }

        addAndMakeFileBase(DefinitionGeneratorType.FUNCTIONS);

        for (FunctionBase function : functions) {

            if (function.getIsFunction()) {
                values.put(FUNCTION, function);
                makeFunctionFile(function);
            } else {
                values.put(PROCEDURE, function);
                makeProcedureFile(function);
            }
        }
    }

    private void addAndMakeFileBase(final DefinitionGeneratorType type) {
        final String filename = String.format(type.getFilename(), ++fileCounter);
        final String path = makeFilenamePath(CHANGELOG, filename);

        addFilename(filename);

        makeTemplate(type, path);
    }

    private void addFilename(final String filename) {
        values.put(FILE_COUNTER, fileCounter);
        filesGenerated.add(filename);
    }

    private void makeDataTableFile(final TableBase table) {
        final DefinitionGeneratorType type = DefinitionGeneratorType.DATA_TABLE;
        final String filename = String.format(type.getFilename(), table.getName());
        final String path = makeFilenamePath(TABLE, filename);
        makeTemplate(type, path);
    }

    private void makeViewFile(final ViewBase viewBase) {
        final DefinitionGeneratorType type = DefinitionGeneratorType.VIEW;
        final String filename = String.format(type.getFilename(), viewBase.getName());
        final String path = makeFilenamePath(VIEW, filename);
        makeTemplate(type, path);
    }

    private void makeFunctionFile(final FunctionBase functionBase) {
        final DefinitionGeneratorType type = DefinitionGeneratorType.FUNCTION;
        final String filename = String.format(type.getFilename(), functionBase.getName());
        final String path = makeFilenamePath(FUNCTION, filename);
        makeTemplate(type, path);
    }

    private void makeProcedureFile(final FunctionBase functionBase) {
        final DefinitionGeneratorType type = DefinitionGeneratorType.PROCEDURE;
        final String filename = String.format(type.getFilename(), functionBase.getName());
        final String path = makeFilenamePath(PROCEDURE, filename);
        makeTemplate(type, path);
    }

    private void makeMasterChangelog() {

        if (filesGenerated.isEmpty()) {
            return;
        }

        final DefinitionGeneratorType type = DefinitionGeneratorType.MASTER_CHANGLE_LOG;
        final String filename = type.getFilename();
        final String path = makeFilenamePath(".", filename);
        values.put(FILES_GENERATED, filesGenerated);
        makeTemplate(type, path);
    }

    private String makeFilenamePath(final String directory, final String filename) {
        final String fullpath = outputDirectory + File.separatorChar + directory + File.separatorChar;
        directoryBuilder.execute(fullpath);
        return fullpath + filename;
    }

    private void makeTemplate(final DefinitionGeneratorType type, final String path) {
        templateGenerator.execute(type, values, path);
    }

    private void makeDataTables(final Parameters parameters, final DriverConnection driverConnection) {
        dataTableGenerator.execute(parameters, driverConnection, dataTables);
    }

}
