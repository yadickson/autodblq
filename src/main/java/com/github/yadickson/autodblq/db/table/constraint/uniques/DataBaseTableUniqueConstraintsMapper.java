/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.uniques;

import com.github.yadickson.autodblq.db.table.constraint.uniques.model.TableUniqueBean;
import com.github.yadickson.autodblq.db.property.DataBaseProperty;

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
public class DataBaseTableUniqueConstraintsMapper implements Function<List<TableUniqueBean>, List<DataBaseProperty>> {

    private final DataBaseTableUniqueConstraintMapper mapper;

    @Inject
    public DataBaseTableUniqueConstraintsMapper(final DataBaseTableUniqueConstraintMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<DataBaseProperty> apply(final List<TableUniqueBean> tables) {
        return tables.stream().map(mapper).collect(Collectors.toList());
    }

}
