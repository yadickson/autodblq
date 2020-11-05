/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table;

import java.util.ArrayList;
import java.util.List;

import com.github.yadickson.autodblq.db.DataBaseGeneratorType;
import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;

/**
 *
 * @author Yadickson Soto
 */
public abstract class DataBaseTableReader {

    private final DataBaseGeneratorType type;

    public DataBaseTableReader(DataBaseGeneratorType type) {
        this.type = type;
    }

    public DataBaseGeneratorType getType() {
        return type;
    }

    public List<TableBase> execute(final DriverConnection driverConnection, final List<TableBase> tables) {

        try {

            return processTables(driverConnection, tables);

        } catch (RuntimeException ex) {
            throw new DataBaseTableReaderException(ex);
        }

    }

    private List<TableBase> processTables(
            final DriverConnection driverConnection,
            final List<TableBase> tables
    ) {
        List<TableBase> tableElements = new ArrayList<>();

        for (TableBase table : tables) {
            List<TableBase> tableBases = processTable(driverConnection, table);
            tableElements.addAll(tableBases);
        }

        return tableElements;
    }

    protected List<TableBase> processTable(
            final DriverConnection driverConnection,
            final TableBase table
    ) {
        final String sqlQuery = findSqlQuery(driverConnection, table);
        return findElements(driverConnection, table, sqlQuery);
    }

    protected abstract String findSqlQuery(final DriverConnection driverConnection, final TableBase table);

    protected abstract List<TableBase> findElements(final DriverConnection driverConnection, final TableBase table, final String sqlQuery);

}
