/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;

/**
 *
 * @author Yadickson Soto
 */
public abstract class DataBaseTableConstraintMapper<T> implements BiFunction<TableBase, List<T>, List<TableBase>> {

    @Override
    public List<TableBase> apply(final TableBase tableBase, final List<T> tableBeans) {

        final List<TableBase> tables = new ArrayList<>();

        for (T tableBean : tableBeans) {
            TableBase result = mapper(tableBase, tableBean);
            tables.add(result);
        }

        return tables;
    }

    protected abstract TableBase mapper(TableBase tableBase, T tableBean);
}
