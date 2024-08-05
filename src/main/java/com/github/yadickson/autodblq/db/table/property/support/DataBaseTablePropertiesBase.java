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

        if ("uniqueidentifier".compareTo(type) == 0 || "uuid".compareTo(type) == 0)
        {
            response = new TablePropertyType(TablePropertyName.UUID.getMessage(), getUuid());
        }
        else if ("bit".compareTo(type) == 0 || "bool".compareTo(type) == 0)
        {
            response = new TablePropertyType(TablePropertyName.BOOLEAN.getMessage(), getBoolean());
        }
        else if ("int".compareTo(type) == 0 || "int4".compareTo(type) == 0)
        {
            response = new TablePropertyType(TablePropertyName.INTEGER.getMessage(), getInteger());
        }
        else if (type.contains("date") || type.contains("timestamp"))
        {
            response = new TablePropertyType(TablePropertyName.DATETIME.getMessage(), getDatetime());
        }
        else if (type.contains("char"))
        {
            String size = column.getLength() > 0 ? "_" + column.getLength() : "";
            String maxSize = !size.isEmpty() ? "(" + column.getLength() + ")" : getMaxString();
            response = new TablePropertyType(TablePropertyName.STRING.getMessage() + size, getVarchar() + maxSize);
        }
        else if ("bit".compareTo(defaultType) == 0 || "bool".compareTo(defaultType) == 0)
        {
            boolean ok = "1".compareTo(defaultValue) == 0 || "true".compareTo(defaultValue) == 0;
            return new TablePropertyType(
                    ok ? TablePropertyName.BOOLEAN_TRUE.getMessage() : TablePropertyName.BOOLEAN_FALSE.getMessage(),
                    ok ? getDefaultBooleanTrueValue() : getDefaultBooleanFalseValue());
        }
        else if (defaultValue.contains("newid") || defaultValue.contains("gen_random_uuid"))
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
