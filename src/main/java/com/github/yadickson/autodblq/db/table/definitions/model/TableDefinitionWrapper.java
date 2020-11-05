/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.definitions.model;

import java.util.List;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;

/**
 *
 * @author Yadickson Soto
 */
public final class TableDefinitionWrapper extends TableBase {

    private final List<TableColumn> columns;

    public TableDefinitionWrapper(final TableBase table, final List<TableColumn> columns) {
        super(table);
        this.columns = columns;
    }

    public List<TableColumn> getColumns() {
        return columns;
    }

}
