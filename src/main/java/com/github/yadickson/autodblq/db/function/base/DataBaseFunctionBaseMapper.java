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

import com.github.yadickson.autodblq.util.*;
import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.function.base.model.FunctionBase;
import com.github.yadickson.autodblq.db.function.base.model.FunctionBaseBean;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseFunctionBaseMapper implements Function<List<FunctionBaseBean>, List<FunctionBase>> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseFunctionBaseMapper.class);

    private final StringToLowerCaseUtil stringToLowerCaseUtil;
    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringToContentUtil stringToContentUtil;
    private final StringToBooleanUtil stringToBooleanUtil;

    @Inject
    public DataBaseFunctionBaseMapper(
            StringToLowerCaseUtil stringToLowerCaseUtil, StringToSnakeCaseUtil stringToSnakeCaseUtil, final StringToContentUtil stringToContentUtil,
            final StringToBooleanUtil stringToBooleanUtil
    ) {
        this.stringToLowerCaseUtil = stringToLowerCaseUtil;
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringToContentUtil = stringToContentUtil;
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
        final String schema = stringToLowerCaseUtil.apply(functionBean.getSchema());
        final String name = stringToLowerCaseUtil.apply(functionBean.getName());
        final String content = stringToContentUtil.apply(functionBean.getContent());
        final Boolean isFunction = stringToBooleanUtil.apply(functionBean.getIsfunction());
        final String newSchema = stringToSnakeCaseUtil.apply(functionBean.getSchema());
        final String newName = stringToSnakeCaseUtil.apply(functionBean.getName());

        LOGGER.debug("[DataBaseFunctionBaseMapper] Schema: " + schema);
        LOGGER.debug("[DataBaseFunctionBaseMapper] Name: " + name);
        LOGGER.debug("[DataBaseFunctionBaseMapper] Content: " + content);
        LOGGER.debug("[DataBaseFunctionBaseMapper] Is Function: " + isFunction);

        return new FunctionBase(schema, name, content, isFunction, newSchema, newName);
    }

}
