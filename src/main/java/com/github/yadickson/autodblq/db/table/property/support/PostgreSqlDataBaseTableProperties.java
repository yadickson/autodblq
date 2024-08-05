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
public class PostgreSqlDataBaseTableProperties extends DataBaseTablePropertiesBase {

    public PostgreSqlDataBaseTableProperties() {
        super(Driver.POSTGRESQL);
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
        return "gen_random_uuid()";
    }

}
