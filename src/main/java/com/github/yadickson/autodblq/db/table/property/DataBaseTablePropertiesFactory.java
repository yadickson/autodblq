/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.property;

import com.github.yadickson.autodblq.db.table.property.support.Db2DataBaseTableProperties;
import com.github.yadickson.autodblq.db.table.property.support.MsSqlDataBaseTableProperties;
import com.github.yadickson.autodblq.db.table.property.support.OracleDataBaseTableProperties;
import com.github.yadickson.autodblq.db.table.property.support.PostgreSqlDataBaseTableProperties;

import javax.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTablePropertiesFactory implements Supplier<List<DataBaseTableProperties>> {
    
    @Override
    public List<DataBaseTableProperties> get() {
        return Arrays.asList(
                new Db2DataBaseTableProperties(),
                new OracleDataBaseTableProperties(),
                new PostgreSqlDataBaseTableProperties(),
                new MsSqlDataBaseTableProperties()
        );
    }
}
