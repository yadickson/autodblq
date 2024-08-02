/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.property;

import com.github.yadickson.autodblq.db.support.Support;
import com.github.yadickson.autodblq.db.table.definitions.model.TableColumn;
import com.github.yadickson.autodblq.db.table.property.model.TableColumnProperty;

/**
 *
 * @author Yadickson Soto
 */
public interface DataBaseTableProperties extends Support {
    TableColumnProperty get(TableColumn column);
}
