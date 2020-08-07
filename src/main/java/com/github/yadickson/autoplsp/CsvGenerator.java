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

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autoplsp.db.Generator;
import com.github.yadickson.autoplsp.db.common.Table;
import com.github.yadickson.autoplsp.handler.BusinessException;
import com.github.yadickson.autoplsp.logger.LoggerManager;

/**
 * Csv definition file generator.
 *
 * @author Yadickson Soto
 */
public final class CsvGenerator extends TemplateGenerator {

    private final String definitionPath;
    private final Generator generator;
    private final Connection connection;

    private final List<Table> loadData;
    private final String version;
    private final String encode;
    private final String csvQuotchar;
    private final String csvSeparator;

    private final String TABLE = "table";
    private final String CSV = "csv";

    private static final String QUOTCHAR = "quotchar";
    private static final String SEPARATOR = "separator";

    /**
     * Class constructor
     *
     * @param outputDir Output resource directory
     * @param definitionPath definition path
     * @param generator generator
     * @param connection database connection
     * @param loadData data table list
     * @param version files version
     * @param encode encode
     * @param csvQuotchar csv quotchar
     * @param csvSeparator csv separator
     */
    public CsvGenerator(
            final String outputDir,
            final String definitionPath,
            final Generator generator,
            final Connection connection,
            final List<Table> loadData,
            final String version,
            final String encode,
            final String csvQuotchar,
            final String csvSeparator
    ) {
        super(outputDir, null);
        this.definitionPath = definitionPath;
        this.generator = generator;
        this.connection = connection;
        this.loadData = loadData;
        this.encode = encode;
        this.csvQuotchar = csvQuotchar;
        this.csvSeparator = csvSeparator;
        this.version = version;
    }

    /**
     * Create sql definition file from template
     *
     * @throws BusinessException Launch if the generation process throws an
     * error
     */
    public void process() throws BusinessException {
        LoggerManager.getInstance().info("[CsvGenerator] Process template load data");

        Map<String, Object> input = new HashMap<String, Object>();

        input.put(QUOTCHAR, csvQuotchar);
        input.put(SEPARATOR, csvSeparator);

        for (Table table : loadData) {
            String name = table.getName() + ".csv";

            input.put(TABLE, table);

            String filename = getFileNamePath(version + File.separator + CSV, name);

            createTemplate(input, "/definition/csv/data.ftl", filename);

            String sql = generator.getDataTableRegistersQuery(table);
            Long count = generator.getDataTableRegistersCount(connection, sql);

            Long page = 0L;
            Long size = 100L;

            while (process(table, filename, page, size).compareTo(size) == 0) {
                page++;
            }

            LoggerManager.getInstance().info("[fillDataTable]  - registers " + count);
        }

    }

    public Long process(final Table table, final String filename, final Long page, final Long count) {

        String sql = generator.getDataTableQuery(table, csvQuotchar, csvSeparator, page, count);

        if (sql == null) {
            return 0L;
        }

        Long registers = 0L;

        LoggerManager.getInstance().info("[fillDataTable] " + count);
        LoggerManager.getInstance().info("[fillDataTable] " + sql);

        PreparedStatement statement = null;
        PrintWriter output = null;

        try {

            statement = connection.prepareStatement(sql);
            output = new PrintWriter(new FileWriter(filename, true));

            ResultSet result = statement.executeQuery();
            ResultSetMetaData metadata = result.getMetaData();
            int columnCount = metadata.getColumnCount();

            for (int j = 0; j < metadata.getColumnCount(); j++) {
                String cName = metadata.getColumnName(j + 1);
                String cType = metadata.getColumnTypeName(j + 1);
                LoggerManager.getInstance().info(cName + " " + cType);
            }

            while (result.next()) {
                List<String> objs = new ArrayList<String>(columnCount);

                for (int j = 0; j < columnCount; j++) {
                    objs.add(result.getString(j + 1));
                }

                String line = StringUtils.join(objs, csvSeparator);
                output.println(line);
                registers++;
            }

        } catch (Exception ex) {
            registers = 0L;
            LoggerManager.getInstance().info(ex.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (Exception ex) {
                LoggerManager.getInstance().error(ex);
            }
        }

        return registers;
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
