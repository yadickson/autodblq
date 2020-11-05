/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.function.base.support;

import java.util.List;

import com.github.yadickson.autodblq.db.function.base.DataBaseFunctionBaseQuery;

/**
 *
 * @author Yadickson Soto
 */
public class OracleDataBaseFunctionBaseQuery implements DataBaseFunctionBaseQuery {

    @Override
    public String getFunctions(final List<String> filter) {
        return null;
    }

    @Override
    public String getProcedures(final List<String> filter) {
        return null;
    }

}
