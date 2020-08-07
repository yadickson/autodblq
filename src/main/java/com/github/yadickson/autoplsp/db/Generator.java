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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.yadickson.autoplsp.db.bean.ContentBean;
import com.github.yadickson.autoplsp.db.bean.CountBean;
import com.github.yadickson.autoplsp.db.bean.FunctionBean;
import com.github.yadickson.autoplsp.db.bean.TableBean;
import com.github.yadickson.autoplsp.db.bean.TableDefBean;
import com.github.yadickson.autoplsp.db.bean.TableFieldBean;
import com.github.yadickson.autoplsp.db.bean.TableFkBean;
import com.github.yadickson.autoplsp.db.bean.TableIncBean;
import com.github.yadickson.autoplsp.db.bean.TableIndBean;
import com.github.yadickson.autoplsp.db.bean.TablePkBean;
import com.github.yadickson.autoplsp.db.bean.TableUnqBean;
import com.github.yadickson.autoplsp.db.bean.VersionBean;
import com.github.yadickson.autoplsp.db.bean.ViewBean;
import com.github.yadickson.autoplsp.db.common.Function;
import com.github.yadickson.autoplsp.db.common.Table;
import com.github.yadickson.autoplsp.db.common.TableDef;
import com.github.yadickson.autoplsp.db.common.TableField;
import com.github.yadickson.autoplsp.db.common.TableFk;
import com.github.yadickson.autoplsp.db.common.TableInc;
import com.github.yadickson.autoplsp.db.common.TableInd;
import com.github.yadickson.autoplsp.db.common.TablePk;
import com.github.yadickson.autoplsp.db.common.TableUnq;
import com.github.yadickson.autoplsp.db.common.View;
import com.github.yadickson.autoplsp.db.util.SqlExecuteImpl;
import com.github.yadickson.autoplsp.handler.BusinessException;
import com.github.yadickson.autoplsp.logger.LoggerManager;

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
     * Method getter sql version information.
     *
     * @return sql to find version
     */
    public abstract String getVersionQuery();

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

    /**
     * Method getter sql default Column by table.
     *
     * @param table table
     * @return sql to find column
     */
    public abstract String getDefaultColumnQuery(final Table table);

    /**
     * Method getter sql auto increment Column by table.
     *
     * @param table table
     * @return sql to find column
     */
    public abstract String getIncrementColumnQuery(final Table table);

    /**
     * Method getter sql views.
     *
     * @return sql to find views
     */
    public abstract String getViewsQuery();

    /**
     * Method getter text view query.
     *
     * @param view view
     * @return sql to find text view
     */
    public abstract String getTextViewQuery(final View view);

    /**
     * Method getter sql functions.
     *
     * @return sql to find functions
     */
    public abstract String getFunctionsQuery();

    /**
     * Method getter text function query.
     *
     * @param function function
     * @return sql to find text function
     */
    public abstract String getTextFunctionQuery(final Function function);

    /**
     * Method getter sql procedures.
     *
     * @return sql to find procedures
     */
    public abstract String getProceduresQuery();

    /**
     * Method getter sql data tables count.
     *
     * @param table table
     * @return sql to find data table count
     */
    public String getDataTableRegistersQuery(final Table table) {
        return "SELECT COUNT(1) AS COUNT FROM " + table.getFullName();
    }

    /**
     * Method getter data tables count.
     *
     * @param connection connection
     * @param sql to find data table count
     * @return register counts
     * @throws BusinessException if error
     */
    public Long getDataTableRegistersCount(final Connection connection, final String sql) throws BusinessException {

        if (sql == null) {
            return 0L;
        }

        List<CountBean> counts = new SqlExecuteImpl().execute(connection, sql, CountBean.class);

        if (counts.isEmpty()) {
            return 0L;
        }

        return Long.parseLong(counts.get(0).getCount());
    }

    /**
     * Method getter sql data tables.
     *
     * @param table table
     * @param quotchar char para string
     * @param separator separator
     * @param page page to find registers, start with zero
     * @param blocks blocks to read
     * @return sql to find data table contents
     */
    public abstract String getDataTableQuery(final Table table, final String quotchar, final String separator, final Long page, final Long blocks);

    /**
     * Method getter text procedure query.
     *
     * @param procedure procedure
     * @return sql to find text procedure
     */
    public abstract String getTextProcedureQuery(final Function procedure);

    public String getString(final String string) {
        return string != null ? string.trim() : null;
    }

    public String getStringJoin(final String string) {
        return string != null ? string.replaceAll("\\s+", ",") : null;
    }

    /**
     * Find version from database.
     *
     * @param connection Database connection.
     * @return version.
     * @throws BusinessException If error.
     * @throws java.sql.SQLException If error.
     */
    public final String getVersion(
            final Connection connection
    ) throws BusinessException, SQLException {

        String sql = getVersionQuery();

        if (sql == null) {
            return null;
        }

        String version = null;

        LoggerManager.getInstance().info("[FindVersion] Find database version");

        List<VersionBean> versions = new SqlExecuteImpl().execute(connection, sql, VersionBean.class);

        for (VersionBean v : versions) {
            version = getString(v.getVersion());
            break;
        }

        LoggerManager.getInstance().info("[FindVersion] Found " + version);
        return version;

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

        List<TableBean> tables = new SqlExecuteImpl().execute(connection, sql, TableBean.class);
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

        List<TableFieldBean> columns = new SqlExecuteImpl().execute(connection, sql, TableFieldBean.class);

        for (TableFieldBean t : columns) {

            TableField field = new TableField(
                    getString(t.getName()),
                    getString(t.getType()),
                    getString(t.getPosition()),
                    getString(t.getLength()),
                    getString(t.getScale()),
                    getString(t.getNullable()),
                    getString(t.getRemarks())
            );

            table.getFields().add(field);

            LoggerManager.getInstance().info("[FindColumnTable]  - Field " + field.getName());
            LoggerManager.getInstance().info("[FindColumnTable]          Type: " + field.getType());
            LoggerManager.getInstance().info("[FindColumnTable]          Position: " + field.getPosition());
            LoggerManager.getInstance().info("[FindColumnTable]          Length: " + field.getLength());
            LoggerManager.getInstance().info("[FindColumnTable]          Scale: " + field.getScale());
            LoggerManager.getInstance().info("[FindColumnTable]          Nullable: " + field.getNullable());
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

        List<TablePkBean> pks = new SqlExecuteImpl().execute(connection, sql, TablePkBean.class);

        for (TablePkBean pk : pks) {

            TablePk field = new TablePk(
                    getString(pk.getName()),
                    getStringJoin(getString(pk.getColumns()))
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

        List<TableFkBean> fks = new SqlExecuteImpl().execute(connection, sql, TableFkBean.class);

        for (TableFkBean fk : fks) {

            TableFk field = new TableFk(
                    getString(fk.getName()),
                    getStringJoin(getString(fk.getColumns())),
                    getString(fk.getTschema()),
                    getString(fk.getTname()),
                    getStringJoin(getString(fk.getTcolumns()))
            );

            table.getFkFields().add(field);

            LoggerManager.getInstance().info("[FindFkTables]  - FK " + field.getName());
            LoggerManager.getInstance().info("[FindFkTables]          Columns: " + field.getColumns());
            LoggerManager.getInstance().info("[FindFkTables]          Ref Table Schema: " + field.getTschema());
            LoggerManager.getInstance().info("[FindFkTables]          Ref Table Name: " + field.getTname());
            LoggerManager.getInstance().info("[FindFkTables]          Ref Table Columns: " + field.getTcolumns());
        }

    }

    /**
     * Fill all unique constraints.
     *
     * @param connection Database connection.
     * @param table table to fill.
     * @throws BusinessException If error.
     * @throws java.sql.SQLException If error.
     */
    public final void fillUnqConstraints(
            final Connection connection,
            final Table table
    ) throws BusinessException, SQLException {

        String sql = getUniqueConstraintQuery(table);

        if (sql == null) {
            return;
        }

        List<TableUnqBean> uniques = new SqlExecuteImpl().execute(connection, sql, TableUnqBean.class);

        for (TableUnqBean unq : uniques) {

            TableUnq field = new TableUnq(
                    getString(unq.getName()),
                    getString(unq.getColumns())
            );

            table.getUnqFields().add(field);

            LoggerManager.getInstance().info("[FindUnqTables]  - FK " + field.getName());
            LoggerManager.getInstance().info("[FindUnqTables]          Columns: " + field.getColumns());
        }

    }

    /**
     * Fill all index constraints.
     *
     * @param connection Database connection.
     * @param table table to fill.
     * @throws BusinessException If error.
     * @throws java.sql.SQLException If error.
     */
    public final void fillIndConstraints(
            final Connection connection,
            final Table table
    ) throws BusinessException, SQLException {

        String sql = getIndexConstraintQuery(table);

        if (sql == null) {
            return;
        }

        List<TableIndBean> indexs = new SqlExecuteImpl().execute(connection, sql, TableIndBean.class);

        for (TableIndBean index : indexs) {

            TableInd field = new TableInd(
                    getString(index.getName()),
                    getString(index.getColumns()),
                    getString(index.getIsunique())
            );

            table.getIndFields().add(field);

            LoggerManager.getInstance().info("[FindIndTables]  - FK " + field.getName());
            LoggerManager.getInstance().info("[FindIndTables]          Columns: " + field.getColumns());
        }

    }

    /**
     * Fill all default constraints.
     *
     * @param connection Database connection.
     * @param table table to fill.
     * @throws BusinessException If error.
     * @throws java.sql.SQLException If error.
     */
    public final void fillDefConstraints(
            final Connection connection,
            final Table table
    ) throws BusinessException, SQLException {

        String sql = getDefaultColumnQuery(table);

        if (sql == null) {
            return;
        }

        List<TableDefBean> defaults = new SqlExecuteImpl().execute(connection, sql, TableDefBean.class);

        for (TableDefBean def : defaults) {

            TableDef field = new TableDef(
                    getString(def.getColumn()),
                    getString(def.getType()),
                    getString(def.getValue())
            );

            table.getDefFields().add(field);

            LoggerManager.getInstance().info("[FindDefTables]  - Column " + field.getColumn());
            LoggerManager.getInstance().info("[FindDefTables]          Type: " + field.getType());
            LoggerManager.getInstance().info("[FindDefTables]          Value: " + field.getValue());
        }

    }

    /**
     * Fill all increment constraints.
     *
     * @param connection Database connection.
     * @param table table to fill.
     * @throws BusinessException If error.
     * @throws java.sql.SQLException If error.
     */
    public final void fillIncConstraints(
            final Connection connection,
            final Table table
    ) throws BusinessException, SQLException {

        String sql = getIncrementColumnQuery(table);

        if (sql == null) {
            return;
        }

        List<TableIncBean> increments = new SqlExecuteImpl().execute(connection, sql, TableIncBean.class);

        for (TableIncBean inc : increments) {

            TableInc field = new TableInc(
                    getString(inc.getColumn()),
                    getString(inc.getType())
            );

            table.getIncFields().add(field);

            LoggerManager.getInstance().info("[FindIncTables]  - Column " + field.getColumn());
            LoggerManager.getInstance().info("[FindIncTables]          Type: " + field.getType());
        }

    }

    /**
     * Find all view definitions from database.
     *
     * @param connection Database connection.
     * @return view definitions list.
     * @throws BusinessException If error.
     * @throws java.sql.SQLException If error.
     */
    public final List<View> findViews(
            final Connection connection
    ) throws BusinessException, SQLException {

        String sql = getViewsQuery();

        List<View> list = new ArrayList<View>();

        if (sql == null) {
            return list;
        }

        LoggerManager.getInstance().info("[FindViews] Find all views");

        List<ViewBean> views = new SqlExecuteImpl().execute(connection, sql, ViewBean.class);
        Map<String, View> mapViews = new HashMap<String, View>();

        for (ViewBean v : views) {

            String tname = getString(v.getName());
            String tschema = getString(v.getSchema());

            String key = tschema + "-" + tname;

            if (!mapViews.containsKey(key)) {
                LoggerManager.getInstance().info("[FindViews] Found (" + tschema + "." + tname + ")");
                View view = new View(tschema, tname);
                mapViews.put(key, view);
                list.add(view);
            }
        }

        LoggerManager.getInstance().info("[FindViews] Found " + list.size() + " views");
        return list;
    }

    /**
     * Fill text view.
     *
     * @param connection Database connection.
     * @param view view to fill.
     * @throws BusinessException If error.
     * @throws java.sql.SQLException If error.
     */
    public final void fillTextView(
            final Connection connection,
            final View view
    ) throws BusinessException, SQLException {

        String sql = getTextViewQuery(view);

        if (sql == null) {
            return;
        }

        List<ContentBean> text = new SqlExecuteImpl().execute(connection, sql, ContentBean.class);

        for (ContentBean value : text) {
            view.setText(value.getText());
            LoggerManager.getInstance().info("[FindTextView]  - " + view.getFullName());
        }

    }

    /**
     * Find all view definitions from database.
     *
     * @param connection Database connection.
     * @return view definitions list.
     * @throws BusinessException If error.
     * @throws java.sql.SQLException If error.
     */
    public final List<Function> findFunctions(
            final Connection connection
    ) throws BusinessException, SQLException {

        String sql = getFunctionsQuery();

        List<Function> list = new ArrayList<Function>();

        if (sql == null) {
            return list;
        }

        LoggerManager.getInstance().info("[FindFunctions] Find all functions");

        List<FunctionBean> functions = new SqlExecuteImpl().execute(connection, sql, FunctionBean.class);
        Map<String, Function> mapFunctions = new HashMap<String, Function>();

        for (FunctionBean f : functions) {

            String tname = getString(f.getName());
            String tschema = getString(f.getSchema());

            String key = tschema + "-" + tname;

            if (!mapFunctions.containsKey(key)) {
                LoggerManager.getInstance().info("[FindFunctions] Found (" + tschema + "." + tname + ")");
                Function func = new Function(tschema, tname, "Y");
                mapFunctions.put(key, func);
                list.add(func);
            }
        }

        LoggerManager.getInstance().info("[FindFunctions] Found " + list.size() + " functions");
        return list;
    }

    /**
     * Fill text function.
     *
     * @param connection Database connection.
     * @param function function to fill.
     * @throws BusinessException If error.
     * @throws java.sql.SQLException If error.
     */
    public final void fillTextFunction(
            final Connection connection,
            final Function function
    ) throws BusinessException, SQLException {

        String sql = getTextFunctionQuery(function);

        if (sql == null) {
            return;
        }

        List<ContentBean> text = new SqlExecuteImpl().execute(connection, sql, ContentBean.class);

        for (ContentBean value : text) {
            function.setText(value.getText());
            LoggerManager.getInstance().info("[FindTextFunction]  - " + function.getFullName());
        }

    }

    /**
     * Find all procedure definitions from database.
     *
     * @param connection Database connection.
     * @return procedure definitions list.
     * @throws BusinessException If error.
     * @throws java.sql.SQLException If error.
     */
    public final List<Function> findProcedures(
            final Connection connection
    ) throws BusinessException, SQLException {

        String sql = getProceduresQuery();

        List<Function> list = new ArrayList<Function>();

        if (sql == null) {
            return list;
        }

        LoggerManager.getInstance().info("[FindProcedures] Find all procedures");

        List<FunctionBean> procedures = new SqlExecuteImpl().execute(connection, sql, FunctionBean.class);
        Map<String, Function> mapProcedures = new HashMap<String, Function>();

        for (FunctionBean p : procedures) {

            String tname = getString(p.getName());
            String tschema = getString(p.getSchema());

            String key = tschema + "-" + tname;

            if (!mapProcedures.containsKey(key)) {
                LoggerManager.getInstance().info("[FindProcedures] Found (" + tschema + "." + tname + ")");
                Function proc = new Function(tschema, tname, "N");
                mapProcedures.put(key, proc);
                list.add(proc);
            }
        }

        LoggerManager.getInstance().info("[FindProcedures] Found " + list.size() + " procedures");
        return list;
    }

    /**
     * Fill text procedure.
     *
     * @param connection Database connection.
     * @param procedure procedure to fill.
     * @throws BusinessException If error.
     * @throws java.sql.SQLException If error.
     */
    public final void fillTextProcedure(
            final Connection connection,
            final Function procedure
    ) throws BusinessException, SQLException {

        String sql = getTextProcedureQuery(procedure);

        if (sql == null) {
            return;
        }

        List<ContentBean> text = new SqlExecuteImpl().execute(connection, sql, ContentBean.class);

        for (ContentBean value : text) {
            procedure.setText(value.getText());
            LoggerManager.getInstance().info("[FindProcedureText]  - " + procedure.getFullName());
        }

    }

}
