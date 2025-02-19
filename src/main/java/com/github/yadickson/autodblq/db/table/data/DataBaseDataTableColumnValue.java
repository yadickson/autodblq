/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Named;

import org.apache.commons.lang.StringUtils;

import com.github.yadickson.autodblq.db.type.base.model.TypeBase;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseDataTableColumnValue {

    public String execute(final List<TypeBase> types, final String columnType, final ResultSet resultSet, final Integer index) throws SQLException {
        String result = null;

        boolean isEnum = ( types.stream().anyMatch( (type) -> type.getName().equalsIgnoreCase(columnType)));
        boolean isBoolean = StringUtils.containsIgnoreCase(columnType, "bool");
        boolean isDatetime = StringUtils.containsIgnoreCase(columnType, "time") || StringUtils.containsIgnoreCase(columnType, "date");
        boolean isString = StringUtils.containsIgnoreCase(columnType, "string") || StringUtils.containsIgnoreCase(columnType, "json") || StringUtils.containsIgnoreCase(columnType, "text") || StringUtils.containsIgnoreCase(columnType, "char");

        if (isEnum) {
            result = formatEnum(resultSet.getString(index), columnType);
        }
        else if (isBoolean) {
            result = formatBoolean(resultSet.getBoolean(index));
        }
        else if (isDatetime) {
            result = formatDate(resultSet.getString(index));
        }
        else if (isString) {
            result = formatString(resultSet.getString(index));
        } else {
            result = resultSet.getString(index);
        }

        return result;
    }

    private String formatEnum (final String value, final String columnType) {
        return value != null ? String.format("'%s'::%s", value, columnType) : null;
    }

    private String formatBoolean (final Boolean value) {
        return value != null ? Boolean.toString(value) : null;
    }

    private String formatDate (final String value) {
        return value != null ? String.format("'%s'", value) : null;
    }

    private String formatString (final String value) {
        return value != null ? String.format("'%s'", value.replaceAll("'", "''")) : null;
    }


}
