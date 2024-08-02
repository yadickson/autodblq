/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.property.support;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.support.SupportType;
import com.github.yadickson.autodblq.db.table.definitions.model.TableColumn;
import com.github.yadickson.autodblq.db.table.property.DataBaseTableProperties;
import com.github.yadickson.autodblq.db.table.property.model.TableColumnProperty;
import com.github.yadickson.autodblq.db.table.property.model.TableColumnType;

/**
 *
 * @author Yadickson Soto
 */
public class PostgreSqlDataBaseTableProperties extends SupportType implements DataBaseTableProperties {

    public PostgreSqlDataBaseTableProperties() {
        super(Driver.POSTGRESQL);
    }

    @Override
    public TableColumnProperty get(TableColumn column) {
        String name = column.getType();
        String value = column.getType();

        if ("uniqueidentifier".compareTo(column.getType()) == 0)
        {
            name = TableColumnType.UUID.getMessage();
            value = "uuid";
        }
        else if ("bit".compareTo(column.getType()) == 0)
        {
            name = TableColumnType.BOOLEAN.getMessage();
            value = "boolean";
        }
        else if ("int".compareTo(column.getType()) == 0)
        {
            name = TableColumnType.INTEGER.getMessage();
            value = "integer";
        }
        else if (column.getType().contains("date"))
        {
            name = TableColumnType.DATETIME.getMessage();
            value = "timestamp(2)";
        }
        else if (column.getType().contains("char"))
        {
            String size = column.getLength() > 0 ? "_" + column.getLength() : "";
            String nsize = !size.isEmpty() ? "(" + column.getLength() + ")" : "";
            name = "string" + size;
            value = "varchar" + nsize;
        }
        else
        {
            return null;
        }

        column.setPropertyType(name);
        return new TableColumnProperty(name, value);
    }
}
