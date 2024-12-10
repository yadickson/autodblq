/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.property.support;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.property.DataBaseProperties;
import com.github.yadickson.autodblq.db.property.DataBaseProperty;
import com.github.yadickson.autodblq.db.property.model.TablePropertyName;
import com.github.yadickson.autodblq.db.property.model.TablePropertyType;
import com.github.yadickson.autodblq.db.support.SupportType;

/**
 *
 * @author Yadickson Soto
 */
public abstract class DataBasePropertiesBase extends SupportType implements DataBaseProperties {

    public DataBasePropertiesBase(final Driver type) {
        super(type);
    }

    @Override
    public TablePropertyType get(DataBaseProperty element) {
        String type = element.getType() == null ? "" : element.getType();
        String defaultType = element.getDefaultType() == null ? "" : element.getDefaultType();
        String defaultValue = element.getDefaultValue() == null ? "" : element.getDefaultValue();
        TablePropertyType response;

        if (isUuid(type))
        {
            response = new TablePropertyType(TablePropertyName.UUID.getMessage(), getUuid());
        }
        else if (isBoolean(type))
        {
            response = new TablePropertyType(TablePropertyName.BOOLEAN.getMessage(), getBoolean());
        }
        else if (isInteger(type))
        {
            response = new TablePropertyType(TablePropertyName.INTEGER.getMessage(), getInteger());
        }
        else if (StringUtils.containsIgnoreCase(type, "date") || StringUtils.containsIgnoreCase(type, "timestamp"))
        {
            response = new TablePropertyType(TablePropertyName.DATETIME.getMessage(), getDatetime());
        }
        else if (StringUtils.containsIgnoreCase(type, "char"))
        {
            String size = element.getLength() > 0 ? "_" + element.getLength() : "";
            String maxSize = !size.isEmpty() ? "(" + element.getLength() + ")" : getMaxString();
            response = new TablePropertyType(TablePropertyName.STRING.getMessage() + size, getVarchar() + maxSize);
        }
        else if (isBoolean(defaultType))
        {
            boolean ok = StringUtils.containsIgnoreCase(defaultValue, "1") || StringUtils.containsIgnoreCase(defaultValue, "true");
            response = new TablePropertyType(
                    ok ? TablePropertyName.BOOLEAN_TRUE.getMessage() : TablePropertyName.BOOLEAN_FALSE.getMessage(),
                    ok ? getDefaultBooleanTrueValue() : getDefaultBooleanFalseValue());
        }
        else if (isUuid(defaultType))
        {
            if (defaultValue != null)
                element.setDefaultValue(defaultValue.toUpperCase(Locale.US));

            if (StringUtils.containsIgnoreCase(defaultValue, "newid") || StringUtils.containsIgnoreCase(defaultValue, "gen_random_uuid"))
                response = new TablePropertyType(TablePropertyName.UUID_FUNCTION.getMessage(), getDefaultUuidValue());
            else
                return null;
        }
        else
        {
            return null;
        }

        element.setPropertyType(response.getName());

        return response;
    }

    @Override
    public TablePropertyType getInitFunction() {
        return new TablePropertyType(TablePropertyName.INIT_FUNCTION.getMessage(), getInitFunctionValue());
    }

    @Override
    public TablePropertyType getEndFunction() {
        return new TablePropertyType(TablePropertyName.END_FUNCTION.getMessage(), getEndFunctionValue());
    }

    @Override
    public TablePropertyType getPreInModeFunction() {
        return new TablePropertyType(TablePropertyName.PRE_IN_MODE.getMessage(), getPreInMode());
    }

    @Override
    public TablePropertyType getPreOutModeFunction() {
        return new TablePropertyType(TablePropertyName.PRE_OUT_MODE.getMessage(), getPreOutMode());
    }

    @Override
    public TablePropertyType getPreInOutModeFunction() {
        return new TablePropertyType(TablePropertyName.PRE_IN_OUT_MODE.getMessage(), getPreInOutMode());
    }

    @Override
    public TablePropertyType getPostInModeFunction() {
        return new TablePropertyType(TablePropertyName.POST_IN_MODE.getMessage(), getPostInMode());
    }

    @Override
    public TablePropertyType getPostOutModeFunction() {
        return new TablePropertyType(TablePropertyName.POST_OUT_MODE.getMessage(), getPostOutMode());
    }

    @Override
    public TablePropertyType getPostInOutModeFunction() {
        return new TablePropertyType(TablePropertyName.POST_IN_OUT_MODE.getMessage(), getPostInOutMode());
    }

    @Override
    public TablePropertyType getPreValueFunction() {
        return new TablePropertyType(TablePropertyName.PRE_VALUE.getMessage(), getPreValue());
    }

    private Boolean isUuid(String input) {
        return "uniqueidentifier".compareTo(input) == 0 || "uuid".compareTo(input) == 0;
    }

    private Boolean isBoolean(String input) {
        return "bit".compareTo(input) == 0 || "bool".compareTo(input) == 0;
    }

    private Boolean isInteger(String input) {
        return "int".compareTo(input) == 0 || "int4".compareTo(input) == 0 || "integer".compareTo(input) == 0;
    }

    protected abstract String getUuid();
    protected abstract String getBoolean();
    protected abstract String getInteger();
    protected abstract String getDatetime();
    protected abstract String getVarchar();
    protected abstract String getMaxString();
    protected abstract String getDefaultBooleanTrueValue();
    protected abstract String getDefaultBooleanFalseValue();
    protected abstract String getDefaultUuidValue();
    protected abstract String getInitFunctionValue();
    protected abstract String getEndFunctionValue();
    protected abstract String getPreInMode();
    protected abstract String getPreOutMode();
    protected abstract String getPreInOutMode();
    protected abstract String getPostInMode();
    protected abstract String getPostOutMode();
    protected abstract String getPostInOutMode();
    protected abstract String getPreValue();
}
