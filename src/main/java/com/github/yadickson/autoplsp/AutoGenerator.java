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
import com.github.yadickson.autoplsp.db.common.Function;
import com.github.yadickson.autoplsp.db.common.Table;
import com.github.yadickson.autoplsp.db.common.View;
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
            property = "autodblq.url",
            required = true)
    private String url;

    /**
     * Database username.
     */
    @Parameter(
            property = "autodblq.username",
            required = true)
    private String username;

    /**
     * Database password.
     */
    @Parameter(
            property = "autodblq.password",
            required = true)
    private String password;

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
     * Author definition folder name.
     */
    @Parameter(
            property = "autodblq.definitionFolder",
            defaultValue = "definition",
            readonly = true,
            required = false)
    private String definitionFolder;

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
     * Liquibase version definition file name.
     */
    @Parameter(
            property = "autodblq.lqpro",
            defaultValue = "false",
            readonly = true,
            required = false)
    private String lqpro;

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
     * List views to build.
     */
    @Parameter(
            alias = "views",
            readonly = true,
            required = false)
    private String[] mViews;

    /**
     * List functions and procedures to build.
     */
    @Parameter(
            alias = "functions",
            readonly = true,
            required = false)
    private String[] mFunctions;

    /**
     * List schemas to build.
     */
    @Parameter(
            alias = "schemas",
            readonly = true,
            required = false)
    private String[] mSchemas;

    /**
     * List sort functions and procedures to build.
     */
    @Parameter(
            alias = "sortFunctions",
            readonly = true,
            required = false)
    private String[] mSortFunctions;

    /**
     * List functions and procedures to build after views.
     */
    @Parameter(
            alias = "sortViews",
            readonly = true,
            required = false)
    private String[] mSortViews;

    /**
     * List to exclude tables to build.
     */
    @Parameter(
            alias = "excludeTables",
            readonly = true,
            required = false)
    private String[] mExcludeTables;

    /**
     * List to exclude views to build.
     */
    @Parameter(
            alias = "excludeViews",
            readonly = true,
            required = false)
    private String[] mExcludeViews;

    /**
     * List to exclude functions to build.
     */
    @Parameter(
            alias = "excludeFunctions",
            readonly = true,
            required = false)
    private String[] mExcludeFunctions;

    /**
     * Maven execute method.
     *
     * @throws MojoExecutionException Launch if the generation process throws an
     * error
     */
    @Override
    public void execute() throws MojoExecutionException {

        getLog().info("[AutoGenerator] Driver: " + driver);
        getLog().info("[AutoGenerator] ConnectionString: " + url);
        getLog().info("[AutoGenerator] User: " + username);
        getLog().info("[AutoGenerator] Pass: ****");
        getLog().info("[AutoGenerator] FolderNameGenerator: " + folderNameGenerator);
        getLog().info("[AutoGenerator] OutputDirectory: " + outputDirectory.getPath());
        getLog().info("[AutoGenerator] DefinitionFolder: " + definitionFolder);
        getLog().info("[AutoGenerator] Version: " + version);
        getLog().info("[AutoGenerator] Author: " + author);
        getLog().info("[AutoGenerator] LiquibaseVersion: " + lqversion);
        getLog().info("[AutoGenerator] LiquibaseProduction: " + lqpro);

        getLog().info("[AutoGenerator] Encode: " + encode);

        if (!outputDirectory.exists() && !outputDirectory.mkdirs()) {
            throw new MojoExecutionException("Fail make " + outputDirectory + " directory.");
        }

        project.addCompileSourceRoot(outputDirectory.getPath());

        String regexTable = getRegex(mTables);
        String regexView = getRegex(mViews);
        String regexFunction = getRegex(mFunctions);
        String regexSortFunction = getRegex(mSortFunctions);
        String regexSortViews = getRegex(mSortViews);
        String regexSchema = getRegex(mSchemas);
        String regexExcludeTable = getRegex(mExcludeTables);
        String regexExcludeView = getRegex(mExcludeViews);
        String regexExcludeFunction = getRegex(mExcludeFunctions);

        LoggerManager.getInstance().configure(getLog());

        LoggerManager.getInstance().info("[AutoGenerator] RegexTable: " + regexTable);
        LoggerManager.getInstance().info("[AutoGenerator] RegexView: " + regexView);
        LoggerManager.getInstance().info("[AutoGenerator] RegexFunction: " + regexFunction);
        LoggerManager.getInstance().info("[AutoGenerator] RegexSortFunction: " + regexSortFunction);
        LoggerManager.getInstance().info("[AutoGenerator] RegexSortView: " + regexSortViews);
        LoggerManager.getInstance().info("[AutoGenerator] RegexSchema: " + regexSchema);
        LoggerManager.getInstance().info("[AutoGenerator] RegexExcludeTable: " + regexExcludeTable);
        LoggerManager.getInstance().info("[AutoGenerator] RegexExcludeView: " + regexExcludeView);
        LoggerManager.getInstance().info("[AutoGenerator] RegexExcludeFunction: " + regexExcludeFunction);

        if (regexTable.isEmpty() && regexSchema.isEmpty()) {
            LoggerManager.getInstance().info("[AutoGenerator] Select tables or schemas ");
            return;
        }

        DriverConnection connManager = new DriverConnection(driver, url, username, password);

        try {

            Generator generator = GeneratorFactory.getGenarator(driver);
            Connection connection = connManager.getConnection();

            List<Table> tablesFound = generator.findTables(connection);
            List<Table> tables = new ArrayList<Table>();

            Pattern patternT = Pattern.compile(regexTable, Pattern.CASE_INSENSITIVE);
            Pattern patternV = Pattern.compile(regexView, Pattern.CASE_INSENSITIVE);
            Pattern patternF = Pattern.compile(regexFunction, Pattern.CASE_INSENSITIVE);
            Pattern patternSortV = Pattern.compile(regexSortViews, Pattern.CASE_INSENSITIVE);
            Pattern patternSortF = Pattern.compile(regexSortFunction, Pattern.CASE_INSENSITIVE);
            Pattern patternS = Pattern.compile(regexSchema, Pattern.CASE_INSENSITIVE);
            Pattern patternExcludeT = Pattern.compile(regexExcludeTable, Pattern.CASE_INSENSITIVE);
            Pattern patternExcludeV = Pattern.compile(regexExcludeView, Pattern.CASE_INSENSITIVE);
            Pattern patternExcludeF = Pattern.compile(regexExcludeFunction, Pattern.CASE_INSENSITIVE);

            for (Table table : tablesFound) {

                String name = table.getName();
                String schema = table.getSchema();

                boolean match = patternT.matcher(name).matches() || patternS.matcher(schema).matches();

                if (match && !patternExcludeT.matcher(name).matches()) {
                    tables.add(table);
                }
            }

            for (Table table : tables) {
                LoggerManager.getInstance().info("[AutoGenerator] Process table name: " + table.getFullName());
                generator.fillColumns(connection, table);
                generator.fillPkConstraints(connection, table);
                generator.fillFkConstraints(connection, table);
                generator.fillUnqConstraints(connection, table);
                generator.fillIndConstraints(connection, table);
                generator.fillDefConstraints(connection, table);
                generator.fillIncConstraints(connection, table);
            }

            List<View> viewsFound = generator.findViews(connection);
            List<View> sortViews = new ArrayList<View>();
            List<View> viewList = new ArrayList<View>();

            for (View view : viewsFound) {

                String name = view.getName();
                String schema = view.getSchema();

                boolean match = patternV.matcher(name).matches() || patternS.matcher(schema).matches();

                if (match && !patternExcludeV.matcher(name).matches()) {
                    LoggerManager.getInstance().info("[AutoGenerator] Process view name: " + view.getFullName());
                    generator.fillTextView(connection, view);

                    if (patternSortV.matcher(name).matches()) {
                        sortViews.add(view);
                    } else {
                        viewList.add(view);
                    }
                }
            }

            sortViews.addAll(viewList);

            List<Function> functionsFound = generator.findFunctions(connection);
            List<Function> proceduresFound = generator.findProcedures(connection);

            List<Function> sortFunctions = new ArrayList<Function>();
            List<Function> functionsList = new ArrayList<Function>();

            functionsFound.addAll(proceduresFound);

            for (Function func : functionsFound) {

                String name = func.getName();
                String schema = func.getSchema();

                boolean match = patternF.matcher(name).matches() || patternS.matcher(schema).matches();

                if (match && !patternExcludeF.matcher(name).matches()) {
                    LoggerManager.getInstance().info("[AutoGenerator] Process function name: " + func.getFullName());

                    if (func.getIsFunction()) {
                        generator.fillTextFunction(connection, func);
                    } else {
                        generator.fillTextProcedure(connection, func);
                    }

                    if (patternSortF.matcher(name).matches()) {
                        sortFunctions.add(func);
                    } else {
                        functionsList.add(func);
                    }
                }
            }

            sortFunctions.addAll(functionsList);

            DefinitionGenerator definition;
            definition = new DefinitionGenerator(
                    outputDirectory.getPath(),
                    definitionFolder,
                    tables,
                    sortViews,
                    sortFunctions,
                    generator.getName(),
                    connManager.getVersion(),
                    version,
                    author,
                    lqversion,
                    "true".equalsIgnoreCase(lqpro)
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

    public String getRegex(final String[] array) {

        List<String> list = new ArrayList<String>();

        if (array != null) {
            for (String element : array) {
                if (element != null) {
                    list.add("(" + element.toUpperCase(Locale.ENGLISH) + ")");
                }
            }
        }

        String result = "";

        if (!list.isEmpty()) {
            result = StringUtils.join(list, "|");
        }

        return result;
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
     * Setter the exclude tables from configuracion
     *
     * @param tables the tables to include from configuracion
     */
    public void setExcludeTables(String[] tables) {
        mExcludeTables = tables == null ? null : tables.clone();
    }

    /**
     * Setter the views from configuracion
     *
     * @param views the views to include from configuracion
     */
    public void setViews(String[] views) {
        mViews = views == null ? null : views.clone();
    }

    /**
     * Setter the views from configuracion
     *
     * @param views the views to include from configuracion
     */
    public void setExcludeViews(String[] views) {
        mExcludeViews = views == null ? null : views.clone();
    }

    /**
     * Setter the functions from configuracion
     *
     * @param functions the functions to include from configuracion
     */
    public void setFunctions(String[] functions) {
        mFunctions = functions == null ? null : functions.clone();
    }

    /**
     * Setter the exclude functions from configuracion
     *
     * @param functions the functions to include from configuracion
     */
    public void setExcludeFunctions(String[] functions) {
        mExcludeFunctions = functions == null ? null : functions.clone();
    }

    /**
     * Setter the schemas from configuracion
     *
     * @param schemas the schemas to include from configuracion
     */
    public void setSchemas(String[] schemas) {
        mSchemas = schemas == null ? null : schemas.clone();
    }

    /**
     * Setter the sort views from configuracion
     *
     * @param sort the views to include from configuracion
     */
    public void setSortViews(String[] sort) {
        mSortViews = sort == null ? null : sort.clone();
    }

    /**
     * Setter the sort function and procedures from configuracion
     *
     * @param sort the function and procedures to include from configuracion
     */
    public void setSortFunctions(String[] sort) {
        mSortFunctions = sort == null ? null : sort.clone();
    }

}
