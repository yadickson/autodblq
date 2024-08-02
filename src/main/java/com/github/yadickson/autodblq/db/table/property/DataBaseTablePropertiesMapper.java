/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.property;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintsWrapper;
import com.github.yadickson.autodblq.db.table.columns.DataBaseTableColumnsWrapper;
import com.github.yadickson.autodblq.db.table.property.model.TablePropertyType;

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
public class DataBaseTablePropertiesMapper implements Function<List<TableBase>, Map<String, List<TablePropertyType>>> {

    private final DataBaseTablePropertiesFactory factory;

    @Inject
    public DataBaseTablePropertiesMapper(
            final DataBaseTablePropertiesFactory factory
    ) {
        this.factory = factory;
    }

    @Override
    public Map<String, List<TablePropertyType>> apply(List<TableBase> tables) {

        List<DataBaseTableProperties> supports = factory.get();
        Map<String, List<TablePropertyType>> response = new TreeMap<>();

        for (DataBaseTableProperties support : supports) {
            Driver type = support.getType();

            List<TablePropertyType> all = new ArrayList<>();

            for(TableBase tableBase: tables)
            {
                List<DataBaseTableProperty> properties;

                if (tableBase instanceof DataBaseTableColumnsWrapper) {
                    properties = ((DataBaseTableColumnsWrapper) tableBase).getColumns();
                }
                else if (tableBase instanceof DataBaseTableConstraintsWrapper) {
                    properties = ((DataBaseTableConstraintsWrapper) tableBase).getConstraints();
                } else {
                    continue;
                }

                for (DataBaseTableProperty dataBaseTableProperty : properties) {
                    TablePropertyType property = support.get(dataBaseTableProperty);
                    if (property != null) all.add(property);
                }
            }

            Set<String> nameSet = new TreeSet<>();

            response.put(type.getMessage(), all.stream()
                    .filter(e -> nameSet.add(e.getName()))
                    .sorted(Comparator.comparing(TablePropertyType::getName))
                    .collect(Collectors.toList()));
        }

        return response;
    }

}
