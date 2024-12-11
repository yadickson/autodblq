/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.columns;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.property.DataBaseProperty;
import com.github.yadickson.autodblq.db.table.columns.model.Column;
import com.github.yadickson.autodblq.db.table.columns.model.TableColumnBean;
import com.github.yadickson.autodblq.logger.LoggerManager;
import com.github.yadickson.autodblq.util.*;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableColumnsMapper implements Function<List<TableColumnBean>, List<DataBaseProperty>> {

    private final LoggerManager loggerManager;
    private final StringTrimUtil stringTrimUtil;
    private final StringToLowerCaseUtil stringToLowerCaseUtil;
    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringToBooleanUtil stringToBooleanUtil;
    private final StringToIntegerUtil stringToIntegerUtil;

    @Inject
    public DataBaseTableColumnsMapper(
            LoggerManager loggerManager, final StringTrimUtil stringTrimUtil,
            final StringToLowerCaseUtil stringToLowerCaseUtil,
            final StringToSnakeCaseUtil stringToSnakeCaseUtil,
            final StringToBooleanUtil stringToBooleanUtil,
            final StringToIntegerUtil stringToIntegerUtil
    ) {
        this.loggerManager = loggerManager;
        this.stringTrimUtil = stringTrimUtil;
        this.stringToLowerCaseUtil = stringToLowerCaseUtil;
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringToBooleanUtil = stringToBooleanUtil;
        this.stringToIntegerUtil = stringToIntegerUtil;
    }

    @Override
    public List<DataBaseProperty> apply(List<TableColumnBean> tableColumnBeans) {

        final List<Column> tableColumns = new ArrayList<>();

        for (TableColumnBean tableColumnBean : tableColumnBeans) {
            Column tableColumn = processTableColumn(tableColumnBean);
            tableColumns.add(tableColumn);
        }

        return tableColumns
                .stream()
                .sorted(Comparator.comparing(Column::getPosition))
                .collect(Collectors.toList());
    }

    private Column processTableColumn(TableColumnBean tableColumnBean) {
        final String realName = stringTrimUtil.apply(tableColumnBean.getName());
        final String name = stringToLowerCaseUtil.apply(tableColumnBean.getName());
        final String newName = stringToSnakeCaseUtil.apply(tableColumnBean.getName());
        final String type = stringToLowerCaseUtil.apply(tableColumnBean.getType());
        final Integer position = stringToIntegerUtil.apply(tableColumnBean.getPosition());
        final Integer length = stringToIntegerUtil.apply(tableColumnBean.getLength());
        final String precision = stringTrimUtil.apply(tableColumnBean.getPrecision());
        final String scale = stringTrimUtil.apply(tableColumnBean.getScale());
        final Boolean nullable = stringToBooleanUtil.apply(tableColumnBean.getNullable());
        final String remarks = stringTrimUtil.apply(tableColumnBean.getRemarks());
        final Boolean identity = stringToBooleanUtil.apply(tableColumnBean.getIdentity());
        final Integer startWith = stringToIntegerUtil.apply(tableColumnBean.getStartwith());
        final Integer incrementBy = stringToIntegerUtil.apply(tableColumnBean.getIncrementby());

        loggerManager.debug("[DataBaseTableColumnMapper] Name: " + realName);
        loggerManager.debug("[DataBaseTableColumnMapper] Type: " + type);
        loggerManager.debug("[DataBaseTableColumnMapper] Position: " + position);
        loggerManager.debug("[DataBaseTableColumnMapper] Length: " + length);
        loggerManager.debug("[DataBaseTableColumnMapper] Precision: " + precision);
        loggerManager.debug("[DataBaseTableColumnMapper] Scale: " + scale);
        loggerManager.debug("[DataBaseTableColumnMapper] Remarks: " + remarks);
        loggerManager.debug("[DataBaseTableColumnMapper] Nullable: " + nullable);

        return new Column(realName, name, newName, type, position, length, precision, scale, remarks, nullable, identity, startWith, incrementBy);
    }

}
