/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.defaults;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintMapper;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintsWrapper;
import com.github.yadickson.autodblq.db.table.constraint.defaults.model.TableDefaultBean;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableDefaultMapper implements DataBaseTableConstraintMapper<TableDefaultBean> {

    private final DataBaseTableDefaultConstraintsMapper mapper;

    @Inject
    public DataBaseTableDefaultMapper(DataBaseTableDefaultConstraintsMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public TableBase apply(final TableBase tableBase, final List<TableDefaultBean> constraints) {
        return new DataBaseTableConstraintsWrapper(tableBase, mapper.apply(constraints));
    }

}
