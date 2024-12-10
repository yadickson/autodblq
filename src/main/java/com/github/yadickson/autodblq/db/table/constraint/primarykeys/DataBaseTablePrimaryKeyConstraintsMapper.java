/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.primarykeys;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.property.DataBaseProperty;
import com.github.yadickson.autodblq.db.table.constraint.primarykeys.model.TablePrimaryKeyBean;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTablePrimaryKeyConstraintsMapper implements Function<List<TablePrimaryKeyBean>, List<DataBaseProperty>> {

    private final DataBaseTablePrimaryKeyConstraintMapper mapper;

    @Inject
    public DataBaseTablePrimaryKeyConstraintsMapper(final DataBaseTablePrimaryKeyConstraintMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<DataBaseProperty> apply(final List<TablePrimaryKeyBean> tables) {
        return tables.stream().map(mapper).collect(Collectors.toList());
    }

}
