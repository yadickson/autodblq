/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.github.yadickson.autodblq.db.DataBaseGeneratorType;
import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.defaults.DataBaseTableDefaultReader;
import com.github.yadickson.autodblq.db.table.foreignkeys.DataBaseTableForeignKeyReader;
import com.github.yadickson.autodblq.db.table.increments.DataBaseTableIncrementReader;
import com.github.yadickson.autodblq.db.table.indexes.DataBaseTableIndexReader;
import com.github.yadickson.autodblq.db.table.primarykeys.DataBaseTablePrimaryKeyReader;
import com.github.yadickson.autodblq.db.table.uniques.DataBaseTableUniqueReader;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableChain {

    private final List<DataBaseTableReader> handlers = new ArrayList<>();

    @Inject
    public DataBaseTableChain(
            final DataBaseTablePrimaryKeyReader dataBaseTablePrimaryKeyReader,
            final DataBaseTableForeignKeyReader dataBaseTableForeignKeyReader,
            final DataBaseTableUniqueReader dataBaseTableUniqueReader,
            final DataBaseTableIndexReader dataBaseTableIndexReader,
            final DataBaseTableDefaultReader dataBaseTableDefaultReader,
            final DataBaseTableIncrementReader dataBaseTableIncrementReader
    ) {
        this.handlers.add(dataBaseTablePrimaryKeyReader);
        this.handlers.add(dataBaseTableForeignKeyReader);
        this.handlers.add(dataBaseTableUniqueReader);
        this.handlers.add(dataBaseTableIndexReader);
        this.handlers.add(dataBaseTableDefaultReader);
        this.handlers.add(dataBaseTableIncrementReader);
    }

    public Map<DataBaseGeneratorType, List<TableBase>> execute(final DriverConnection driverConnection, final List<TableBase> tables) {

        try {

            return processHandlers(driverConnection, tables);

        } catch (RuntimeException ex) {
            throw new DataBaseTableChainException(ex);
        }

    }

    private Map<DataBaseGeneratorType, List<TableBase>> processHandlers(final DriverConnection driverConnection, final List<TableBase> tables) {

        Map<DataBaseGeneratorType, List<TableBase>> result = new HashMap<>();

        for (DataBaseTableReader reader : handlers) {
            DataBaseGeneratorType key = reader.getType();
            List<TableBase> response = reader.execute(driverConnection, tables);
            result.put(key, response);
        }

        return result;
    }

}
