package com.github.yadickson.autodblq.db.property;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.github.yadickson.autodblq.ParametersPlugin;
import com.github.yadickson.autodblq.db.function.parameters.DataBaseFunctionParametersWrapper;
import com.github.yadickson.autodblq.db.property.model.TablePropertyType;
import com.github.yadickson.autodblq.db.table.columns.DataBaseTableColumnsWrapper;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintsWrapper;

@Singleton
public final class DataBasePropertyManager implements Consumer<List<DataBaseProperty>> {

    private final Map<String, List<TablePropertyType>> properties = new TreeMap<>();

    private final DataBasePropertiesFactory factory;
    private final ParametersPlugin parametersPlugin;

    @Inject
    public DataBasePropertyManager(DataBasePropertiesFactory factory, ParametersPlugin parametersPlugin) {
        this.factory = factory;
        this.parametersPlugin = parametersPlugin;

        for (DataBaseProperties support : factory.get()) {
            properties.put(support.getType().getMessage(), new ArrayList<>());
        }
    }

    @Override
    public void accept(final List<DataBaseProperty> elements) {

        for(DataBaseProperty element: elements)
        {
            List<DataBaseProperty> properties;

            if (element instanceof DataBaseTableColumnsWrapper) {
                properties = ((DataBaseTableColumnsWrapper) element).getColumns();
            }
            else if (element instanceof DataBaseTableConstraintsWrapper) {
                properties = ((DataBaseTableConstraintsWrapper) element).getConstraints();
            }
            else if (element instanceof DataBaseFunctionParametersWrapper) {
                process(element);
                properties = ((DataBaseFunctionParametersWrapper) element).getParameters();
            } else {
                continue;
            }

            for (DataBaseProperty dataBaseProperty : properties) {
                process(dataBaseProperty);
            }
        }
    }

    public TablePropertyType process(DataBaseProperty dataBaseProperty) {
        TablePropertyType property = null;

        if(parametersPlugin.getKeepNames())
        {
            return null;
        }

        for (DataBaseProperties support : factory.get()) {
            property = support.get(dataBaseProperty);
            if (property != null) properties.get(support.getType().getMessage()).add(property);
        }

        return property;
    }

    public Map<String, List<TablePropertyType>> getProperties() {

        for (DataBaseProperties support : factory.get()) {

            Set<String> nameSet = new TreeSet<>();

            properties.put(support.getType().getMessage(), properties.get(support.getType().getMessage())
                    .stream()
                    .filter(e -> nameSet.add(e.getName()))
                    .sorted(Comparator.comparing(TablePropertyType::getName))
                    .collect(Collectors.toList()));
        }

        return properties;
    }

    public void addFunctionDefinitions() {
        for (DataBaseProperties support : factory.get()) {
            TablePropertyType initFunction = support.getInitFunction();
            TablePropertyType endFunction = support.getEndFunction();
            TablePropertyType preInModeFunction = support.getPreInModeFunction();
            TablePropertyType preOutModeFunction = support.getPreOutModeFunction();
            TablePropertyType preInOutModeFunction = support.getPreInOutModeFunction();
            TablePropertyType postInModeFunction = support.getPostInModeFunction();
            TablePropertyType postOutModeFunction = support.getPostOutModeFunction();
            TablePropertyType postInOutModeFunction = support.getPostInOutModeFunction();
            TablePropertyType preValueFunction = support.getPreValueFunction();
            if (initFunction != null) properties.get(support.getType().getMessage()).add(initFunction);
            if (endFunction != null) properties.get(support.getType().getMessage()).add(endFunction);
            if (preInModeFunction != null) properties.get(support.getType().getMessage()).add(preInModeFunction);
            if (preOutModeFunction != null) properties.get(support.getType().getMessage()).add(preOutModeFunction);
            if (preInOutModeFunction != null) properties.get(support.getType().getMessage()).add(preInOutModeFunction);
            if (postInModeFunction != null) properties.get(support.getType().getMessage()).add(postInModeFunction);
            if (postOutModeFunction != null) properties.get(support.getType().getMessage()).add(postOutModeFunction);
            if (postInOutModeFunction != null) properties.get(support.getType().getMessage()).add(postInOutModeFunction);
            if (preValueFunction != null) properties.get(support.getType().getMessage()).add(preValueFunction);
        }
    }
}
