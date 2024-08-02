/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.property.support;

import com.github.yadickson.autodblq.db.connection.driver.Driver;
import com.github.yadickson.autodblq.db.support.SupportType;
import com.github.yadickson.autodblq.db.table.definitions.model.TableColumn;
import com.github.yadickson.autodblq.db.table.property.DataBaseTableProperties;
import com.github.yadickson.autodblq.db.table.property.model.TableColumnProperty;

/**
 *
 * @author Yadickson Soto
 */
public class OracleDataBaseTableProperties extends SupportType implements DataBaseTableProperties {

    public OracleDataBaseTableProperties() {
        super(Driver.ORACLE);
    }

    @Override
    public TableColumnProperty get(TableColumn column) {
        return null;
    }

}
