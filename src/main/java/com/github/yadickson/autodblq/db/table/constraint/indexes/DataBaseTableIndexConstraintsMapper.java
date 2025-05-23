/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.indexes;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.property.DataBaseProperty;
import com.github.yadickson.autodblq.db.table.constraint.indexes.model.TableIndexBean;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableIndexConstraintsMapper implements Function<List<TableIndexBean>, List<DataBaseProperty>> {

    private final DataBaseTableIndexConstraintMapper mapper;

    @Inject
    public DataBaseTableIndexConstraintsMapper(final DataBaseTableIndexConstraintMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<DataBaseProperty> apply(final List<TableIndexBean> tables) {
        return tables.stream().map(mapper).collect(Collectors.toList());
    }

}
