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
public class Db2DataBaseVersionQuery implements DataBaseVersionQuery {

    @Override
    public String get() {
        return "SELECT service_level AS \"version\" FROM SYSIBMADM.ENV_INST_INFO";
    }

}
