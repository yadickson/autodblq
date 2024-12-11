/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.type.base;

import java.util.Comparator;
import java.util.List;

import com.github.yadickson.autodblq.db.type.base.model.TypeBase;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseTypeBaseSort implements Comparator<TypeBase> {

    private final List<String> filter;

    public DataBaseTypeBaseSort(final List<String> filter) {
        this.filter = filter;
    }

    @Override
    public int compare(TypeBase a, TypeBase b) {
        return filter.indexOf(a.getName()) - filter.indexOf(b.getName());
    }

}
