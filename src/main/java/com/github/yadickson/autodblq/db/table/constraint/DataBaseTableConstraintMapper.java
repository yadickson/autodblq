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
public abstract class DataBaseTableConstraintMapper<T> implements BiFunction<TableBase, List<T>, TableBase> {

    public abstract TableBase apply(final TableBase tableBase, final List<T> tableBeans);
}
