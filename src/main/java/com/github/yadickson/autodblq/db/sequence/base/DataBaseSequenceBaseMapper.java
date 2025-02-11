/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.sequence.base;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.db.sequence.base.model.SequenceBase;
import com.github.yadickson.autodblq.db.sequence.base.model.SequenceBaseBean;
import com.github.yadickson.autodblq.logger.LoggerManager;
import com.github.yadickson.autodblq.util.*;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseSequenceBaseMapper implements Function<List<SequenceBaseBean>, List<SequenceBase>> {

    private final LoggerManager loggerManager;
    private final StringToLowerCaseUtil stringToLowerCaseUtil;
    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringTrimUtil stringTrimUtil;
    private final StringToIntegerUtil stringToIntegerUtil;
    private final StringToBooleanUtil stringToBooleanUtil;

    @Inject
    public DataBaseSequenceBaseMapper(LoggerManager loggerManager, final StringToLowerCaseUtil stringToLowerCaseUtil, final StringToSnakeCaseUtil stringToSnakeCaseUtil, final StringTrimUtil stringTrimUtil, final StringToIntegerUtil stringToIntegerUtil, StringToBooleanUtil stringToBooleanUtil) {
        this.loggerManager = loggerManager;
        this.stringToLowerCaseUtil = stringToLowerCaseUtil;
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringTrimUtil = stringTrimUtil;
        this.stringToIntegerUtil = stringToIntegerUtil;
        this.stringToBooleanUtil = stringToBooleanUtil;
    }

    @Override
    public List<SequenceBase> apply(final List<SequenceBaseBean> sequenceBeans) {
        final List<SequenceBase> sequences = new ArrayList<>();

        for (SequenceBaseBean sequenceBean : sequenceBeans) {
            SequenceBase sequence = processType(sequenceBean);
            sequences.add(sequence);
        }

        return sequences;
    }

    private SequenceBase processType(final SequenceBaseBean sequenceBean) {
        final String realSchema = stringTrimUtil.apply(sequenceBean.getSchema());
        final String realName = stringTrimUtil.apply(sequenceBean.getName());
        final String schema = stringToLowerCaseUtil.apply(sequenceBean.getSchema());
        final String name = stringToLowerCaseUtil.apply(sequenceBean.getName());
        final String newSchema = stringToSnakeCaseUtil.apply(sequenceBean.getSchema());
        final String newName = stringToSnakeCaseUtil.apply(sequenceBean.getName());
        final String type = stringToLowerCaseUtil.apply(sequenceBean.getType());
        final Boolean cycle = stringToBooleanUtil.apply(sequenceBean.getCycle());
        final Integer startValue = stringToIntegerUtil.apply(sequenceBean.getStartvalue());
        final Integer incrementBy = stringToIntegerUtil.apply(sequenceBean.getIncrementby());

        loggerManager.debug("[DataBaseTypeBaseMapper] Schema: " + realSchema);
        loggerManager.debug("[DataBaseTypeBaseMapper] Name: " + realName);
        loggerManager.debug("[DataBaseTypeBaseMapper] Start Value: " + startValue);
        loggerManager.debug("[DataBaseTypeBaseMapper] Increment By: " + incrementBy);

        return new SequenceBase(realSchema, realName, schema, name, newSchema, newName, type, cycle, startValue, incrementBy);
    }

}
