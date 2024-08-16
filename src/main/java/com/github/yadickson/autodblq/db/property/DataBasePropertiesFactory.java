/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.property;

import com.github.yadickson.autodblq.db.property.support.Db2DataBaseProperties;
import com.github.yadickson.autodblq.db.property.support.MsSqlDataBaseProperties;
import com.github.yadickson.autodblq.db.property.support.OracleDataBaseProperties;
import com.github.yadickson.autodblq.db.property.support.PostgreSqlDataBaseProperties;

import javax.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBasePropertiesFactory implements Supplier<List<DataBaseProperties>> {
    
    @Override
    public List<DataBaseProperties> get() {
        return Arrays.asList(
                new Db2DataBaseProperties(),
                new OracleDataBaseProperties(),
                new PostgreSqlDataBaseProperties(),
                new MsSqlDataBaseProperties()
        );
    }
}
