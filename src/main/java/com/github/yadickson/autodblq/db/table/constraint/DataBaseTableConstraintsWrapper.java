/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.property.DataBaseTableProperty;

import java.util.List;

/**
 *
 * @author Yadickson Soto
 */
public final class DataBaseTableConstraintsWrapper extends TableBase {

    private final List<DataBaseTableProperty> constraints;

    public DataBaseTableConstraintsWrapper(final TableBase table, final List<DataBaseTableProperty> constraints) {
        super(table);
        this.constraints = constraints;
    }

    public List<DataBaseTableProperty> getConstraints() {
        return constraints;
    }

}
