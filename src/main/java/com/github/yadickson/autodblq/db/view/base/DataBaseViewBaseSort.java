/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.view.base;

import java.util.Comparator;
import java.util.List;

import com.github.yadickson.autodblq.db.view.base.model.ViewBase;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseViewBaseSort implements Comparator<ViewBase> {

    private final List<String> filter;

    public DataBaseViewBaseSort(final List<String> filter) {
        this.filter = filter;
    }

    @Override
    public int compare(ViewBase a, ViewBase b) {
        return filter.indexOf(a.getName()) - filter.indexOf(b.getName());
    }

}
