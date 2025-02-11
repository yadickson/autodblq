/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.sequence.base.support;

import java.util.List;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.sequence.base.DataBaseSequenceBaseQuery;
import com.github.yadickson.autodblq.db.support.SupportType;

/**
 *
 * @author Yadickson Soto
 */
public class Db2DataBaseSequenceBaseQuery extends SupportType implements DataBaseSequenceBaseQuery {

    public Db2DataBaseSequenceBaseQuery() {
        super(Driver.DB2);
    }

    @Override
    public String get(final List<String> filter) {
        return null;
    }
}
