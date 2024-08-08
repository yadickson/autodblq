/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.base;

import java.util.Comparator;
import java.util.List;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseTableBaseSort implements Comparator<TableBase> {

    private final List<String> filter;

    public DataBaseTableBaseSort(final List<String> filter) {
        this.filter = filter;
    }

    @Override
    public int compare(TableBase a, TableBase b) {
        return filter.indexOf(a.getName()) - filter.indexOf(b.getName());
    }

}
