/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.indexes;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintsWrapper;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintMapper;
import com.github.yadickson.autodblq.db.table.constraint.indexes.model.TableIndexBean;

import java.util.List;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableIndexMapper implements DataBaseTableConstraintMapper<TableIndexBean> {

    private final DataBaseTableIndexConstraintsMapper mapper;

    @Inject
    public DataBaseTableIndexMapper(
            final DataBaseTableIndexConstraintsMapper mapper
    ) {
        this.mapper = mapper;
    }

    @Override
    public TableBase apply(final TableBase tableBase, final List<TableIndexBean> constraints) {
        return new DataBaseTableConstraintsWrapper(tableBase, mapper.apply(constraints));
    }

}
