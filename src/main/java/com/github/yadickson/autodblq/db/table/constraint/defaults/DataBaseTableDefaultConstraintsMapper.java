/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.defaults;

import com.github.yadickson.autodblq.db.table.constraint.defaults.model.TableDefaultBean;
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
public class DataBaseTableDefaultConstraintsMapper implements Function<List<TableDefaultBean>, List<DataBaseTableProperty>> {

    private final DataBaseTableDefaultConstraintMapper mapper;

    @Inject
    public DataBaseTableDefaultConstraintsMapper(final DataBaseTableDefaultConstraintMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<DataBaseTableProperty> apply(final List<TableDefaultBean> tables) {
        return tables.stream().map(mapper).collect(Collectors.toList());
    }

}
