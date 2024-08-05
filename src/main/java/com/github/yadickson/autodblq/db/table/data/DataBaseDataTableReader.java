/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.data;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.Parameters;
import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.property.DataTablePropertyManager;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseDataTableReader {

    private final DataTablePropertyManager dataTablePropertyManager;
    private final DataBaseDataTableBlockQueryFactory dataBaseDataTableBlockQueryFactory;

    @Inject
    public DataBaseDataTableReader(DataTablePropertyManager dataTablePropertyManager, DataBaseDataTableBlockQueryFactory dataBaseDataTableBlockQueryFactory) {
        this.dataTablePropertyManager = dataTablePropertyManager;
        this.dataBaseDataTableBlockQueryFactory = dataBaseDataTableBlockQueryFactory;
    }

    public DataBaseDataTableReaderIterator execute(final Parameters parameters, final DriverConnection driverConnection, final TableBase table) {
        return new DataBaseDataTableReaderIterator(dataTablePropertyManager, dataBaseDataTableBlockQueryFactory, parameters, driverConnection, table);
    }

}
