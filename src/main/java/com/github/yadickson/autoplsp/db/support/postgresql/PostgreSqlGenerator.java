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
package com.github.yadickson.autoplsp.db.support.postgresql;

import com.github.yadickson.autoplsp.db.Generator;
import com.github.yadickson.autoplsp.db.common.Function;
import com.github.yadickson.autoplsp.db.common.Table;
import com.github.yadickson.autoplsp.db.common.View;

/**
 * Oracle Store procedure and function generator class
 *
 * @author Yadickson Soto
 */
public class PostgreSqlGenerator extends Generator {

    /**
     * Class constructor
     *
     * @param name sp generator
     */
    public PostgreSqlGenerator(String name) {
        super(name);
    }

    /**
     * Method getter sql version information.
     *
     * @return sql to find version
     */
    @Override
    public String getVersionQuery() {
        return "SELECT 'PostgreSQL v' || current_setting('server_version') as \"version\"";
    }

    /**
     * Method getter sql tables.
     *
     * @return sql to find tables
     */
    @Override
    public String getTablesQuery() {
        return null;
    }

    @Override
    public String getColumnQuery(final Table table) {
        return null;
    }

    @Override
    public String getPrimaryKeyConstraintQuery(final Table table) {
        return null;
    }

    @Override
    public String getForeignKeyConstraintQuery(final Table table) {
        return null;
    }

    @Override
    public String getUniqueConstraintQuery(final Table table) {
        return null;
    }

    @Override
    public String getIndexConstraintQuery(final Table table) {
        return null;
    }

    /**
     * Method getter sql default Column by table.
     *
     * @param table table
     * @return sql to find column
     */
    @Override
    public String getDefaultColumnQuery(final Table table) {
        return null;
    }

    /**
     * Method getter sql auto increment Column by table.
     *
     * @param table table
     * @return sql to find column
     */
    @Override
    public String getIncrementColumnQuery(final Table table) {
        return null;
    }

    @Override
    public String getViewsQuery() {
        return null;
    }

    @Override
    public String getTextViewQuery(final View view) {
        return null;
    }

    @Override
    public String getFunctionsQuery() {
        return null;
    }

    @Override
    public String getTextFunctionQuery(final Function function) {
        return null;
    }

    @Override
    public String getProceduresQuery() {
        return null;
    }

    @Override
    public String getTextProcedureQuery(final Function procedure) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDataTableQuery(final Table table, final String quotchar, final String separator, final Long page, final Long blocks) {
        return null;
    }

}
