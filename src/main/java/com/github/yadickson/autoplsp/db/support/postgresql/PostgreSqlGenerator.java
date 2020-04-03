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
import com.github.yadickson.autoplsp.db.common.Table;

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

}
