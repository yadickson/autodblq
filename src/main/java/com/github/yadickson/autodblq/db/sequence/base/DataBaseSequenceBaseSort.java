/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.sequence.base;

import java.util.Comparator;
import java.util.List;

import com.github.yadickson.autodblq.db.sequence.base.model.SequenceBase;

/**
 *
 * @author Yadickson Soto
 */
public class DataBaseSequenceBaseSort implements Comparator<SequenceBase> {

    private final List<String> filter;

    public DataBaseSequenceBaseSort(final List<String> filter) {
        this.filter = filter;
    }

    @Override
    public int compare(SequenceBase a, SequenceBase b) {
        return filter.indexOf(a.getName()) - filter.indexOf(b.getName());
    }

}
