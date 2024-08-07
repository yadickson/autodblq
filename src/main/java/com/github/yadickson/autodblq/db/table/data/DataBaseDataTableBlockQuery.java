/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.data;

import com.github.yadickson.autodblq.db.table.columns.DataBaseTableColumnsWrapper;

/**
 *
 * @author Yadickson Soto
 */
public interface DataBaseDataTableBlockQuery {

    String get(final DataBaseTableColumnsWrapper table, final Long page, final Long blocks);

}
