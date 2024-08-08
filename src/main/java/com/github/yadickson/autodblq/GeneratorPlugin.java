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
import java.util.Optional;

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
import com.github.yadickson.autodblq.util.StringToBooleanUtil;
import com.github.yadickson.autodblq.logger.MavenLoggerConfiguration;
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
     * Liquibase production definition enabled.
     */
    @Parameter(
            property = "autodblq.lqProductionEnabled",
            defaultValue = "false",
            alias = "lqProductionEnabled",
            required = true)
    private String lqProductionEnabled;

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
     * addDbms.
     */
    @Parameter(
            property = "autodblq.addNullable",
            defaultValue = "true",
            alias = "addNullable",
            required = false)
    private String addNullable;

    /**
     * addDbms.
     */
    @Parameter(
            property = "autodblq.addIdentity",
            defaultValue = "true",
            alias = "addIdentity",
            required = false)
    private String addIdentity;

    /**
     * addDbms.
     */
    @Parameter(
            property = "autodblq.keepNames",
            defaultValue = "true",
            alias = "keepNames",
            required = true)
    private String keepNames;

    private final MavenLoggerConfiguration mavenLoggerConfiguration;
    private final ParametersPlugin parametersPlugin;
    private final DataBaseGenerator dataBaseGenerator;
    private final DefinitionGenerator definitionGenerator;
    private final StringToBooleanUtil stringToBooleanUtil;

    private Map<DataBaseGeneratorType, Object> dataBaseGeneratorResult;

    @Inject
    public GeneratorPlugin(
            final MavenLoggerConfiguration mavenLoggerConfiguration,
            final ParametersPlugin parametersPlugin,
            final DataBaseGenerator dataBaseGenerator,
            final DefinitionGenerator definitionGenerator,
            final StringToBooleanUtil stringToBooleanUtil
    ) {
        this.mavenLoggerConfiguration = mavenLoggerConfiguration;
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
        mavenLoggerConfiguration.execute(getLog());
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
                .setTables(tables)
                .setDataTables(dataTables)
                .setViews(views)
                .setFunctions(functions)
                .setLiquibaseProductionEnabled(stringToBooleanUtil.apply(lqProductionEnabled))
                .setAddDbVersion(stringToBooleanUtil.apply(addDbVersion))
                .setAddSchema(stringToBooleanUtil.apply(addSchema))
                .setAddDbms(stringToBooleanUtil.apply(addDbms))
                .setAddNullable(stringToBooleanUtil.apply(addNullable))
                .setAddIdentity(stringToBooleanUtil.apply(addIdentity))
                .setKeepNames(stringToBooleanUtil.apply(keepNames));
    }

    private void printParameters() {
        getLog().info("[Generator] Driver: " + parametersPlugin.getDriver());
        getLog().info("[Generator] URL: " + parametersPlugin.getUrl());
        getLog().info("[Generator] User: " + parametersPlugin.getUsername());
        getLog().info("[Generator] Pass: ****");
        getLog().info("[Generator] Author: " + parametersPlugin.getAuthor());
        getLog().info("[Generator] Version: " + parametersPlugin.getVersion());
        getLog().info("[Generator] Encode: " + parametersPlugin.getEncode());
        getLog().info("[Generator] OutputDirectory: " + parametersPlugin.getOutputDirectory());
        getLog().info("[Generator] LiquibaseProductionEnabled: " + parametersPlugin.getLiquibaseProductionEnabled());
        getLog().info("[Generator] AddDbVersion: " + parametersPlugin.getAddDbVersion());
        getLog().info("[Generator] KeepNames: " + parametersPlugin.getKeepNames());
        getLog().info("[Generator] AddSchema: " + parametersPlugin.getAddSchema());
        getLog().info("[Generator] AddDbms: " + parametersPlugin.getAddDbms());
        getLog().info("[Generator] AddNullable: " + parametersPlugin.getAddNullable());
        getLog().info("[Generator] AddIdentity: " + parametersPlugin.getAddIdentity());
    }

    private void generate() throws MojoExecutionException {

        try (DriverConnectionDecorator driverConnection = new DriverConnectionDecorator(parametersPlugin)) {

            runDataBaseGenerator(driverConnection);
            runDefinitionGenerator(driverConnection);

        } catch (SQLException | IOException | RuntimeException ex) {
            getLog().error(ex.getMessage(), ex);
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
