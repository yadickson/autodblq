/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.base;

import com.github.yadickson.autodblq.db.support.Support;

import java.util.List;

/**
 *
 * @author Yadickson Soto
 */
public interface DataBaseTableBaseQuery extends Support {

    String get(List<String> filter);

}
