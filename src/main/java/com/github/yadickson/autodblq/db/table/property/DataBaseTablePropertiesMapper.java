/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.property;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.definitions.model.TableColumn;
import com.github.yadickson.autodblq.db.table.definitions.model.TableDefinitionWrapper;
import com.github.yadickson.autodblq.db.table.property.model.TableColumnProperty;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTablePropertiesMapper implements Function<List<TableBase>, Map<String, List<TableColumnProperty>>> {

    private final DataBaseTablePropertiesFactory factory;

    @Inject
    public DataBaseTablePropertiesMapper(
            final DataBaseTablePropertiesFactory factory
    ) {
        this.factory = factory;
    }

    @Override
    public Map<String, List<TableColumnProperty>> apply(List<TableBase> tables) {

        List<DataBaseTableProperties> supports = factory.get();
        Map<String, List<TableColumnProperty>> response = new TreeMap<>();

        for (DataBaseTableProperties support : supports) {
            Driver type = support.getType();

            List<TableColumnProperty> all = new ArrayList<>();

            for(TableBase tableBase: tables)
            {
                for (TableColumn column : ((TableDefinitionWrapper)tableBase).getColumns()) {
                    TableColumnProperty property = support.get(column);
                    if (property != null) all.add(property);
                }
            }

            Set<String> nameSet = new TreeSet<>();

            response.put(type.getMessage(), all.stream()
                    .filter(e -> nameSet.add(e.getName()))
                    .sorted(Comparator.comparing(TableColumnProperty::getName))
                    .collect(Collectors.toList()));
        }

        return response;
    }

}
