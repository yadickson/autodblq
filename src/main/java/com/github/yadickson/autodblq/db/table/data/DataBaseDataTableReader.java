/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.data;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.ParametersPlugin;
import com.github.yadickson.autodblq.db.connection.DriverConnection;
import com.github.yadickson.autodblq.db.property.DataBasePropertyManager;
import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.logger.LoggerManager;
import com.github.yadickson.autodblq.util.StringToLongUtil;
import com.github.yadickson.autodblq.util.StringToLowerCaseUtil;
import com.github.yadickson.autodblq.util.StringToSnakeCaseUtil;
import com.github.yadickson.autodblq.util.StringTrimUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseDataTableReader {

    private final LoggerManager loggerManager;
    private final DataBasePropertyManager dataBasePropertyManager;
    private final DataBaseDataTableBlockQueryFactory dataBaseDataTableBlockQueryFactory;
    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringToLongUtil stringToLongUtil;
    private final StringToLowerCaseUtil stringToLowerCaseUtil;
    private final StringTrimUtil stringTrimUtil;
    private final DataBaseDataTableColumnValue columnValue;
    private final ParametersPlugin parametersPlugin;

    @Inject
    public DataBaseDataTableReader(LoggerManager loggerManager, DataBasePropertyManager dataBasePropertyManager, DataBaseDataTableBlockQueryFactory dataBaseDataTableBlockQueryFactory, ParametersPlugin parametersPlugin, StringToSnakeCaseUtil stringToSnakeCaseUtil, StringToLongUtil stringToLongUtil, StringToLowerCaseUtil stringToLowerCaseUtil, StringTrimUtil stringTrimUtil, DataBaseDataTableColumnValue columnValue, ParametersPlugin parametersPlugin1) {
        this.loggerManager = loggerManager;
        this.dataBasePropertyManager = dataBasePropertyManager;
        this.dataBaseDataTableBlockQueryFactory = dataBaseDataTableBlockQueryFactory;
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringToLongUtil = stringToLongUtil;
        this.stringToLowerCaseUtil = stringToLowerCaseUtil;
        this.stringTrimUtil = stringTrimUtil;
        this.columnValue = columnValue;
        this.parametersPlugin = parametersPlugin1;
    }

    public DataBaseDataTableReaderIterator execute(final DriverConnection driverConnection, final TableBase table) {
        return new DataBaseDataTableReaderIterator(loggerManager, dataBasePropertyManager, dataBaseDataTableBlockQueryFactory, stringToSnakeCaseUtil, stringToLowerCaseUtil, stringToLongUtil, stringTrimUtil, driverConnection, table, columnValue, parametersPlugin);
    }

}
