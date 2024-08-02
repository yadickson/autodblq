/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.uniques;

import com.github.yadickson.autodblq.db.table.constraint.uniques.model.TableUnique;
import com.github.yadickson.autodblq.db.table.constraint.uniques.model.TableUniqueBean;
import com.github.yadickson.autodblq.db.table.property.DataBaseTableProperty;
import com.github.yadickson.autodblq.util.StringJoinUtil;
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
public class DataBaseTableUniqueConstraintMapper implements Function<TableUniqueBean, DataBaseTableProperty> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableUniqueConstraintMapper.class);

    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringJoinUtil stringJoinUtil;

    @Inject
    public DataBaseTableUniqueConstraintMapper(final StringToSnakeCaseUtil stringToSnakeCaseUtil, final StringJoinUtil stringJoinUtil) {
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringJoinUtil = stringJoinUtil;
    }

    @Override
    public DataBaseTableProperty apply(final TableUniqueBean tableBean) {
        final String constraintName = stringToSnakeCaseUtil.apply(tableBean.getName());
        final String constraintColumns = stringJoinUtil.apply(stringToSnakeCaseUtil.apply(tableBean.getColumns()));

        LOGGER.debug("[DataBaseTableDefaultMapper] Unique Constraint Name: " + constraintName);
        LOGGER.debug("[DataBaseTableDefaultMapper] Unique Constraint Columns: " + constraintColumns);

        return new TableUnique(constraintName, constraintColumns);
    }
}
