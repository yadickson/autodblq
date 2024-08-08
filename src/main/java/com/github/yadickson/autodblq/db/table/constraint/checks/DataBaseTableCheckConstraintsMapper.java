/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.checks;

import com.github.yadickson.autodblq.db.table.constraint.checks.model.TableCheckBean;
import com.github.yadickson.autodblq.db.table.property.DataBaseTableProperty;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableCheckConstraintsMapper implements Function<List<TableCheckBean>, List<DataBaseTableProperty>> {

    private final DataBaseTableCheckConstraintMapper mapper;

    @Inject
    public DataBaseTableCheckConstraintsMapper(final DataBaseTableCheckConstraintMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<DataBaseTableProperty> apply(final List<TableCheckBean> tables) {
        return tables.stream().map(mapper).collect(Collectors.toList());
    }

}
