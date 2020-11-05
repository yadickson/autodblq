/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.data;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.github.yadickson.autodblq.Parameters;
import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseDataTableReader {

    private final DataBaseDataTableBlockQueryFactory dataBaseDataTableBlockQueryFactory;

    @Inject
    public DataBaseDataTableReader(DataBaseDataTableBlockQueryFactory dataBaseDataTableBlockQueryFactory) {
        this.dataBaseDataTableBlockQueryFactory = dataBaseDataTableBlockQueryFactory;
    }

    public DataBaseDataTableReaderIterator execute(final Parameters parameters, final DriverConnection driverConnection, final TableBase table) {
        return new DataBaseDataTableReaderIterator(dataBaseDataTableBlockQueryFactory, parameters, driverConnection, table);
    }

}
