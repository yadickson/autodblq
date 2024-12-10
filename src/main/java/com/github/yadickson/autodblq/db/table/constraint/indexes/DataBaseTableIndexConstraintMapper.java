/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.indexes;

import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.db.property.DataBaseProperty;
import com.github.yadickson.autodblq.db.table.constraint.indexes.model.Index;
import com.github.yadickson.autodblq.db.table.constraint.indexes.model.TableIndexBean;
import com.github.yadickson.autodblq.util.*;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableIndexConstraintMapper implements Function<TableIndexBean, DataBaseProperty> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableIndexConstraintMapper.class);

    private final StringToLowerCaseUtil stringToLowerCaseUtil;
    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringJoinUtil stringJoinUtil;
    private final StringToBooleanUtil stringToBooleanUtil;
    private final StringTrimUtil stringTrimUtil;

    @Inject
    public DataBaseTableIndexConstraintMapper(StringToLowerCaseUtil stringToLowerCaseUtil, final StringToSnakeCaseUtil stringToSnakeCaseUtil, final StringJoinUtil stringJoinUtil, final StringToBooleanUtil stringToBooleanUtil, StringTrimUtil stringTrimUtil) {
        this.stringToLowerCaseUtil = stringToLowerCaseUtil;
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringJoinUtil = stringJoinUtil;
        this.stringToBooleanUtil = stringToBooleanUtil;
        this.stringTrimUtil = stringTrimUtil;
    }

    @Override
    public DataBaseProperty apply(final TableIndexBean tableBean) {
        final String constraintRealName = stringTrimUtil.apply(tableBean.getName());
        final String constraintName = stringToLowerCaseUtil.apply(tableBean.getName());
        final String constraintNewName = stringToSnakeCaseUtil.apply(tableBean.getName());
        final String constraintRealColumns = stringJoinUtil.apply(stringTrimUtil.apply(tableBean.getColumns()));
        final String constraintColumns = stringJoinUtil.apply(stringToSnakeCaseUtil.apply(tableBean.getColumns()));
        final Boolean constraintIsUnique = stringToBooleanUtil.apply(tableBean.getIsunique());

        LOGGER.debug("[DataBaseTableDefaultMapper] Index Constraint Name: " + constraintRealName);
        LOGGER.debug("[DataBaseTableDefaultMapper] Index Constraint Columns: " + constraintRealColumns);
        LOGGER.debug("[DataBaseTableDefaultMapper] Index Constraint IsUnique: " + constraintIsUnique);

        return new Index(constraintRealName, constraintName, constraintNewName, constraintRealColumns, constraintColumns, constraintIsUnique);
    }
}
