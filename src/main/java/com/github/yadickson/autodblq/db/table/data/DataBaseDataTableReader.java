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
import com.github.yadickson.autodblq.util.StringToLowerCaseUtil;
import com.github.yadickson.autodblq.util.StringToSnakeCaseUtil;
import com.github.yadickson.autodblq.util.StringTrimUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseDataTableReader {

    private final DataBasePropertyManager dataBasePropertyManager;
    private final DataBaseDataTableBlockQueryFactory dataBaseDataTableBlockQueryFactory;
    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringToLowerCaseUtil stringToLowerCaseUtil;
    private final StringTrimUtil stringTrimUtil;

    @Inject
    public DataBaseDataTableReader(DataBasePropertyManager dataBasePropertyManager, DataBaseDataTableBlockQueryFactory dataBaseDataTableBlockQueryFactory, ParametersPlugin parametersPlugin, StringToSnakeCaseUtil stringToSnakeCaseUtil, StringToLowerCaseUtil stringToLowerCaseUtil, StringTrimUtil stringTrimUtil) {
        this.dataBasePropertyManager = dataBasePropertyManager;
        this.dataBaseDataTableBlockQueryFactory = dataBaseDataTableBlockQueryFactory;
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringToLowerCaseUtil = stringToLowerCaseUtil;
        this.stringTrimUtil = stringTrimUtil;
    }

    public DataBaseDataTableReaderIterator execute(final DriverConnection driverConnection, final TableBase table) {
        return new DataBaseDataTableReaderIterator(dataBasePropertyManager, dataBaseDataTableBlockQueryFactory, stringToSnakeCaseUtil, stringToLowerCaseUtil, stringTrimUtil, driverConnection, table);
    }

}
