/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.indexes;

import com.github.yadickson.autodblq.db.table.constraint.indexes.model.TableIndex;
import com.github.yadickson.autodblq.db.table.constraint.indexes.model.TableIndexBean;
import com.github.yadickson.autodblq.db.table.property.DataBaseTableProperty;
import com.github.yadickson.autodblq.util.StringJoinUtil;
import com.github.yadickson.autodblq.util.StringToBooleanUtil;
import com.github.yadickson.autodblq.util.StringToSnakeCaseUtil;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.function.Function;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class DataBaseTableIndexConstraintMapper implements Function<TableIndexBean, DataBaseTableProperty> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableIndexConstraintMapper.class);

    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringJoinUtil stringJoinUtil;
    private final StringToBooleanUtil stringToBooleanUtil;

    @Inject
    public DataBaseTableIndexConstraintMapper(final StringToSnakeCaseUtil stringToSnakeCaseUtil, final StringJoinUtil stringJoinUtil, final StringToBooleanUtil stringToBooleanUtil) {
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringJoinUtil = stringJoinUtil;
        this.stringToBooleanUtil = stringToBooleanUtil;
    }

    @Override
    public DataBaseTableProperty apply(final TableIndexBean tableBean) {
        final String constraintName = stringToSnakeCaseUtil.apply(tableBean.getName());
        final String constraintColumns = stringJoinUtil.apply(stringToSnakeCaseUtil.apply(tableBean.getColumns()));
        final Boolean constraintIsUnique = stringToBooleanUtil.apply(tableBean.getIsunique());

        LOGGER.debug("[DataBaseTableDefaultMapper] Index Constraint Name: " + constraintName);
        LOGGER.debug("[DataBaseTableDefaultMapper] Index Constraint Columns: " + constraintColumns);
        LOGGER.debug("[DataBaseTableDefaultMapper] Index Constraint IsUnique: " + constraintIsUnique);

        return new TableIndex(constraintName, constraintColumns, constraintIsUnique);
    }
}
