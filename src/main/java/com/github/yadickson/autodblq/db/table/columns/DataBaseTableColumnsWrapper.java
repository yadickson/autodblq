/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.columns;

import java.util.List;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.property.DataBaseProperty;

/**
 *
 * @author Yadickson Soto
 */
public final class DataBaseTableColumnsWrapper extends TableBase {

    private final List<DataBaseProperty> columns;

    public DataBaseTableColumnsWrapper(final TableBase table, final List<DataBaseProperty> columns) {
        super(table);
        this.columns = columns;
    }

    public List<DataBaseProperty> getColumns() {
        return columns;
    }

}
