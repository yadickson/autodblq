/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.property.support;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.property.model.TablePropertyType;

/**
 *
 * @author Yadickson Soto
 */
public class Db2DataBaseProperties extends DataBasePropertiesBase {

    public Db2DataBaseProperties() {
        super(Driver.DB2);
    }

    @Override
    protected String getUuid() {
        return "varchar(16)";
    }

    @Override
    protected String getBoolean() {
        return "boolean";
    }

    @Override
    protected String getInteger() {
        return "integer";
    }

    @Override
    protected String getDatetime() {
        return "timestamp(2)";
    }

    @Override
    protected String getVarchar() {
        return "varchar";
    }

    @Override
    protected String getMaxString() {
        return "";
    }

    @Override
    protected String getDefaultBooleanTrueValue() {
        return "true";
    }

    @Override
    protected String getDefaultBooleanFalseValue() {
        return "false";
    }

    @Override
    protected String getDefaultUuidValue() {
        return "";
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
        return "";
    }

    @Override
    protected String getPostOutMode() {
        return "";
    }

    @Override
    protected String getPostInOutMode() {
        return "";
    }

    @Override
    protected String getPreValue() {
        return "";
    }
}
