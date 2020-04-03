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
package com.github.yadickson.autoplsp.db.support.db2;

import com.github.yadickson.autoplsp.db.Generator;
import com.github.yadickson.autoplsp.db.common.Table;

/**
 * Oracle Store procedure and function generator class.
 *
 * @author Yadickson Soto
 */
public class DB2Generator extends Generator {

    /**
     * Class constructor.
     *
     * @param name sp generator
     */
    public DB2Generator(String name) {
        super(name);
    }

    /**
     * Method getter sql tables.
     *
     * @return sql to find tables
     */
    @Override
    public String getTablesQuery() {
        return "SELECT tabschema schema, tabname name, remarks FROM syscat.tables WHERE TYPE = 'T' ORDER BY SCHEMA ASC, name asc";
    }

    @Override
    public String getColumnQuery(final Table table) {
        return "SELECT t.colno POSITION, t.colname name, t.typename type, t.length, t.NULLS nullable, t.scale, t.default defaultval, t.IDENTITY, t.remarks\n"
                + "  FROM syscat.columns t\n"
                + " WHERE t.tabname = '" + table.getName() + "'\n"
                + "   AND t.tabschema = '" + table.getSchema() + "'\n"
                + " ORDER BY t.colno";
    }

    @Override
    public String getPrimaryKeyConstraintQuery(final Table table) {
        return "SELECT t.name, listagg(t.COLNAME, ',') columns\n"
                + "from\n"
                + "(\n"
                + "SELECT DISTINCT K.CONSTNAME name, TRIM(K.COLNAME) COLNAME\n"
                + "                  FROM syscat.keycoluse K\n"
                + "                  INNER JOIN syscat.tabconst c on c.tabschema = k.tabschema AND c.CONSTNAME = k.CONSTNAME AND c.TYPE = 'P'\n"
                + "                 WHERE k.tabname = '" + table.getName() + "'\n"
                + "                   AND k.tabschema = '" + table.getSchema() + "'\n"
                + ") t\n"
                + "group BY t.name\n"
                + "order BY t.name asc";
    }

    @Override
    public String getForeignKeyConstraintQuery(final Table table) {
        return "select \n"
                + "    ref.constname name,\n"
                + "    TRIM(REF.FK_COLNAMES) columns,\n"
                + "    ref.reftabschema tschema,\n"
                + "    ref.reftabname tname,\n"
                + "    TRIM(REF.PK_COLNAMES) tcolumns\n"
                + "from syscat.references REF\n"
                + "WHERE ref.tabname = '" + table.getName() + "'\n"
                + "   AND ref.tabschema = '" + table.getSchema() + "'\n"
                + "order BY name asc";
    }

    @Override
    public String getUniqueConstraintQuery(final Table table) {
        return null;
    }

    @Override
    public String getIndexConstraintQuery(final Table table) {
        return "select\n"
                + "    indname as name,\n"
                + "    replace(trim(replace(replace(colnames,'-',' '),'+',' ')), ' ', ',') as columns,\n"
                + "    DECODE(uniquerule, 'U', 'Y', 'N') isunique\n"
                + "from syscat.indexes \n"
                + "where uniquerule != 'P'\n"
                + "AND tabname = '" + table.getName() + "'\n"
                + "AND tabschema = '" + table.getSchema() + "'\n"
                + "order BY name asc";
    }

}
