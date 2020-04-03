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

import com.github.yadickson.autoplsp.db.common.Table;
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
    private final String driverName;
    private final String driverVersion;

    private final String version;
    private final String author;
    private final String lqVersion;
    private final String CHANGELOG = "changelog";

    private static final String FILE = "file";
    private static final String VERSION = "version";
    private static final String AUTHOR = "author";
    private static final String TABLES = "tables";
    private static final String DRIVER_NAME = "driverName";
    private static final String DRIVER_VERSION = "driverVersion";
    private static final String LQ_VERSION = "lqversion";
    private static final String CHANGELOG_PATH = "changelogpath";
    private static final String FILES = "files";

    /**
     * Class constructor
     *
     * @param outputDir Output resource directory
     * @param definitionPath definition path
     * @param tables table list
     * @param driverName The driver name.
     * @param driverVersion The driver version.
     * @param version files version
     * @param lqVersion liquibase version
     * @param author author
     */
    public DefinitionGenerator(
            final String outputDir,
            final String definitionPath,
            final List<Table> tables,
            final String driverName,
            final String driverVersion,
            final String version,
            final String author,
            final String lqVersion
    ) {
        super(outputDir, null);
        this.definitionPath = definitionPath;
        this.tables = tables;
        this.driverName = driverName;
        this.driverVersion = driverVersion;
        this.version = version;
        this.author = author;
        this.lqVersion = lqVersion;
    }

    /**
     * Create sql definition file from template
     *
     * @throws BusinessException Launch if the generation process throws an
     * error
     */
    public void process() throws BusinessException {
        LoggerManager.getInstance().info("[LiquibaseGenerator] Process template tables");
        Map<String, Object> input = new HashMap<String, Object>();

        input.put(TABLES, tables);
        input.put(VERSION, version);
        input.put(AUTHOR, author);
        input.put(LQ_VERSION, lqVersion);
        input.put(DRIVER_NAME, driverName);
        input.put(DRIVER_VERSION, driverVersion);
        input.put(CHANGELOG_PATH, CHANGELOG);

        int file = 0;

        List<String> files = new ArrayList<String>();
        String name;

        input.put(FILE, ++file);
        name = String.format("%02d-tables.xml", file);
        createTemplate(input, "/definition/changelog/00-tables.ftl", getFileNamePath(CHANGELOG, name));
        files.add(name);

        input.put(FILE, ++file);
        name = String.format("%02d-default-values.xml", file);
        createTemplate(input, "/definition/changelog/01-default-values.ftl", getFileNamePath(CHANGELOG, name));
        files.add(name);

        input.put(FILE, ++file);
        name = String.format("%02d-auto-increment.xml", file);
        createTemplate(input, "/definition/changelog/02-auto-increment.ftl", getFileNamePath(CHANGELOG, name));
        files.add(name);

        input.put(FILE, ++file);
        name = String.format("%02d-index.xml", file);
        createTemplate(input, "/definition/changelog/03-index.ftl", getFileNamePath(CHANGELOG, name));
        files.add(name);

        input.put(FILE, ++file);
        name = String.format("%02d-unique.xml", file);
        createTemplate(input, "/definition/changelog/04-unique.ftl", getFileNamePath(CHANGELOG, name));
        files.add(name);

        input.put(FILE, ++file);
        name = String.format("%02d-primary-keys.xml", file);
        createTemplate(input, "/definition/changelog/05-primary-keys.ftl", getFileNamePath(CHANGELOG, name));
        files.add(name);

        input.put(FILE, ++file);
        name = String.format("%02d-foreign-keys.xml", file);
        createTemplate(input, "/definition/changelog/06-foreign-keys.ftl", getFileNamePath(CHANGELOG, name));
        files.add(name);

        input.put(FILES, files);
        createTemplate(input, "/definition/masterchangelog.ftl", getFileNamePath("", "masterchangelog.xml"));
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
