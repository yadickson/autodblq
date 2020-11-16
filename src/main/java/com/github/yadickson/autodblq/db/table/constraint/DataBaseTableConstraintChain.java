/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.DataBaseGeneratorType;
import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.constraint.defaults.DataBaseTableDefaultReader;
import com.github.yadickson.autodblq.db.table.constraint.foreignkeys.DataBaseTableForeignKeyReader;
import com.github.yadickson.autodblq.db.table.constraint.increments.DataBaseTableIncrementReader;
import com.github.yadickson.autodblq.db.table.constraint.indexes.DataBaseTableIndexReader;
import com.github.yadickson.autodblq.db.table.constraint.primarykeys.DataBaseTablePrimaryKeyReader;
import com.github.yadickson.autodblq.db.table.constraint.uniques.DataBaseTableUniqueReader;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableConstraintChain {

    private final List<DataBaseTableConstraintReader> handlers = new ArrayList<>();

    @Inject
    public DataBaseTableConstraintChain(
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
            throw new DataBaseTableConstraintChainException(ex);
        }

    }

    private Map<DataBaseGeneratorType, List<TableBase>> processHandlers(final DriverConnection driverConnection, final List<TableBase> tables) {

        Map<DataBaseGeneratorType, List<TableBase>> result = new HashMap<>();

        for (DataBaseTableConstraintReader reader : handlers) {
            Pair<DataBaseGeneratorType, List<TableBase>> response = reader.execute(driverConnection, tables);
            result.put(response.getKey(), response.getValue());
        }

        return result;
    }

}
