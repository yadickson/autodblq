/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.function.base;

import java.util.Comparator;
import java.util.List;

import com.github.yadickson.autodblq.db.function.base.model.FunctionBase;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseFunctionBaseSort implements Comparator<FunctionBase> {

    private final List<String> filter;

    public DataBaseFunctionBaseSort(final List<String> filter) {
        this.filter = filter;
    }

    @Override
    public int compare(FunctionBase a, FunctionBase b) {
        return filter.indexOf(a.getName()) - filter.indexOf(b.getName());
    }

}
