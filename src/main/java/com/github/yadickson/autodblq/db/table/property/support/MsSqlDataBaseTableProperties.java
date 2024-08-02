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
import com.github.yadickson.autodblq.db.table.property.model.TablePropertyType;
import com.github.yadickson.autodblq.db.table.property.model.TablePropertyName;

/**
 *
 * @author Yadickson Soto
 */
public class MsSqlDataBaseTableProperties extends SupportType implements DataBaseTableProperties {

    public MsSqlDataBaseTableProperties() {
        super(Driver.MSSQL);
    }

    @Override
    public TablePropertyType get(DataBaseTableProperty column) {
        String type = column.getType();
        String value;

        if (type == null)
        {
            return null;
        }
        else if ("uniqueidentifier".compareTo(type) == 0)
        {
            type = TablePropertyName.UUID.getMessage();
            value = "uuid";
        }
        else if (type.contains("newid"))
        {
            type = TablePropertyName.UUID_FUNCTION.getMessage();
            value = "newid()";
        }
        else if ("bit".compareTo(type) == 0)
        {
            type = TablePropertyName.BOOLEAN.getMessage();
            value = "boolean";
        }
        else if ("int".compareTo(type) == 0)
        {
            type = TablePropertyName.INTEGER.getMessage();
            value = "int";
        }
        else if (type.contains("date"))
        {
            type = TablePropertyName.DATETIME.getMessage();
            value = "datetimeoffset";
        }
        else if (type.contains("char"))
        {
            String size = column.getLength() > 0 ? "_" + column.getLength() : "";
            String nsize = !size.isEmpty() ? "(" + column.getLength() + ")" : "(max)";
            type = "string" + size;
            value = "varchar" + nsize;
        }
        else
        {
            return null;
        }

        column.setPropertyType(type);

        return new TablePropertyType(type, value);
    }
}
