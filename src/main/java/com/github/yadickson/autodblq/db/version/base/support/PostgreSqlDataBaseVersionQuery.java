/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.version.base.support;

import com.github.yadickson.autodblq.db.version.base.DataBaseVersionQuery;

/**
 *
 * @author Yadickson Soto
 */
public class PostgreSqlDataBaseVersionQuery implements DataBaseVersionQuery {

    @Override
    public String get() {
        return "SELECT 'PostgreSQL v' || current_setting('server_version') as \"version\"";
    }

}
