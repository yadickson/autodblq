/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.writer;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;

import com.github.yadickson.autodblq.ParametersPlugin;
import com.github.yadickson.autodblq.db.DataBaseGeneratorType;
import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.function.base.model.FunctionBase;
import com.github.yadickson.autodblq.db.property.DataBasePropertyManager;
import com.github.yadickson.autodblq.db.property.model.TablePropertyType;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.type.base.model.TypeBase;
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

    private final ParametersPlugin parametersPlugin;
    private final TemplateGenerator templateGenerator;
    private final DataTableGenerator dataTableGenerator;
    private final DirectoryBuilder directoryBuilder;
    private final DataBasePropertyManager dataBasePropertyManager;

    private String version;
    private List<TableBase> tables;
    private List<TableBase> defaults;
    private List<TableBase> checks;
    private List<TableBase> indexes;
    private List<TableBase> uniques;
    private List<TableBase> primaryKeys;
    private List<TableBase> foreignKeys;
    private List<TableBase> dataTables;
    private List<ViewBase> views;
    private List<TypeBase> types;
    private List<FunctionBase> functions;
    private List<FunctionBase> procedures;

    private final Map<String, Object> values = new HashMap<>();
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
    private static final String DATA_BASE_CHECKS = "checks";
    private static final String DATA_BASE_DEFAULTS = "defaults";
    private static final String DATA_BASE_INDEXES = "indexes";
    private static final String DATA_BASE_UNIQUES = "uniques";
    private static final String DATA_BASE_PRIMARY_KEYS = "primaryKeys";
    private static final String DATA_BASE_FOREIGN_KEYS = "foreignKeys";
    private static final String DATA_BASE_DATA_TABLE = "dataTables";
    private static final String DATA_BASE_VIEWS = "views";
    private static final String DATA_BASE_TYPES = "types";
    private static final String DATA_BASE_FUNCTIONS = "functions";
    private static final String DATA_BASE_PROCEDURES = "procedures";

    private static final String DRIVER_NAME = "driverName";
    private static final String CHANGELOG_PATH = "changelogpath";
    private static final String FILES_GENERATED = "files";
    private static final String TYPE_UTIL = "typeUtil";
    private static final String ENCODE = "encode";
    private static final String ADD_DB_VERSION = "addDbVersion";
    private static final String ADD_SCHEMA = "addSchema";
    private static final String ADD_DBMS = "addDbms";
    private static final String ADD_NULLABLE = "addNullable";
    private static final String ADD_IDENTITY = "addIdentity";
    private static final String KEEP_TYPES = "keepTypes";
    private static final String KEEP_NAMES = "keepNames";

    private static final String DATASETS_DIRECTORY = "datasetsDirectory";
    private static final String VIEWS_DIRECTORY = "viewsDirectory";
    private static final String FUNCTIONS_DIRECTORY = "functionsDirectory";
    private static final String PROCEDURES_DIRECTORY = "proceduresDirectory";

    @Inject
    public DefinitionGenerator(
            final ParametersPlugin parametersPlugin,
            final TemplateGenerator templateGenerator,
            final DataTableGenerator dataTableGenerator,
            final DirectoryBuilder directoryBuilder, DataBasePropertyManager dataBasePropertyManager
    ) {
        this.parametersPlugin = parametersPlugin;
        this.templateGenerator = templateGenerator;
        this.dataTableGenerator = dataTableGenerator;
        this.directoryBuilder = directoryBuilder;
        this.dataBasePropertyManager = dataBasePropertyManager;
    }

    public void execute(final DriverConnection driverConnection, final Map<DataBaseGeneratorType, Object> dataBaseGenerator) {

        try {

            processInputs(dataBaseGenerator);
            makeInputValues(driverConnection);
            cleanOutputDirectory();

            makeDataTables(driverConnection);
            makeDefinitions();
            makeMasterChangelog();

        } catch (IOException | RuntimeException ex) {
            throw new DefinitionGeneratorException(ex);
        }
    }

    private void processInputs(final Map<DataBaseGeneratorType, Object> dataBaseGenerator) {
        version = (String) dataBaseGenerator.get(DataBaseGeneratorType.VERSION);
        tables = (List) dataBaseGenerator.get(DataBaseGeneratorType.TABLE_DEFINITION);
        dataTables = (List) dataBaseGenerator.get(DataBaseGeneratorType.DATA_DEFINITION);
        checks = (List) dataBaseGenerator.get(DataBaseGeneratorType.TABLE_CHECKS);
        defaults = (List) dataBaseGenerator.get(DataBaseGeneratorType.TABLE_DEFAULTS);
        indexes = (List) dataBaseGenerator.get(DataBaseGeneratorType.TABLE_INDEXES);
        uniques = (List) dataBaseGenerator.get(DataBaseGeneratorType.TABLE_UNIQUES);
        primaryKeys = (List) dataBaseGenerator.get(DataBaseGeneratorType.TABLE_PRIMARY_KEYS);
        foreignKeys = (List) dataBaseGenerator.get(DataBaseGeneratorType.TABLE_FOREIGN_KEYS);
        views = (List) dataBaseGenerator.get(DataBaseGeneratorType.VIEW_DEFINITION);
        types = (List) dataBaseGenerator.get(DataBaseGeneratorType.TYPE_DEFINITION);
        functions = ((List<FunctionBase>) dataBaseGenerator.get(DataBaseGeneratorType.FUNCTION_DEFINITION)).stream().filter(function -> function.getIsFunction()).collect(Collectors.toList());
        procedures = ((List<FunctionBase>) dataBaseGenerator.get(DataBaseGeneratorType.FUNCTION_DEFINITION)).stream().filter(function -> !function.getIsFunction()).collect(Collectors.toList());
    }

    private void makeInputValues(final DriverConnection driverConnection) {

        values.put(VERSION, parametersPlugin.getVersion());
        values.put(AUTHOR, parametersPlugin.getAuthor());
        values.put(ENCODE, parametersPlugin.getEncode());

        values.put(DATA_BASE_VERSION, version);
        values.put(DATA_BASE_TABLES, tables);
        values.put(DATA_BASE_DATA_TABLE, dataTables);
        values.put(DATA_BASE_CHECKS, checks);
        values.put(DATA_BASE_DEFAULTS, defaults);
        values.put(DATA_BASE_INDEXES, indexes);
        values.put(DATA_BASE_UNIQUES, uniques);
        values.put(DATA_BASE_PRIMARY_KEYS, primaryKeys);
        values.put(DATA_BASE_FOREIGN_KEYS, foreignKeys);
        values.put(DATA_BASE_VIEWS, views);
        values.put(DATA_BASE_TYPES, types);
        values.put(DATA_BASE_FUNCTIONS, functions);
        values.put(DATA_BASE_PROCEDURES, procedures);

        values.put(DRIVER_NAME, driverConnection.getDriver().getMessage());

        values.put(CHANGELOG_PATH, CHANGELOG);
        values.put(TYPE_UTIL, new TableColumnTypeUtil());

        values.put(ADD_DB_VERSION, parametersPlugin.getAddDbVersion());
        values.put(ADD_SCHEMA, parametersPlugin.getAddSchema());
        values.put(ADD_DBMS, parametersPlugin.getAddDbms());
        values.put(ADD_NULLABLE, parametersPlugin.getAddNullable());
        values.put(ADD_IDENTITY, parametersPlugin.getAddIdentity());
        values.put(KEEP_TYPES, parametersPlugin.getKeepTypes());
        values.put(KEEP_NAMES, parametersPlugin.getKeepNames());

        values.put(DATASETS_DIRECTORY, parametersPlugin.getOutputDatasetsDirectory());
        values.put(VIEWS_DIRECTORY, parametersPlugin.getOutputViewsDirectory());
        values.put(FUNCTIONS_DIRECTORY, parametersPlugin.getOutputFunctionsDirectory());
        values.put(PROCEDURES_DIRECTORY, parametersPlugin.getOutputProceduresDirectory());
    }

    private void cleanOutputDirectory() throws IOException {
        outputDirectory = parametersPlugin.getOutputDirectory() + File.separatorChar + parametersPlugin.getVersion() + File.separatorChar;
        FileUtils.deleteDirectory(new File(outputDirectory));
    }

    private void makeDefinitions() {
        makeProperties();
        makeTypes();
        makeTables();
        makeChecks();
        makeDefaults();
        makeIndexes();
        makeUniques();
        makePrimaryKeys();
        makeForeignKeys();
        makeViews();
        makeFunctions();
        makeProcedures();
        makeDataTables();
    }

    private void makeProperties() {

        if(!parametersPlugin.getKeepTypes()) {
            Map<String, List<TablePropertyType>> properties = dataBasePropertyManager.getProperties();
            values.put(DATA_BASE_PROPERTIES, properties);
        }

        addAndMakeFileBase(DefinitionGeneratorType.PROPERTIES);
    }

    private void makeTypes() {
        addAndMakeFileBase(DefinitionGeneratorType.TYPES);
    }

    private void makeTables() {
        addAndMakeFileBase(DefinitionGeneratorType.TABLES);
    }

    private void makeChecks() {
        addAndMakeFileBase(DefinitionGeneratorType.CHECKS);
    }

    private void makeDefaults() {
        addAndMakeFileBase(DefinitionGeneratorType.DEFAULTS);
    }

    private void makeIndexes() {
        addAndMakeFileBase(DefinitionGeneratorType.INDEXES);
    }

    private void makeUniques() {
        addAndMakeFileBase(DefinitionGeneratorType.UNIQUES);
    }

    private void makePrimaryKeys() {
        addAndMakeFileBase(DefinitionGeneratorType.PRIMARY_KEYS);
    }

    private void makeForeignKeys() {
        addAndMakeFileBase(DefinitionGeneratorType.FOREIGN_KEYS);
    }

    private void makeDataTables() {
        addAndMakeFileBase(DefinitionGeneratorType.DATA_TABLES);
    }

    private void makeViews() {
        addAndMakeFileBase(DefinitionGeneratorType.VIEWS);

        for (ViewBase view : views) {
            values.put(VIEW, view);
            makeViewFile(view);
        }
    }

    private void makeFunctions() {
        addAndMakeFileBase(DefinitionGeneratorType.FUNCTIONS);

        for (FunctionBase function : functions) {
            values.put(FUNCTION, function);
            makeFunctionFile(function);
        }
    }

    private void makeProcedures() {
        addAndMakeFileBase(DefinitionGeneratorType.PROCEDURES);

        for (FunctionBase procedure : procedures) {
            values.put(PROCEDURE, procedure);
            makeProcedureFile(procedure);
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
        final String filename = String.format(type.getFilename(), parametersPlugin.getKeepNames() ? table.getName() : table.getNewName());
        final String path = makeFilenamePath(parametersPlugin.getOutputDatasetsDirectory(), filename);
        makeTemplate(type, path);
    }

    private void makeViewFile(final ViewBase viewBase) {
        final DefinitionGeneratorType type = DefinitionGeneratorType.VIEW;
        final String filename = String.format(type.getFilename(), parametersPlugin.getKeepNames() ? viewBase.getName() : viewBase.getNewName());
        final String path = makeFilenamePath(parametersPlugin.getOutputViewsDirectory(), filename);
        makeTemplate(type, path);
    }

    private void makeFunctionFile(final FunctionBase functionBase) {
        final DefinitionGeneratorType type = DefinitionGeneratorType.FUNCTION;
        final String filename = String.format(type.getFilename(), parametersPlugin.getKeepNames() ? functionBase.getName() : functionBase.getNewName());
        final String path = makeFilenamePath(parametersPlugin.getOutputFunctionsDirectory(), filename);
        makeTemplate(type, path);
    }

    private void makeProcedureFile(final FunctionBase procedureBase) {
        final DefinitionGeneratorType type = DefinitionGeneratorType.PROCEDURE;
        final String filename = String.format(type.getFilename(), parametersPlugin.getKeepNames() ? procedureBase.getName() : procedureBase.getNewName());
        final String path = makeFilenamePath(parametersPlugin.getOutputProceduresDirectory(), filename);
        makeTemplate(type, path);
    }

    private void makeMasterChangelog() {

        if (filesGenerated.isEmpty()) {
            return;
        }

        final DefinitionGeneratorType type = DefinitionGeneratorType.MASTER_CHANGELOG;
        final String filename = type.getFilename();
        final String path = makeFilenamePath(".", filename);
        values.put(FILES_GENERATED, filesGenerated);
        makeTemplate(type, path);
    }

    private String makeFilenamePath(final String directory, final String filename) {
        final String fullPath = outputDirectory + File.separatorChar + directory + File.separatorChar;
        directoryBuilder.execute(fullPath);
        return fullPath + filename;
    }

    private void makeTemplate(final DefinitionGeneratorType type, final String path) {
        templateGenerator.execute(type, values, path, false);
    }

    private void makeDataTables(final DriverConnection driverConnection) {

        for (TableBase table : dataTables) {
            values.put(TABLE, table);
            makeDataTableFile(table);
        }

        dataTableGenerator.execute(driverConnection, dataTables);
    }

}
