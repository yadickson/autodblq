/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.indexes.model;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;

/**
 *
 * @author Yadickson Soto
 */
public final class TableIndexWrapper extends TableBase {

    private final String indexName;
    private final String columns;
    private final Boolean isUnique;

    public TableIndexWrapper(final TableBase table, final String indexName, final String columns, final Boolean isUnique) {
        super(table);
        this.indexName = indexName;
        this.columns = columns;
        this.isUnique = isUnique;
    }

    public String getIndexName() {
        return indexName;
    }

    public String getColumns() {
        return columns;
    }

    public Boolean getIsUnique() {
        return isUnique;
    }

}
