/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.definitions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.table.definitions.model.TableColumn;
import com.github.yadickson.autodblq.db.table.definitions.model.TableColumnBean;
import com.github.yadickson.autodblq.db.util.StringToBooleanUtil;
import com.github.yadickson.autodblq.db.util.StringToUpperCaseUtil;
import com.github.yadickson.autodblq.db.util.StringTrimUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableDefinitionMapper implements Function<List<TableColumnBean>, List<TableColumn>> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableDefinitionMapper.class);

    private final StringTrimUtil stringTrimUtil;
    private final StringToUpperCaseUtil stringToUpperCaseUtil;
    private final StringToBooleanUtil stringToBooleanUtil;

    @Inject
    public DataBaseTableDefinitionMapper(
            final StringTrimUtil stringTrimUtil,
            final StringToUpperCaseUtil stringToUpperCaseUtil,
            final StringToBooleanUtil stringToBooleanUtil
    ) {
        this.stringTrimUtil = stringTrimUtil;
        this.stringToUpperCaseUtil = stringToUpperCaseUtil;
        this.stringToBooleanUtil = stringToBooleanUtil;
    }

    @Override
    public List<TableColumn> apply(List<TableColumnBean> tableColumnBeans) {

        final List<TableColumn> tableColumns = new ArrayList<>();

        for (TableColumnBean tableColumnBean : tableColumnBeans) {
            TableColumn tableColumn = processTableColumn(tableColumnBean);
            tableColumns.add(tableColumn);
        }

        return tableColumns;
    }

    private TableColumn processTableColumn(TableColumnBean tableColumnBean) {
        final String name = stringTrimUtil.apply(tableColumnBean.getName());
        final String type = stringToUpperCaseUtil.apply(tableColumnBean.getType());
        final String position = stringTrimUtil.apply(tableColumnBean.getPosition());
        final String length = stringTrimUtil.apply(tableColumnBean.getLength());
        final String scale = stringTrimUtil.apply(tableColumnBean.getScale());
        final Boolean nulleable = stringToBooleanUtil.apply(tableColumnBean.getNullable());
        final String remarks = stringTrimUtil.apply(tableColumnBean.getRemarks());

        LOGGER.debug("[DataBaseTableColumnMapper] Name: " + name);
        LOGGER.debug("[DataBaseTableColumnMapper] Type: " + type);
        LOGGER.debug("[DataBaseTableColumnMapper] Position: " + position);
        LOGGER.debug("[DataBaseTableColumnMapper] Length: " + length);
        LOGGER.debug("[DataBaseTableColumnMapper] Scale: " + scale);
        LOGGER.debug("[DataBaseTableColumnMapper] Nulleable: " + nulleable);
        LOGGER.debug("[DataBaseTableColumnMapper] Remarks: " + remarks);

        return new TableColumn(name, type, position, length, scale, nulleable, remarks);
    }

}
