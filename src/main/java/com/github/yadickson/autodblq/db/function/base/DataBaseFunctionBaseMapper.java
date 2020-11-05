/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.function.base;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.function.base.model.FunctionBase;
import com.github.yadickson.autodblq.db.function.base.model.FunctionBaseBean;
import com.github.yadickson.autodblq.db.util.StringToBooleanUtil;
import com.github.yadickson.autodblq.db.util.StringTrimUtil;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DataBaseFunctionBaseMapper implements Function<List<FunctionBaseBean>, List<FunctionBase>> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseFunctionBaseMapper.class);

    private final StringTrimUtil stringTrimUtil;
    private final StringToBooleanUtil stringToBooleanUtil;

    @Inject
    public DataBaseFunctionBaseMapper(
            final StringTrimUtil stringTrimUtil,
            final StringToBooleanUtil stringToBooleanUtil
    ) {
        this.stringTrimUtil = stringTrimUtil;
        this.stringToBooleanUtil = stringToBooleanUtil;
    }

    @Override
    public List<FunctionBase> apply(final List<FunctionBaseBean> tableBeans) {
        final List<FunctionBase> tables = new ArrayList<>();

        for (FunctionBaseBean tableBean : tableBeans) {
            FunctionBase table = processFunction(tableBean);
            tables.add(table);
        }

        return tables;
    }

    private FunctionBase processFunction(final FunctionBaseBean functionBean) {
        final String schema = stringTrimUtil.apply(functionBean.getSchema());
        final String name = stringTrimUtil.apply(functionBean.getName());
        final String content = stringTrimUtil.apply(functionBean.getContent());
        final Boolean function = stringToBooleanUtil.apply(functionBean.getIsfunction());

        LOGGER.debug("[DataBaseFunctionBaseMapper] Schema: " + schema);
        LOGGER.debug("[DataBaseFunctionBaseMapper] Name: " + name);
        LOGGER.debug("[DataBaseFunctionBaseMapper] Content: " + content);
        LOGGER.debug("[DataBaseFunctionBaseMapper] Is Function: " + function);

        return new FunctionBase(schema, name, content, function);
    }

}
