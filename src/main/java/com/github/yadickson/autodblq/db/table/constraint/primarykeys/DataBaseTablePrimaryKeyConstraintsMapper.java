/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.primarykeys;

import com.github.yadickson.autodblq.db.table.constraint.primarykeys.model.TablePrimaryKeyBean;
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
public class DataBaseTablePrimaryKeyConstraintsMapper implements Function<List<TablePrimaryKeyBean>, List<DataBaseTableProperty>> {

    private final DataBaseTablePrimaryKeyConstraintMapper mapper;

    @Inject
    public DataBaseTablePrimaryKeyConstraintsMapper(final DataBaseTablePrimaryKeyConstraintMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<DataBaseTableProperty> apply(final List<TablePrimaryKeyBean> tables) {
        return tables.stream().map(mapper).collect(Collectors.toList());
    }

}
