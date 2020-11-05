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

import com.github.yadickson.autodblq.db.DataBaseGenerator;
import com.github.yadickson.autodblq.db.DataBaseGeneratorType;
import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.connection.DriverConnectionDecorator;
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
        requiresProject = true)
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
     * Liquibase version definition file name.
     */
    @Parameter(
            property = "autodblq.lqVersion",
            defaultValue = "3.6",
            alias = "lqVersion",
            required = true)
    private String lqVersion;

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
     * csvQuotchar.
     */
    @Parameter(
            property = "autodblq.csvQuotchar",
            defaultValue = "'",
            alias = "csvQuotchar",
            required = true)
    private String csvQuotchar;

    /**
     * csvSeparator.
     */
    @Parameter(
            property = "autodblq.csvSeparator",
            defaultValue = ",",
            alias = "csvSeparator",
            required = true)
    private String csvSeparator;

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

    private final MavenLoggerConfiguration mavenLoggerConfiguration;
    private final DataBaseGenerator dataBaseGenerator;
    private final DefinitionGenerator definitionGenerator;

    private Parameters parameters;
    private Map<DataBaseGeneratorType, Object> dataBaseGeneratorResult;

    @Inject
    public GeneratorPlugin(
            final MavenLoggerConfiguration mavenLoggerConfiguration,
            final DataBaseGenerator dataBaseGenerator,
            final DefinitionGenerator definitionGenerator
    ) {
        this.mavenLoggerConfiguration = mavenLoggerConfiguration;
        this.dataBaseGenerator = dataBaseGenerator;
        this.definitionGenerator = definitionGenerator;
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
        parameters = Optional.ofNullable(parameters).orElse(new Parameters(driver, url, username, password, author, version, encode, csvQuotchar, csvSeparator, outputDirectory, lqVersion, lqProductionEnabled, tables, dataTables, views, functions));
    }

    private void printParameters() {
        getLog().info("[Generator] Driver: " + parameters.getDriver());
        getLog().info("[Generator] URL: " + parameters.getUrl());
        getLog().info("[Generator] User: " + parameters.getUsername());
        getLog().info("[Generator] Pass: ****");
        getLog().info("[Generator] Author: " + parameters.getAuthor());
        getLog().info("[Generator] Version: " + parameters.getVersion());
        getLog().info("[Generator] Encode: " + parameters.getEncode());
        getLog().info("[Generator] CSV Quotchar: " + parameters.getCsvQuotchar());
        getLog().info("[Generator] CSV Separator: " + parameters.getCsvSeparator());
        getLog().info("[Generator] OutputDirectory: " + parameters.getOutputDirectory());
        getLog().info("[Generator] LiquibaseVersion: " + parameters.getLiquibaseVersion());
        getLog().info("[Generator] LiquibaseProductionEnabled: " + parameters.getLiquibaseProductionEnabled());
    }

    private void generate() throws MojoExecutionException {

        try (DriverConnectionDecorator driverConnection = new DriverConnectionDecorator(parameters)) {

            runDataBaseGenerator(driverConnection);
            runDefinitionGenerator(driverConnection);

        } catch (SQLException | IOException | RuntimeException ex) {
            getLog().error(ex.getMessage(), ex);
            throw new MojoExecutionException("Fail liquibase generator", ex);
        }
    }

    private void runDataBaseGenerator(final DriverConnection driverConnection) {
        dataBaseGeneratorResult = dataBaseGenerator.execute(parameters, driverConnection);
    }

    private void runDefinitionGenerator(final DriverConnection driverConnection) {
        definitionGenerator.execute(parameters, driverConnection, dataBaseGeneratorResult);
    }

    /**
     * Setter parameters only for test.
     *
     * @param pparameters the parameters of the project to set
     */
    public void setParameters(final Parameters pparameters) {
        this.parameters = pparameters;
    }

}
