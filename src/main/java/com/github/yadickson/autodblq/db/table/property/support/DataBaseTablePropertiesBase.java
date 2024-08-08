/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.property.support;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.support.SupportType;
import com.github.yadickson.autodblq.db.table.property.DataBaseTableProperties;
import com.github.yadickson.autodblq.db.table.property.DataBaseTableProperty;
import com.github.yadickson.autodblq.db.table.property.model.TablePropertyName;
import com.github.yadickson.autodblq.db.table.property.model.TablePropertyType;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Yadickson Soto
 */
public abstract class DataBaseTablePropertiesBase extends SupportType implements DataBaseTableProperties {

    public DataBaseTablePropertiesBase(final Driver type) {
        super(type);
    }

    @Override
    public TablePropertyType get(DataBaseTableProperty column) {
        String type = column.getType() == null ? "" : column.getType();
        String defaultType = column.getDefaultType() == null ? "" : column.getDefaultType();
        String defaultValue = column.getDefaultValue() == null ? "" : column.getDefaultValue();
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
            String size = column.getLength() > 0 ? "_" + column.getLength() : "";
            String maxSize = !size.isEmpty() ? "(" + column.getLength() + ")" : getMaxString();
            response = new TablePropertyType(TablePropertyName.STRING.getMessage() + size, getVarchar() + maxSize);
        }
        else if (isBoolean(defaultType))
        {
            boolean ok = StringUtils.containsIgnoreCase(defaultValue, "1") || StringUtils.containsIgnoreCase(defaultValue, "true");
            response = new TablePropertyType(
                    ok ? TablePropertyName.BOOLEAN_TRUE.getMessage() : TablePropertyName.BOOLEAN_FALSE.getMessage(),
                    ok ? getDefaultBooleanTrueValue() : getDefaultBooleanFalseValue());
        }
        else if (isUuid(defaultType) && (StringUtils.containsIgnoreCase(defaultValue, "newid") || StringUtils.containsIgnoreCase(defaultValue, "gen_random_uuid")))
        {
            response = new TablePropertyType(TablePropertyName.UUID_FUNCTION.getMessage(), getDefaultUuidValue());
        }
        else
        {
            return null;
        }

        column.setPropertyType(response.getName());

        return response;
    }

    private Boolean isUuid(String input) {
        return "uniqueidentifier".compareTo(input) == 0 || "uuid".compareTo(input) == 0;
    }

    private Boolean isBoolean(String input) {
        return "bit".compareTo(input) == 0 || "bool".compareTo(input) == 0;
    }

    private Boolean isInteger(String input) {
        return "int".compareTo(input) == 0 || "int4".compareTo(input) == 0;
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
}
