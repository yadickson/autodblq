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
public class OracleDataBaseVersionQuery implements DataBaseVersionQuery {

    @Override
    public String get() {
        return "SELECT banner version FROM sys.V_$VERSION WHERE banner LIKE 'Oracle Database%'";
    }

}
