/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.defaults;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.property.DataBaseProperty;
import com.github.yadickson.autodblq.db.table.constraint.defaults.model.TableDefaultBean;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableDefaultConstraintsMapper implements Function<List<TableDefaultBean>, List<DataBaseProperty>> {

    private final DataBaseTableDefaultConstraintMapper mapper;

    @Inject
    public DataBaseTableDefaultConstraintsMapper(final DataBaseTableDefaultConstraintMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<DataBaseProperty> apply(final List<TableDefaultBean> tables) {
        return tables.stream().map(mapper).collect(Collectors.toList());
    }

}
