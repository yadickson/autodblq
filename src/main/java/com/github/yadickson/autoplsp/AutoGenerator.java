/*
 * Copyright (C) 2019 Yadickson Soto
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.yadickson.autoplsp;

import com.github.yadickson.autoplsp.db.DriverConnection;
import com.github.yadickson.autoplsp.db.GeneratorFactory;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.github.yadickson.autoplsp.db.Generator;
import com.github.yadickson.autoplsp.db.common.Table;
import com.github.yadickson.autoplsp.logger.LoggerManager;
import java.sql.Connection;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Maven plugin to java classes and config spring file generator from database.
 *
 * @author Yadickson Soto
 */
@Mojo(name = "generator",
        threadSafe = true,
        defaultPhase = LifecyclePhase.GENERATE_SOURCES,
        requiresProject = true)
public class AutoGenerator extends AbstractMojo {

    /**
     * Maven projeck link.
     */
    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    /**
     * Database driver.
     */
    @Parameter(
            property = "autodblq.driver",
            required = true)
    private String driver;

    /**
     * Database url connection string.
     */
    @Parameter(
            property = "autodblq.connectionString",
            required = true)
    private String connectionString;

    /**
     * Database username.
     */
    @Parameter(
            property = "autodblq.user",
            required = true)
    private String user;

    /**
     * Database password.
     */
    @Parameter(
            property = "autodblq.pass",
            required = true)
    private String pass;

    /**
     * Output source directory.
     */
    @Parameter(
            defaultValue = "${project.build.directory}/generated-sources",
            readonly = true,
            required = false)
    private File outputDirectory;

    /**
     * Output folder name directory.
     */
    @Parameter(
            defaultValue = "autodblq-generator",
            readonly = true,
            required = false)
    private String folderNameGenerator;

    /**
     * Author definition file name.
     */
    @Parameter(
            property = "autodblq.author",
            defaultValue = "author",
            readonly = true,
            required = false)
    private String author;

    /**
     * Version definition file name.
     */
    @Parameter(
            property = "autodblq.version",
            defaultValue = "1.0.0",
            readonly = true,
            required = false)
    private String version;

    /**
     * Liquibase version definition file name.
     */
    @Parameter(
            property = "autodblq.lqversion",
            defaultValue = "3.6",
            readonly = true,
            required = false)
    private String lqversion;

    /**
     * Encode.
     */
    @Parameter(
            property = "autodblq.encode",
            defaultValue = "UTF-8",
            readonly = true,
            required = false)
    private String encode;

    /**
     * List tables to build.
     */
    @Parameter(
            alias = "tables",
            readonly = true,
            required = false)
    private String[] mTables;

    /**
     * List schemas to build.
     */
    @Parameter(
            alias = "schemas",
            readonly = true,
            required = false)
    private String[] mSchemas;

    /**
     * Maven execute method.
     *
     * @throws MojoExecutionException Launch if the generation process throws an
     * error
     */
    @Override
    public void execute() throws MojoExecutionException {

        getLog().info("[AutoGenerator] Driver: " + driver);
        getLog().info("[AutoGenerator] ConnectionString: " + connectionString);
        getLog().info("[AutoGenerator] User: " + user);
        getLog().info("[AutoGenerator] Pass: ****");
        getLog().info("[AutoGenerator] FolderNameGenerator: " + folderNameGenerator);
        getLog().info("[AutoGenerator] OutputDirectory: " + outputDirectory.getPath());
        getLog().info("[AutoGenerator] Version: " + version);
        getLog().info("[AutoGenerator] Author: " + author);
        getLog().info("[AutoGenerator] LiquibaseVersion: " + lqversion);
        getLog().info("[AutoGenerator] Encode: " + encode);

        if (!outputDirectory.exists() && !outputDirectory.mkdirs()) {
            throw new MojoExecutionException("Fail make " + outputDirectory + " directory.");
        }

        project.addCompileSourceRoot(outputDirectory.getPath());

        List<String> fillTables = new ArrayList<String>();
        List<String> fillSchemas = new ArrayList<String>();

        String regexTable = "";
        String regexSchema = "";

        if (mTables != null) {
            for (String table : mTables) {
                if (table != null) {
                    fillTables.add("(" + table.toUpperCase(Locale.ENGLISH) + ")");
                }
            }
        }

        if (mSchemas != null) {
            for (String schema : mSchemas) {
                if (schema != null) {
                    fillSchemas.add("(" + schema.toUpperCase(Locale.ENGLISH) + ")");
                }
            }
        }

        if (!fillTables.isEmpty()) {
            regexTable = StringUtils.join(fillTables, "|");
        }

        if (!fillSchemas.isEmpty()) {
            regexSchema = StringUtils.join(fillSchemas, "|");
        }

        LoggerManager.getInstance().configure(getLog());

        LoggerManager.getInstance().info("[AutoGenerator] RegexTable: " + regexTable);
        LoggerManager.getInstance().info("[AutoGenerator] RegexSchema: " + regexSchema);

        if (fillTables.isEmpty() && fillSchemas.isEmpty()) {
            LoggerManager.getInstance().info("[AutoGenerator] Select tables or schemas ");
            return;
        }

        DriverConnection connManager = new DriverConnection(driver, connectionString, user, pass);

        try {

            Generator generator = GeneratorFactory.getGenarator(driver);
            Connection connection = connManager.getConnection();

            List<Table> found = generator.findTables(connection);
            List<Table> tables = new ArrayList<Table>();

            Pattern patternT = Pattern.compile(regexTable, Pattern.CASE_INSENSITIVE);
            Pattern patternS = Pattern.compile(regexSchema, Pattern.CASE_INSENSITIVE);

            for (Table table : found) {

                String name = table.getName();
                String schema = table.getSchema();

                boolean match = patternT.matcher(name).matches() || patternS.matcher(schema).matches();

                if (match) {
                    tables.add(table);
                }
            }

            for (Table table : tables) {
                LoggerManager.getInstance().info("[AutoGenerator] Process table name: " + table.getFullName());
                generator.fillColumns(connection, table);
                generator.fillPkConstraints(connection, table);
                generator.fillFkConstraints(connection, table);
            }

            DefinitionGenerator definition;
            definition = new DefinitionGenerator(
                    outputDirectory.getPath(),
                    tables,
                    generator.getName(),
                    connManager.getVersion(),
                    version,
                    author,
                    lqversion
            );

            definition.process();

        } catch (RuntimeException ex) {
            getLog().error(ex.getMessage(), ex);
            throw new MojoExecutionException("Fail liquibase generator");
        } catch (Exception ex) {
            getLog().error(ex.getMessage(), ex);
            throw new MojoExecutionException("Fail liquibase generator");
        } finally {
            connManager.closeConnection();
        }
    }

    /**
     * Setter the tables from configuracion
     *
     * @param tables the tables to include from configuracion
     */
    public void setTables(String[] tables) {
        mTables = tables == null ? null : tables.clone();
    }

    /**
     * Setter the schemas from configuracion
     *
     * @param schemas the schemas to include from configuracion
     */
    public void setSchemas(String[] schemas) {
        mSchemas = schemas == null ? null : schemas.clone();
    }
}
