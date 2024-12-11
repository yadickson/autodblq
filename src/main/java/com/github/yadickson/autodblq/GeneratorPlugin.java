/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import com.github.yadickson.autodblq.db.DataBaseGenerator;
import com.github.yadickson.autodblq.db.DataBaseGeneratorType;
import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.connection.DriverConnectionDecorator;
import com.github.yadickson.autodblq.logger.LoggerManager;
import com.github.yadickson.autodblq.util.StringToBooleanUtil;
import com.github.yadickson.autodblq.writer.DefinitionGenerator;

/**
 * Maven plugin to create liquibase configuration files from database.
 *
 * @author Yadickson Soto
 */
@Mojo(name = "generator",
        threadSafe = true,
        defaultPhase = LifecyclePhase.GENERATE_SOURCES,
        requiresProject = true,
        requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class GeneratorPlugin extends AbstractMojo {

    /**
     * Database driver.
     */
    @Parameter(
            property = "autodblq.driver",
            alias = "driver",
            required = true)
    private String driver;

    /**
     * Database url connection string.
     */
    @Parameter(
            property = "autodblq.url",
            alias = "url",
            required = true)
    private String url;

    /**
     * Database username.
     */
    @Parameter(
            property = "autodblq.username",
            alias = "username",
            required = true)
    private String username;

    /**
     * Database password.
     */
    @Parameter(
            property = "autodblq.password",
            alias = "password",
            required = true)
    private String password;

    /**
     * Output directory.
     */
    @Parameter(
            property = "autodblq.outputDirectory",
            defaultValue = "${project.build.directory}/generated-sources",
            alias = "outputDirectory",
            required = true)
    private String outputDirectory;

    /**
     * Author definition in file name.
     */
    @Parameter(
            property = "autodblq.author",
            defaultValue = "author",
            alias = "author",
            required = true)
    private String author;

    /**
     * Version definition in file name.
     */
    @Parameter(
            property = "autodblq.version",
            defaultValue = "1.0.0",
            alias = "version",
            required = true)
    private String version;

    /**
     * Encode.
     */
    @Parameter(
            property = "autodblq.encode",
            defaultValue = "UTF-8",
            alias = "encode",
            required = true)
    private String encode;

    /**
     * Tables to build.
     */
    @Parameter(
            alias = "tables",
            readonly = true,
            required = false)
    private List<String> tables;

    /**
     * Data tables to read the content.
     */
    @Parameter(
            alias = "dataTables",
            readonly = true,
            required = false)
    private List<String> dataTables;

    /**
     * Views to build.
     */
    @Parameter(
            alias = "views",
            readonly = true,
            required = false)
    private List<String> views;

    /**
     * Types to build.
     */
    @Parameter(
            alias = "types",
            readonly = true,
            required = false)
    private List<String> types;

    /**
     * Functions and procedures to build.
     */
    @Parameter(
            alias = "functions",
            readonly = true,
            required = false)
    private List<String> functions;

    /**
     * addDbVersion.
     */
    @Parameter(
            property = "autodblq.addDbVersion",
            defaultValue = "false",
            alias = "addDbVersion",
            required = false)
    private String addDbVersion;

    /**
     * addSchema.
     */
    @Parameter(
            property = "autodblq.addSchema",
            defaultValue = "false",
            alias = "addSchema",
            required = false)
    private String addSchema;

    /**
     * addDbms.
     */
    @Parameter(
            property = "autodblq.addDbms",
            defaultValue = "false",
            alias = "addDbms",
            required = false)
    private String addDbms;

    /**
     * addNullable.
     */
    @Parameter(
            property = "autodblq.addNullable",
            defaultValue = "true",
            alias = "addNullable",
            required = false)
    private String addNullable;

    /**
     * addIdentity.
     */
    @Parameter(
            property = "autodblq.addIdentity",
            defaultValue = "true",
            alias = "addIdentity",
            required = false)
    private String addIdentity;

    /**
     * keepNames.
     */
    @Parameter(
            property = "autodblq.keepNames",
            defaultValue = "true",
            alias = "keepNames",
            required = true)
    private String keepNames;

    /**
     * addIdentity.
     */
    @Parameter(
            property = "autodblq.keepTypes",
            defaultValue = "true",
            alias = "keepTypes",
            required = false)
    private String keepTypes;

    /**
     * Output views directory.
     */
    @Parameter(
            property = "autodblq.outputViewsDirectory",
            defaultValue = "views",
            alias = "outputViewsDirectory",
            required = true)
    private String outputViewsDirectory;

    /**
     * Output datasets directory.
     */
    @Parameter(
            property = "autodblq.outputDatasetsDirectory",
            defaultValue = "datasets",
            alias = "outputDatasetsDirectory",
            required = true)
    private String outputDatasetsDirectory;

    /**
     * Output functions directory.
     */
    @Parameter(
            property = "autodblq.outputFunctionsDirectory",
            defaultValue = "functions",
            alias = "outputFunctionsDirectory",
            required = true)
    private String outputFunctionsDirectory;

    /**
     * Output procedures directory.
     */
    @Parameter(
            property = "autodblq.outputProceduresDirectory",
            defaultValue = "procedures",
            alias = "outputProceduresDirectory",
            required = true)
    private String outputProceduresDirectory;

    private final LoggerManager loggerManager;
    private final ParametersPlugin parametersPlugin;
    private final DataBaseGenerator dataBaseGenerator;
    private final DefinitionGenerator definitionGenerator;
    private final StringToBooleanUtil stringToBooleanUtil;

    private Map<DataBaseGeneratorType, Object> dataBaseGeneratorResult;

    @Inject
    public GeneratorPlugin(
            final LoggerManager loggerManager,
            final ParametersPlugin parametersPlugin,
            final DataBaseGenerator dataBaseGenerator,
            final DefinitionGenerator definitionGenerator,
            final StringToBooleanUtil stringToBooleanUtil
    ) {
        this.loggerManager = loggerManager;
        this.parametersPlugin = parametersPlugin;
        this.dataBaseGenerator = dataBaseGenerator;
        this.definitionGenerator = definitionGenerator;
        this.stringToBooleanUtil = stringToBooleanUtil;
    }

    @Override
    public void execute() throws MojoExecutionException {

        configLogger();
        makeParameters();
        printParameters();
        generate();

    }

    private void configLogger() {
        loggerManager.configure(getLog());
    }

    private void makeParameters() {
        parametersPlugin
                .setDriver(driver)
                .setUrl(url)
                .setUsername(username)
                .setPassword(password)
                .setAuthor(author)
                .setVersion(version)
                .setEncode(encode)
                .setOutputDirectory(outputDirectory)
                .setOutputDatasetsDirectory(outputDatasetsDirectory)
                .setOutputViewsDirectory(outputViewsDirectory)
                .setOutputFunctionsDirectory(outputFunctionsDirectory)
                .setOutputProceduresDirectory(outputProceduresDirectory)
                .setTables(tables)
                .setDataTables(dataTables)
                .setViews(views)
                .setTypes(types)
                .setFunctions(functions)
                .setAddDbVersion(stringToBooleanUtil.apply(addDbVersion))
                .setAddSchema(stringToBooleanUtil.apply(addSchema))
                .setAddDbms(stringToBooleanUtil.apply(addDbms))
                .setAddNullable(stringToBooleanUtil.apply(addNullable))
                .setAddIdentity(stringToBooleanUtil.apply(addIdentity))
                .setkeepTypes(stringToBooleanUtil.apply(keepTypes))
                .setKeepNames(stringToBooleanUtil.apply(keepNames));
    }

    private void printParameters() {
        loggerManager.info("[Generator] Driver: " + parametersPlugin.getDriver());
        loggerManager.info("[Generator] URL: " + parametersPlugin.getUrl());
        loggerManager.info("[Generator] User: " + parametersPlugin.getUsername());
        loggerManager.info("[Generator] Pass: ****");
        loggerManager.info("[Generator] Author: " + parametersPlugin.getAuthor());
        loggerManager.info("[Generator] Version: " + parametersPlugin.getVersion());
        loggerManager.info("[Generator] Encode: " + parametersPlugin.getEncode());
        loggerManager.info("[Generator] OutputDirectory: " + parametersPlugin.getOutputDirectory());
        loggerManager.info("[Generator] OutputDatasetsDirectory: " + parametersPlugin.getOutputDatasetsDirectory());
        loggerManager.info("[Generator] OutputViewsDirectory: " + parametersPlugin.getOutputViewsDirectory());
        loggerManager.info("[Generator] OutputFunctionsDirectory: " + parametersPlugin.getOutputFunctionsDirectory());
        loggerManager.info("[Generator] OutputProceduresDirectory: " + parametersPlugin.getOutputProceduresDirectory());
        loggerManager.info("[Generator] AddDbVersion: " + parametersPlugin.getAddDbVersion());
        loggerManager.info("[Generator] KeepNames: " + parametersPlugin.getKeepNames());
        loggerManager.info("[Generator] AddSchema: " + parametersPlugin.getAddSchema());
        loggerManager.info("[Generator] AddDbms: " + parametersPlugin.getAddDbms());
        loggerManager.info("[Generator] AddNullable: " + parametersPlugin.getAddNullable());
        loggerManager.info("[Generator] AddIdentity: " + parametersPlugin.getAddIdentity());
    }

    private void generate() throws MojoExecutionException {

        try (DriverConnectionDecorator driverConnection = new DriverConnectionDecorator(parametersPlugin)) {

            runDataBaseGenerator(driverConnection);
            runDefinitionGenerator(driverConnection);

        } catch (SQLException | IOException | RuntimeException ex) {
            loggerManager.error(ex.getMessage(), ex);
            throw new MojoExecutionException("Fail liquibase generator", ex);
        }
    }

    private void runDataBaseGenerator(final DriverConnection driverConnection) {
        dataBaseGeneratorResult = dataBaseGenerator.execute(driverConnection);
    }

    private void runDefinitionGenerator(final DriverConnection driverConnection) {
        definitionGenerator.execute(driverConnection, dataBaseGeneratorResult);
    }

}
