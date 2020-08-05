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

import com.github.yadickson.autoplsp.db.common.Function;
import com.github.yadickson.autoplsp.db.common.Table;
import com.github.yadickson.autoplsp.db.common.View;
import com.github.yadickson.autoplsp.db.util.FieldTypeUtil;
import com.github.yadickson.autoplsp.handler.BusinessException;
import com.github.yadickson.autoplsp.logger.LoggerManager;
import java.io.File;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sql definition file generator.
 *
 * @author Yadickson Soto
 */
public final class DefinitionGenerator extends TemplateGenerator {

    private final String definitionPath;
    private final List<Table> tables;
    private final List<Table> loadData;
    private final List<View> views;
    private final List<Function> functions;
    private final String driverName;
    private final String driverVersion;

    private final String dbVersion;
    private final String version;
    private final String author;
    private final String lqVersion;
    private final Boolean lqPro;
    private final String encode;
    private final String csvQuotchar;
    private final String csvSeparator;

    private final String CHANGELOG = "changelog";
    private final String VIEW = "view";
    private final String FUNCTION = "function";
    private final String PROCEDURE = "procedure";

    private static final String FILE = "file";
    private static final String VERSION = "version";
    private static final String AUTHOR = "author";
    private static final String TABLES = "tables";
    private static final String LOAD_DATA = "loaddata";
    private static final String VIEWS = "views";
    private static final String FUNCTIONS = "functions";
    private static final String DRIVER_NAME = "driverName";
    private static final String DRIVER_VERSION = "driverVersion";
    private static final String LQ_VERSION = "lqversion";
    private static final String LQ_PRO = "lqpro";
    private static final String CHANGELOG_PATH = "changelogpath";
    private static final String FILES = "files";
    private static final String TYPE_UTIL = "typeUtil";
    private static final String ENCODE = "encode";
    private static final String QUOTCHAR = "quotchar";
    private static final String SEPARATOR = "separator";
    private static final String DB_VERSION = "dbversion";

    /**
     * Class constructor
     *
     * @param outputDir Output resource directory
     * @param definitionPath definition path
     * @param tables table list
     * @param loadData data table list
     * @param views view list
     * @param functions functions
     * @param driverName The driver name.
     * @param driverVersion The driver version.
     * @param version files version
     * @param author author
     * @param lqVersion liquibase version
     * @param lqPro liquibase pro support
     * @param encode encode
     * @param csvQuotchar csv quotchar
     * @param csvSeparator csv separator
     * @param dbVersion data base version
     */
    public DefinitionGenerator(
            final String outputDir,
            final String definitionPath,
            final List<Table> tables,
            final List<Table> loadData,
            final List<View> views,
            final List<Function> functions,
            final String driverName,
            final String driverVersion,
            final String version,
            final String author,
            final String lqVersion,
            final Boolean lqPro,
            final String encode,
            final String csvQuotchar,
            final String csvSeparator,
            final String dbVersion
    ) {
        super(outputDir, null);
        this.definitionPath = definitionPath;
        this.tables = tables;
        this.loadData = loadData;
        this.views = views;
        this.functions = functions;
        this.driverName = driverName;
        this.driverVersion = driverVersion;
        this.version = version;
        this.author = author;
        this.lqVersion = lqVersion;
        this.lqPro = lqPro;
        this.encode = encode;
        this.csvQuotchar = csvQuotchar;
        this.csvSeparator = csvSeparator;
        this.dbVersion = dbVersion;
    }

    /**
     * Create sql definition file from template
     *
     * @throws BusinessException Launch if the generation process throws an
     * error
     */
    public void process() throws BusinessException {
        LoggerManager.getInstance().info("[LiquibaseGenerator] Process templates");
        Map<String, Object> input = new HashMap<String, Object>();

        input.put(TABLES, tables);
        input.put(LOAD_DATA, loadData);
        input.put(VIEWS, views);
        input.put(VERSION, version);
        input.put(AUTHOR, author);
        input.put(LQ_VERSION, lqVersion);
        input.put(LQ_PRO, lqPro);
        input.put(DRIVER_NAME, driverName);
        input.put(DRIVER_VERSION, driverVersion);
        input.put(CHANGELOG_PATH, CHANGELOG);
        input.put(TYPE_UTIL, new FieldTypeUtil());
        input.put(ENCODE, encode);
        input.put(QUOTCHAR, csvQuotchar);
        input.put(SEPARATOR, csvSeparator);
        input.put(DB_VERSION, dbVersion);

        int file = 0;

        List<String> files = new ArrayList<String>();
        String name;

        if (!tables.isEmpty()) {

            input.put(FILE, ++file);
            name = String.format("%02d-tables.xml", file);
            createTemplate(input, "/definition/changelog/table.ftl", getFileNamePath(version + File.separator + CHANGELOG, name));
            files.add(name);

            input.put(FILE, ++file);
            name = String.format("%02d-default-values.xml", file);
            createTemplate(input, "/definition/changelog/default-value.ftl", getFileNamePath(version + File.separator + CHANGELOG, name));
            files.add(name);

            input.put(FILE, ++file);
            name = String.format("%02d-auto-increment.xml", file);
            createTemplate(input, "/definition/changelog/auto-increment.ftl", getFileNamePath(version + File.separator + CHANGELOG, name));
            files.add(name);

            input.put(FILE, ++file);
            name = String.format("%02d-index.xml", file);
            createTemplate(input, "/definition/changelog/index.ftl", getFileNamePath(version + File.separator + CHANGELOG, name));
            files.add(name);

            input.put(FILE, ++file);
            name = String.format("%02d-unique.xml", file);
            createTemplate(input, "/definition/changelog/unique.ftl", getFileNamePath(version + File.separator + CHANGELOG, name));
            files.add(name);

            input.put(FILE, ++file);
            name = String.format("%02d-primary-keys.xml", file);
            createTemplate(input, "/definition/changelog/primary-key.ftl", getFileNamePath(version + File.separator + CHANGELOG, name));
            files.add(name);

            input.put(FILE, ++file);
            name = String.format("%02d-foreign-keys.xml", file);
            createTemplate(input, "/definition/changelog/foreign-key.ftl", getFileNamePath(version + File.separator + CHANGELOG, name));
            files.add(name);
        }

        if (!loadData.isEmpty()) {
            input.put(FILE, ++file);
            name = String.format("%02d-load-data.xml", file);
            createTemplate(input, "/definition/changelog/load-data.ftl", getFileNamePath(version + File.separator + CHANGELOG, name));
            files.add(name);
        }

        if (!functions.isEmpty()) {
            input.put(FUNCTIONS, functions);
            input.put(FILE, ++file);
            name = String.format("%02d-functions.xml", file);
            createTemplate(input, "/definition/changelog/function.ftl", getFileNamePath(version + File.separator + CHANGELOG, name));
            files.add(name);
        }

        if (!views.isEmpty()) {
            input.put(FILE, ++file);
            name = String.format("%02d-views.xml", file);
            createTemplate(input, "/definition/changelog/view.ftl", getFileNamePath(version + File.separator + CHANGELOG, name));
            files.add(name);
        }

        input.put(FILES, files);
        createTemplate(input, "/definition/masterchangelog.ftl", getFileNamePath(version + File.separator + "", "version-changelog.xml"));

        for (View view : views) {
            input.put(VIEW, view);
            createTemplate(input, "/definition/view.ftl", getFileNamePath(version + File.separator + VIEW, view.getName() + ".sql"));
        }

        for (Function func : functions) {

            if (func.getIsFunction()) {
                input.put(FUNCTION, func);
                createTemplate(input, "/definition/function.ftl", getFileNamePath(version + File.separator + FUNCTION, func.getName() + ".sql"));
            } else {
                input.put(PROCEDURE, func);
                createTemplate(input, "/definition/procedure.ftl", getFileNamePath(version + File.separator + PROCEDURE, func.getName() + ".sql"));
            }
        }

    }

    /**
     * Get output directory path
     *
     * @param path path
     * @return full directory path
     * @exception BusinessException if error
     */
    @Override
    protected String getOutputPath(String path) throws BusinessException {
        return super.getOutputPath(definitionPath + File.separatorChar + path);
    }

}
