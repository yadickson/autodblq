/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.base;

import com.github.yadickson.autodblq.db.table.base.model.TableBase;
import com.github.yadickson.autodblq.db.table.base.model.TableBaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.util.StringTrimUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseTableBaseMapper implements Function<List<TableBaseBean>, List<TableBase>> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableBaseMapper.class);

    private final StringTrimUtil stringTrimUtil;

    @Inject
    public DataBaseTableBaseMapper(final StringTrimUtil stringTrimUtil) {
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
        final String schema = stringTrimUtil.apply(tableBean.getSchema());
        final String name = stringTrimUtil.apply(tableBean.getName());
        final String remarks = stringTrimUtil.apply(tableBean.getRemarks());

        LOGGER.debug("[DataBaseTableBaseMapper] Schema: " + schema);
        LOGGER.debug("[DataBaseTableBaseMapper] Name: " + name);
        LOGGER.debug("[DataBaseTableBaseMapper] Remarks: " + remarks);
        
        return new TableBase(schema, name, remarks);
    }

}
