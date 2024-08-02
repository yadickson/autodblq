package com.github.yadickson.autodblq.db.table.definitions.support;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.definitions.DataBaseTableDefinitionQuery;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Yadickson Soto
 */
public class MsSqlDataBaseTableDefinitionQuery implements DataBaseTableDefinitionQuery {

    @Override
    public String get(final TableBase table) {
        return "SELECT \n"
                + " c.column_id as 'position', \n"
                + " c.name, \n"
                + " ct.name as 'type', \n"
                + " c.max_length as 'length', \n"
                + " case when c.is_nullable = 0 then 'false' else 'true' end as 'nullable', \n"
                + " c.precision, \n"
                + " c.scale, \n"
                + " cd.value as 'remarks' \n"
                + "FROM sys.tables t \n"
                + "inner join sys.columns c on c.object_id = t.object_id \n"
                + "inner join sys.types ct ON ct.user_type_id = c.user_type_id \n"
                + "left join sys.extended_properties cd on cd.major_id = c.object_id and cd.name = 'MS_Description' and cd.minor_id = c.column_id \n"
                + "WHERE \n"
                + filterByName(table)
                + filterBySchema(table)
                + "ORDER BY c.column_id";
    }

    private String filterByName(final TableBase table) {
        return " t.name = '" + table.getName() + "' \n";
    }

    private String filterBySchema(final TableBase table) {

        if (StringUtils.isEmpty(table.getSchema())) {
            return StringUtils.EMPTY;
        }

        return " AND t.schema_id = schema_id('" + table.getSchema() + "') \n";
    }

}
