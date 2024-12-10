/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.foreignkeys;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintMapper;
import com.github.yadickson.autodblq.db.table.constraint.DataBaseTableConstraintsWrapper;
import com.github.yadickson.autodblq.db.table.constraint.foreignkeys.model.TableForeignKeyBean;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableForeignKeyMapper implements DataBaseTableConstraintMapper<TableForeignKeyBean> {

    private final DataBaseTableForeignKeyConstraintsMapper mapper;

    @Inject
    public DataBaseTableForeignKeyMapper(
            final DataBaseTableForeignKeyConstraintsMapper mapper
    ) {
        this.mapper = mapper;
    }

    @Override
    public TableBase apply(final TableBase tableBase, final List<TableForeignKeyBean> constraints) {
        return new DataBaseTableConstraintsWrapper(tableBase, mapper.apply(constraints));
    }

}
