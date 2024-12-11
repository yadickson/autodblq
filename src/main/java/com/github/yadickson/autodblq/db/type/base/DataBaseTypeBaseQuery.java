/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.type.base;

import java.util.List;

import com.github.yadickson.autodblq.db.support.Support;

/**
 *
 * @author Yadickson Soto
 */
public interface DataBaseTypeBaseQuery extends Support {

    String get(List<String> filter);

}
