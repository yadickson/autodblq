/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.checks;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintMapper;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintsWrapper;
import com.github.yadickson.autodblq.db.table.constraint.checks.model.TableCheckBean;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableCheckMapper implements DataBaseTableConstraintMapper<TableCheckBean> {

    private final DataBaseTableCheckConstraintsMapper mapper;

    @Inject
    public DataBaseTableCheckMapper(DataBaseTableCheckConstraintsMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public TableBase apply(final TableBase tableBase, final List<TableCheckBean> constraints) {
        return new DataBaseTableConstraintsWrapper(tableBase, mapper.apply(constraints));
    }

}
