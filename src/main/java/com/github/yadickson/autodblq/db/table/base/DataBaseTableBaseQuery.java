/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.base;

import java.util.List;

/**
 *
 * @author Yadickson Soto
 */
public interface DataBaseTableBaseQuery {

    String get(List<String> filter);

}
