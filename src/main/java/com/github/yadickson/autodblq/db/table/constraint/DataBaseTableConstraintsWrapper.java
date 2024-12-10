/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint;

import java.util.List;

import com.github.yadickson.autodblq.db.property.DataBaseProperty;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;

/**
 *
 * @author Yadickson Soto
 */
public final class DataBaseTableConstraintsWrapper extends TableBase {

    private final List<DataBaseProperty> constraints;

    public DataBaseTableConstraintsWrapper(final TableBase table, final List<DataBaseProperty> constraints) {
        super(table);
        this.constraints = constraints;
    }

    public List<DataBaseProperty> getConstraints() {
        return constraints;
    }

}
