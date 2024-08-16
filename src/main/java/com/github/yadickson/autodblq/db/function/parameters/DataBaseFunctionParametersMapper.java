/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.function.parameters;

import com.github.yadickson.autodblq.db.function.parameters.model.FunctionParameter;
import com.github.yadickson.autodblq.db.function.parameters.model.FunctionParameterBean;
import com.github.yadickson.autodblq.db.property.DataBaseProperty;
import com.github.yadickson.autodblq.util.*;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseFunctionParametersMapper implements Function<List<FunctionParameterBean>, List<DataBaseProperty>> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseFunctionParametersMapper.class);

    private final StringTrimUtil stringTrimUtil;
    private final StringToLowerCaseUtil stringToLowerCaseUtil;
    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringToIntegerUtil stringToIntegerUtil;

    @Inject
    public DataBaseFunctionParametersMapper(
            final StringTrimUtil stringTrimUtil,
            final StringToLowerCaseUtil stringToLowerCaseUtil,
            final StringToSnakeCaseUtil stringToSnakeCaseUtil,
            final StringToIntegerUtil stringToIntegerUtil
    ) {
        this.stringTrimUtil = stringTrimUtil;
        this.stringToLowerCaseUtil = stringToLowerCaseUtil;
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringToIntegerUtil = stringToIntegerUtil;
    }

    @Override
    public List<DataBaseProperty> apply(List<FunctionParameterBean> functionParameterBeans) {

        final List<FunctionParameter> functionParameters = new ArrayList<>();

        for (FunctionParameterBean functionParameterBean : functionParameterBeans) {
            FunctionParameter functionParameter = processFunctionParameter(functionParameterBean);
            functionParameters.add(functionParameter);
        }

        return functionParameters
                .stream()
                .sorted(Comparator.comparing(FunctionParameter::getPosition))
                .collect(Collectors.toList());
    }

    private FunctionParameter processFunctionParameter(FunctionParameterBean functionParameterBean) {
        final String realName = stringTrimUtil.apply(functionParameterBean.getName());
        final String name = stringToLowerCaseUtil.apply(functionParameterBean.getName());
        final String newName = stringToSnakeCaseUtil.apply(functionParameterBean.getName());
        final String type = stringToLowerCaseUtil.apply(functionParameterBean.getType());
        final Integer position = stringToIntegerUtil.apply(functionParameterBean.getPosition());
        final Integer length = stringToIntegerUtil.apply(functionParameterBean.getLength());
        final String precision = stringTrimUtil.apply(functionParameterBean.getPrecision());
        final String scale = stringTrimUtil.apply(functionParameterBean.getScale());
        final String defaultValue = stringTrimUtil.apply(functionParameterBean.getDefaultvalue());
        final String mode = stringToLowerCaseUtil.apply(functionParameterBean.getMode());

        LOGGER.debug("[DataBaseFunctionParametersMapper] Name: " + realName);
        LOGGER.debug("[DataBaseFunctionParametersMapper] Type: " + type);
        LOGGER.debug("[DataBaseFunctionParametersMapper] Position: " + position);
        LOGGER.debug("[DataBaseFunctionParametersMapper] Length: " + length);
        LOGGER.debug("[DataBaseFunctionParametersMapper] Precision: " + precision);
        LOGGER.debug("[DataBaseFunctionParametersMapper] Scale: " + scale);
        LOGGER.debug("[DataBaseFunctionParametersMapper] DefaultValue: " + defaultValue);

        return new FunctionParameter(realName, name, newName, type, position, length, precision, scale, defaultValue, mode);
    }

}
