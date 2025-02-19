/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.data.model;

import java.util.List;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;

/**
 *
 * @author Yadickson Soto
 */
public final class DataBaseTableDataWrapper extends TableBase {

    private final List<String> files;

    public DataBaseTableDataWrapper(final TableBase table, final List<String> files) {
        super(table);
        this.files = files;
    }

    public List<String> getFiles() {
        return files;
    }


}
