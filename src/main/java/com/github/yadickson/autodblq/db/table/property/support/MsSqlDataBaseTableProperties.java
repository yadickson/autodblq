/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.property.support;

import com.github.yadickson.autodblq.db.connection.driver.Driver;

/**
 *
 * @author Yadickson Soto
 */
public class MsSqlDataBaseTableProperties extends DataBaseTablePropertiesBase {

    public MsSqlDataBaseTableProperties() {
        super(Driver.MSSQL);
    }

    @Override
    protected String getUuid() {
        return "uuid";
    }

    @Override
    protected String getBoolean() {
        return "boolean";
    }

    @Override
    protected String getInteger() {
        return "int";
    }

    @Override
    protected String getDatetime() {
        return "datetimeoffset";
    }

    @Override
    protected String getVarchar() {
        return "varchar";
    }

    @Override
    protected String getMaxString() {
        return "(max)";
    }

    @Override
    protected String getDefaultBooleanTrueValue() {
        return "1";
    }

    @Override
    protected String getDefaultBooleanFalseValue() {
        return "0";
    }

    @Override
    protected String getDefaultUuidValue() {
        return "newid()";
    }
}
