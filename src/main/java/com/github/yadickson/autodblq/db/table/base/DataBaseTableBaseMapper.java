/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.base;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.base.model.TableBaseBean;
import com.github.yadickson.autodblq.logger.LoggerManager;
import com.github.yadickson.autodblq.util.StringToLowerCaseUtil;
import com.github.yadickson.autodblq.util.StringToSnakeCaseUtil;
import com.github.yadickson.autodblq.util.StringTrimUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableBaseMapper implements Function<List<TableBaseBean>, List<TableBase>> {

    private final LoggerManager loggerManager;
    private final StringToLowerCaseUtil stringToLowerCaseUtil;
    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringTrimUtil stringTrimUtil;

    @Inject
    public DataBaseTableBaseMapper(LoggerManager loggerManager, final StringToLowerCaseUtil stringToLowerCaseUtil, final StringToSnakeCaseUtil stringToSnakeCaseUtil, final StringTrimUtil stringTrimUtil) {
        this.loggerManager = loggerManager;
        this.stringToLowerCaseUtil = stringToLowerCaseUtil;
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringTrimUtil = stringTrimUtil;
    }

    @Override
    public List<TableBase> apply(final List<TableBaseBean> tableBeans) {
        final List<TableBase> tables = new ArrayList<>();

        for (TableBaseBean tableBean : tableBeans) {
            TableBase table = processTable(tableBean);
            tables.add(table);
        }

        return tables;
    }

    private TableBase processTable(final TableBaseBean tableBean) {
        final String realSchema = stringTrimUtil.apply(tableBean.getSchema());
        final String realName = stringTrimUtil.apply(tableBean.getName());
        final String schema = stringToLowerCaseUtil.apply(tableBean.getSchema());
        final String name = stringToLowerCaseUtil.apply(tableBean.getName());
        final String remarks = stringTrimUtil.apply(tableBean.getRemarks());
        final String newSchema = stringToSnakeCaseUtil.apply(tableBean.getSchema());
        final String newName = stringToSnakeCaseUtil.apply(tableBean.getName());

        loggerManager.debug("[DataBaseTableBaseMapper] Schema: " + realSchema);
        loggerManager.debug("[DataBaseTableBaseMapper] Name: " + realName);
        loggerManager.debug("[DataBaseTableBaseMapper] Remarks: " + remarks);
        
        return new TableBase(realSchema, realName, schema, name, remarks, newSchema, newName);
    }

}
