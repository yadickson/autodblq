/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.primarykeys;

import com.github.yadickson.autodblq.db.table.constraint.primarykeys.model.PrimaryKey;
import com.github.yadickson.autodblq.db.table.constraint.primarykeys.model.TablePrimaryKeyBean;
import com.github.yadickson.autodblq.db.property.DataBaseProperty;
import com.github.yadickson.autodblq.util.StringJoinUtil;
import com.github.yadickson.autodblq.util.StringToLowerCaseUtil;
import com.github.yadickson.autodblq.util.StringToSnakeCaseUtil;
import com.github.yadickson.autodblq.util.StringTrimUtil;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.function.Function;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTablePrimaryKeyConstraintMapper implements Function<TablePrimaryKeyBean, DataBaseProperty> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTablePrimaryKeyConstraintMapper.class);

    private final StringToLowerCaseUtil stringToLowerCaseUtil;
    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringJoinUtil stringJoinUtil;
    private final StringTrimUtil stringTrimUtil;

    @Inject
    public DataBaseTablePrimaryKeyConstraintMapper(StringToLowerCaseUtil stringToLowerCaseUtil, final StringToSnakeCaseUtil stringToSnakeCaseUtil, final StringJoinUtil stringJoinUtil, StringTrimUtil stringTrimUtil) {
        this.stringToLowerCaseUtil = stringToLowerCaseUtil;
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringJoinUtil = stringJoinUtil;
        this.stringTrimUtil = stringTrimUtil;
    }

    @Override
    public DataBaseProperty apply(final TablePrimaryKeyBean tableBean) {
        final String constraintRealName = stringTrimUtil.apply(tableBean.getName());
        final String constraintName = stringToLowerCaseUtil.apply(tableBean.getName());
        final String constraintNewName = stringToSnakeCaseUtil.apply(tableBean.getName());
        final String constraintRealColumns = stringJoinUtil.apply(stringTrimUtil.apply(tableBean.getColumns()));
        final String constraintColumns = stringJoinUtil.apply(stringToSnakeCaseUtil.apply(tableBean.getColumns()));

        LOGGER.debug("[DataBaseTableDefaultMapper] PrimaryKey Constraint Name: " + constraintRealName);
        LOGGER.debug("[DataBaseTableDefaultMapper] PrimaryKey Constraint Columns: " + constraintRealColumns);

        return new PrimaryKey(constraintRealName, constraintName, constraintNewName, constraintRealColumns, constraintColumns);
    }
}
