package com.github.yadickson.autodblq.db.table.property;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.columns.DataBaseTableColumnsWrapper;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintsWrapper;
import com.github.yadickson.autodblq.db.table.property.model.TablePropertyType;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Singleton
public final class DataTablePropertyManager implements Consumer<List<TableBase>> {

    private final Map<String, List<TablePropertyType>> properties = new TreeMap<>();

    private final DataBaseTablePropertiesFactory factory;

    @Inject
    public DataTablePropertyManager(DataBaseTablePropertiesFactory factory) {
        this.factory = factory;

        for (DataBaseTableProperties support : factory.get()) {
            properties.put(support.getType().getMessage(), new ArrayList<>());
        }
    }

    @Override
    public void accept(final List<TableBase> tables) {

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
                process(dataBaseTableProperty);
            }
        }
    }

    public TablePropertyType process(DataBaseTableProperty dataBaseTableProperty) {
        TablePropertyType property = null;

        for (DataBaseTableProperties support : factory.get()) {
            property = support.get(dataBaseTableProperty);
            if (property != null) properties.get(support.getType().getMessage()).add(property);
        }

        return property;
    }

    public Map<String, List<TablePropertyType>> getProperties() {

        for (DataBaseTableProperties support : factory.get()) {

            Set<String> nameSet = new TreeSet<>();

            properties.put(support.getType().getMessage(), properties.get(support.getType().getMessage())
                    .stream()
                    .filter(e -> nameSet.add(e.getName()))
                    .sorted(Comparator.comparing(TablePropertyType::getName))
                    .collect(Collectors.toList()));
        }

        return properties;
    }
}
