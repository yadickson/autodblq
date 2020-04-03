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
package com.github.yadickson.autoplsp.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.github.yadickson.autoplsp.db.bean.TableBean;
import com.github.yadickson.autoplsp.db.bean.TableFieldBean;
import com.github.yadickson.autoplsp.db.bean.TableFkBean;
import com.github.yadickson.autoplsp.db.bean.TablePkBean;
import com.github.yadickson.autoplsp.db.common.Table;
import com.github.yadickson.autoplsp.db.common.TableField;
import com.github.yadickson.autoplsp.db.common.TableFk;
import com.github.yadickson.autoplsp.db.common.TablePk;
import com.github.yadickson.autoplsp.db.util.FindTableImpl;
import com.github.yadickson.autoplsp.handler.BusinessException;
import com.github.yadickson.autoplsp.logger.LoggerManager;
import java.util.HashMap;

/**
 * Store procedure and function generator interface
 *
 * @author Yadickson Soto
 */
public abstract class Generator {

    private final String name;

    /**
     * Class constructor
     *
     * @param name sp generator name
     */
    public Generator(String name) {
        this.name = name;
    }

    /**
     * Get generator name
     *
     * @return procedure list
     */
    public String getName() {
        return name;
    }

    /**
     * Method getter sql tables.
     *
     * @return sql to find tables
     */
    public abstract String getTablesQuery();

    /**
     * Method getter sql Column by table.
     *
     * @param table table
     * @return sql to find column
     */
    public abstract String getColumnQuery(final Table table);

    /**
     * Method getter sql PrimaryKeyConstraint by table.
     *
     * @param table table
     * @return sql to find primary key
     */
    public abstract String getPrimaryKeyConstraintQuery(final Table table);

    /**
     * Method getter sql ForeignKeyConstraint by table.
     *
     * @param table table
     * @return sql to find foreign key
     */
    public abstract String getForeignKeyConstraintQuery(final Table table);

    /**
     * Method getter sql UniqueConstraint by table.
     *
     * @param table table
     * @return sql to find foreign key
     */
    public abstract String getUniqueConstraintQuery(final Table table);

    /**
     * Method getter sql IndexConstraint by table.
     *
     * @param table table
     * @return sql to find foreign key
     */
    public abstract String getIndexConstraintQuery(final Table table);
    
    public String getString(final String string) {
        return string != null ? string.trim() : null;
    }

    /**
     * Find all table definitions from database.
     *
     * @param connection Database connection.
     * @return table definitions list.
     * @throws BusinessException If error.
     * @throws java.sql.SQLException If error.
     */
    public final List<Table> findTables(
            final Connection connection
    ) throws BusinessException, SQLException {

        String sql = getTablesQuery();

        List<Table> list = new ArrayList<Table>();

        if (sql == null) {
            return list;
        }

        LoggerManager.getInstance().info("[FindTables] Find all tables");

        List<TableBean> tables = new FindTableImpl().getTables(connection, sql);
        Map<String, Table> mapTables = new HashMap<String, Table>();

        for (TableBean t : tables) {

            String tname = getString(t.getName());
            String tschema = getString(t.getSchema());
            String tremarks = getString(t.getRemarks());

            String key = tschema + "-" + tname;

            if (!mapTables.containsKey(key)) {
                LoggerManager.getInstance().info("[FindTables] Found (" + tschema + "." + tname + ")");
                Table table = new Table(tschema, tname, tremarks);
                mapTables.put(key, table);
                list.add(table);
            }
        }

        LoggerManager.getInstance().info("[FindTables] Found " + list.size() + " tables");
        return list;
    }

    /**
     * Fill all column.
     *
     * @param connection Database connection.
     * @param table table to fill.
     * @throws BusinessException If error.
     * @throws java.sql.SQLException If error.
     */
    public final void fillColumns(
            final Connection connection,
            final Table table
    ) throws BusinessException, SQLException {

        String sql = getColumnQuery(table);

        if (sql == null) {
            return;
        }

        List<TableFieldBean> columns = new FindTableImpl().getColumns(connection, sql);

        for (TableFieldBean t : columns) {

            TableField field = new TableField(
                    getString(t.getName()),
                    getString(t.getType()),
                    getString(t.getPosition()),
                    getString(t.getLength()),
                    getString(t.getScale()),
                    getString(t.getNullable()),
                    getString(t.getDefaultval()),
                    getString(t.getIdentity()),
                    getString(t.getRemarks())
            );

            table.getFields().add(field);

            LoggerManager.getInstance().info("[FindColumnTable]  - Field " + field.getName());
            LoggerManager.getInstance().info("[FindColumnTable]          Type: " + field.getType());
            LoggerManager.getInstance().info("[FindColumnTable]          Position: " + field.getPosition());
            LoggerManager.getInstance().info("[FindColumnTable]          Length: " + field.getLength());
            LoggerManager.getInstance().info("[FindColumnTable]          Scale: " + field.getScale());
            LoggerManager.getInstance().info("[FindColumnTable]          Nullable: " + field.getNullable());
            LoggerManager.getInstance().info("[FindColumnTable]          DefaultValue: " + field.getDefaultValue());
            LoggerManager.getInstance().info("[FindColumnTable]          Identity: " + field.getIdentity());
            LoggerManager.getInstance().info("[FindColumnTable]          Identity: " + field.getIsString());
        }

    }

    /**
     * Fill all pk constraints.
     *
     * @param connection Database connection.
     * @param table table to fill.
     * @throws BusinessException If error.
     * @throws java.sql.SQLException If error.
     */
    public final void fillPkConstraints(
            final Connection connection,
            final Table table
    ) throws BusinessException, SQLException {

        String sql = getPrimaryKeyConstraintQuery(table);

        if (sql == null) {
            return;
        }

        List<TablePkBean> pks = new FindTableImpl().getPkConstraints(connection, sql);

        for (TablePkBean pk : pks) {

            TablePk field = new TablePk(
                    getString(pk.getName()),
                    getString(pk.getColumns())
            );

            table.getPkFields().add(field);

            LoggerManager.getInstance().info("[FindPkTables]  - PK " + field.getName());
            LoggerManager.getInstance().info("[FindPkTables]          Columns: " + field.getColumns());
        }

    }

    /**
     * Fill all fk constraints.
     *
     * @param connection Database connection.
     * @param table table to fill.
     * @throws BusinessException If error.
     * @throws java.sql.SQLException If error.
     */
    public final void fillFkConstraints(
            final Connection connection,
            final Table table
    ) throws BusinessException, SQLException {

        String sql = getForeignKeyConstraintQuery(table);

        if (sql == null) {
            return;
        }

        List<TableFkBean> fks = new FindTableImpl().getFkConstraints(connection, sql);

        for (TableFkBean fk : fks) {

            TableFk field = new TableFk(
                    getString(fk.getName()),
                    getString(fk.getColumn()),
                    getString(fk.getTschema()),
                    getString(fk.getTname()),
                    getString(fk.getTcolumn())
            );

            table.getFkFields().add(field);

            LoggerManager.getInstance().info("[FindFkTables]  - FK " + field.getName());
            LoggerManager.getInstance().info("[FindFkTables]          Columns: " + field.getColumn());
            LoggerManager.getInstance().info("[FindFkTables]          Ref Table Schema: " + field.getTschema());
            LoggerManager.getInstance().info("[FindFkTables]          Ref Table Name: " + field.getTname());
            LoggerManager.getInstance().info("[FindFkTables]          Ref Table Columns: " + field.getTcolumn());
        }

    }

}
