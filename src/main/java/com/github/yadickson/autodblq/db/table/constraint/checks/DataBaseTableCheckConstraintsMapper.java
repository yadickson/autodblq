/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.checks;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.property.DataBaseProperty;
import com.github.yadickson.autodblq.db.table.constraint.checks.model.TableCheckBean;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableCheckConstraintsMapper implements Function<List<TableCheckBean>, List<DataBaseProperty>> {

    private final DataBaseTableCheckConstraintMapper mapper;

    @Inject
    public DataBaseTableCheckConstraintsMapper(final DataBaseTableCheckConstraintMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<DataBaseProperty> apply(final List<TableCheckBean> tables) {
        return tables.stream().map(mapper).collect(Collectors.toList());
    }

}
