/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.foreignkeys;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.property.DataBaseProperty;
import com.github.yadickson.autodblq.db.table.constraint.foreignkeys.model.TableForeignKeyBean;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableForeignKeyConstraintsMapper implements Function<List<TableForeignKeyBean>, List<DataBaseProperty>> {

    private final DataBaseTableForeignKeyConstraintMapper mapper;

    @Inject
    public DataBaseTableForeignKeyConstraintsMapper(final DataBaseTableForeignKeyConstraintMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<DataBaseProperty> apply(final List<TableForeignKeyBean> tables) {
        return tables.stream().map(mapper).collect(Collectors.toList());
    }

}
