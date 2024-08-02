/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.definitions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.util.*;
import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.table.definitions.model.TableColumn;
import com.github.yadickson.autodblq.db.table.definitions.model.TableColumnBean;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableDefinitionMapper implements Function<List<TableColumnBean>, List<TableColumn>> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableDefinitionMapper.class);

    private final StringTrimUtil stringTrimUtil;
    private final StringToLowerCaseUtil stringToLowerCaseUtil;
    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringToBooleanUtil stringToBooleanUtil;
    private final StringToIntegerUtil stringToIntegerUtil;

    @Inject
    public DataBaseTableDefinitionMapper(
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
    public List<TableColumn> apply(List<TableColumnBean> tableColumnBeans) {

        final List<TableColumn> tableColumns = new ArrayList<>();

        for (TableColumnBean tableColumnBean : tableColumnBeans) {
            TableColumn tableColumn = processTableColumn(tableColumnBean);
            tableColumns.add(tableColumn);
        }

        return tableColumns
                .stream()
                .sorted(Comparator.comparing(TableColumn::getPosition))
                .collect(Collectors.toList());
    }

    private TableColumn processTableColumn(TableColumnBean tableColumnBean) {
        final String name = stringToSnakeCaseUtil.apply(tableColumnBean.getName());
        final String type = stringToLowerCaseUtil.apply(tableColumnBean.getType());
        final Integer position = stringToIntegerUtil.apply(tableColumnBean.getPosition());
        final Integer length = stringToIntegerUtil.apply(tableColumnBean.getLength());
        final String precision = stringTrimUtil.apply(tableColumnBean.getPrecision());
        final String scale = stringTrimUtil.apply(tableColumnBean.getScale());
        final Boolean nulleable = stringToBooleanUtil.apply(tableColumnBean.getNullable());
        final String remarks = stringTrimUtil.apply(tableColumnBean.getRemarks());
        final String defaultValue = stringTrimUtil.apply(tableColumnBean.getDefaultvalue());

        LOGGER.debug("[DataBaseTableColumnMapper] Name: " + name);
        LOGGER.debug("[DataBaseTableColumnMapper] Type: " + type);
        LOGGER.debug("[DataBaseTableColumnMapper] Position: " + position);
        LOGGER.debug("[DataBaseTableColumnMapper] Length: " + length);
        LOGGER.debug("[DataBaseTableColumnMapper] Precision: " + precision);
        LOGGER.debug("[DataBaseTableColumnMapper] Scale: " + scale);
        LOGGER.debug("[DataBaseTableColumnMapper] Nulleable: " + nulleable);
        LOGGER.debug("[DataBaseTableColumnMapper] Remarks: " + remarks);
        LOGGER.debug("[DataBaseTableColumnMapper] DefaultValue: " + defaultValue);

        return new TableColumn(name, type, position, length, precision, scale, nulleable, remarks, defaultValue);
    }

}
