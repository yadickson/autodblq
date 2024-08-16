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
import com.github.yadickson.autodblq.util.*;
import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.table.columns.model.Column;
import com.github.yadickson.autodblq.db.table.columns.model.TableColumnBean;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableColumnsMapper implements Function<List<TableColumnBean>, List<DataBaseProperty>> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableColumnsMapper.class);

    private final StringTrimUtil stringTrimUtil;
    private final StringToLowerCaseUtil stringToLowerCaseUtil;
    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringToBooleanUtil stringToBooleanUtil;
    private final StringToIntegerUtil stringToIntegerUtil;

    @Inject
    public DataBaseTableColumnsMapper(
            final StringTrimUtil stringTrimUtil,
            final StringToLowerCaseUtil stringToLowerCaseUtil,
            final StringToSnakeCaseUtil stringToSnakeCaseUtil,
            final StringToBooleanUtil stringToBooleanUtil,
            final StringToIntegerUtil stringToIntegerUtil
    ) {
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

        LOGGER.debug("[DataBaseTableColumnMapper] Name: " + realName);
        LOGGER.debug("[DataBaseTableColumnMapper] Type: " + type);
        LOGGER.debug("[DataBaseTableColumnMapper] Position: " + position);
        LOGGER.debug("[DataBaseTableColumnMapper] Length: " + length);
        LOGGER.debug("[DataBaseTableColumnMapper] Precision: " + precision);
        LOGGER.debug("[DataBaseTableColumnMapper] Scale: " + scale);
        LOGGER.debug("[DataBaseTableColumnMapper] Remarks: " + remarks);
        LOGGER.debug("[DataBaseTableColumnMapper] Nullable: " + nullable);

        return new Column(realName, name, newName, type, position, length, precision, scale, remarks, nullable, identity, startWith, incrementBy);
    }

}
