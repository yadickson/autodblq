/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.primarykeys;

import com.github.yadickson.autodblq.db.table.constraint.primarykeys.model.TablePrimaryKey;
import com.github.yadickson.autodblq.db.table.constraint.primarykeys.model.TablePrimaryKeyBean;
import com.github.yadickson.autodblq.db.table.property.DataBaseTableProperty;
import com.github.yadickson.autodblq.util.StringJoinUtil;
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
public class DataBaseTablePrimaryKeyConstraintMapper implements Function<TablePrimaryKeyBean, DataBaseTableProperty> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTablePrimaryKeyConstraintMapper.class);

    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringJoinUtil stringJoinUtil;

    @Inject
    public DataBaseTablePrimaryKeyConstraintMapper(final StringToSnakeCaseUtil stringToSnakeCaseUtil, final StringJoinUtil stringJoinUtil) {
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringJoinUtil = stringJoinUtil;
    }

    @Override
    public DataBaseTableProperty apply(final TablePrimaryKeyBean tableBean) {
        final String constraintName = stringToSnakeCaseUtil.apply(tableBean.getName());
        final String constraintColumns = stringJoinUtil.apply(stringToSnakeCaseUtil.apply(tableBean.getColumns()));

        LOGGER.debug("[DataBaseTableDefaultMapper] PrimaryKey Constraint Name: " + constraintName);
        LOGGER.debug("[DataBaseTableDefaultMapper] PrimaryKey Constraint Columns: " + constraintColumns);

        return new TablePrimaryKey(constraintName, constraintColumns);
    }
}
