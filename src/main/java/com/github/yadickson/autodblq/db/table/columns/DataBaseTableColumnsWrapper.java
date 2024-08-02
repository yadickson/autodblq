/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.columns;

import java.util.List;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.columns.model.TableColumn;
import com.github.yadickson.autodblq.db.table.property.DataBaseTableProperty;

/**
 *
 * @author Yadickson Soto
 */
public final class DataBaseTableColumnsWrapper extends TableBase {

    private final List<DataBaseTableProperty> columns;

    public DataBaseTableColumnsWrapper(final TableBase table, final List<DataBaseTableProperty> columns) {
        super(table);
        this.columns = columns;
    }

    public List<DataBaseTableProperty> getColumns() {
        return columns;
    }

}
