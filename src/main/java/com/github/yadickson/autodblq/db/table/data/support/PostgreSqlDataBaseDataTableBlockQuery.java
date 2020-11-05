/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.data.support;

import com.github.yadickson.autodblq.db.table.data.DataBaseDataTableBlockQuery;
import com.github.yadickson.autodblq.db.table.definitions.model.TableDefinitionWrapper;

/**
 *
 * @author Yadickson Soto
 */
public class PostgreSqlDataBaseDataTableBlockQuery implements DataBaseDataTableBlockQuery {

    @Override
    public String get(TableDefinitionWrapper table, Long page, Long blocks) {
        return null;
    }
    
}
