/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.type.base.support;

import java.util.List;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.support.SupportType;
import com.github.yadickson.autodblq.db.type.base.DataBaseTypeBaseQuery;

/**
 *
 * @author Yadickson Soto
 */
public class OracleDataBaseTypeBaseQuery extends SupportType implements DataBaseTypeBaseQuery {

    public OracleDataBaseTypeBaseQuery() {
        super(Driver.ORACLE);
    }

    @Override
    public String get(final List<String> filter) {
        return null;
    }

}
