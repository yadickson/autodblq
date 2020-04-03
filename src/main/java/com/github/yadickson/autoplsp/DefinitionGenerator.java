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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sql definition file generator.
 *
 * @author Yadickson Soto
 */
public final class DefinitionGenerator extends TemplateGenerator {

    private final String fileName;
    private final List<Table> tables;
    private final String driverName;
    private final String driverVersion;

    private static final String TABLES = "tables";
    private static final String DRIVER_NAME = "driverName";
    private static final String DRIVER_VERSION = "driverVersion";

    /**
     * Class constructor
     *
     * @param outputDir Output resource directory
     * @param outputFileName Spring configuration file name
     * @param tables table list
     * @param driverName The driver name.
     * @param driverVersion The driver version.
     */
    public DefinitionGenerator(
            final String outputDir,
            final String outputFileName,
            final List<Table> tables,
            final String driverName,
            final String driverVersion) {
        super(outputDir, null);
        this.fileName = outputFileName;
        this.tables = tables;
        this.driverName = driverName;
        this.driverVersion = driverVersion;
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
        input.put(DRIVER_NAME, driverName);
        input.put(DRIVER_VERSION, driverVersion);

        createTemplate(input, "/definition/Definition.ftl", getFileNamePath("", fileName));
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
        return super.getOutputPath("definition" + File.separatorChar + path);
    }

}
