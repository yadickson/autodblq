/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.property.support;

import com.github.yadickson.autodblq.db.connection.driver.Driver;

/**
 *
 * @author Yadickson Soto
 */
public class MsSqlDataBaseProperties extends DataBasePropertiesBase {

    public MsSqlDataBaseProperties() {
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

    @Override
    protected String getInitFunctionValue() {
        return "";
    }

    @Override
    protected String getEndFunctionValue() {
        return "";
    }

    @Override
    protected String getPreInMode() {
        return "";
    }

    @Override
    protected String getPreOutMode() {
        return "";
    }

    @Override
    protected String getPreInOutMode() {
        return "";
    }

    @Override
    protected String getPostInMode() {
        return " in";
    }

    @Override
    protected String getPostOutMode() {
        return " out";
    }

    @Override
    protected String getPostInOutMode() {
        return " inout";
    }

    @Override
    protected String getPreValue() {
        return "@";
    }
}
