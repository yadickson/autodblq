/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint.defaults;

import com.github.yadickson.autodblq.db.table.constraint.defaults.model.TableDefault;
import com.github.yadickson.autodblq.db.table.constraint.defaults.model.TableDefaultBean;
import com.github.yadickson.autodblq.db.table.property.DataBaseTableProperty;
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
public class DataBaseTableDefaultConstraintMapper implements Function<TableDefaultBean, DataBaseTableProperty> {

    private static final Logger LOGGER = Logger.getLogger(DataBaseTableDefaultConstraintMapper.class);

    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;
    private final StringTrimUtil stringTrimUtil;

    @Inject
    public DataBaseTableDefaultConstraintMapper(final StringToSnakeCaseUtil stringToSnakeCaseUtil, final StringTrimUtil stringTrimUtil) {
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
        this.stringTrimUtil = stringTrimUtil;
    }

    @Override
    public DataBaseTableProperty apply(final TableDefaultBean tableBean) {
        final String constraintColumn = stringToSnakeCaseUtil.apply(tableBean.getColumn());
        final String constraintValue = stringTrimUtil.apply(tableBean.getValue());

        LOGGER.debug("[DataBaseTableDefaultMapper] Default Constraint Column: " + constraintColumn);
        LOGGER.debug("[DataBaseTableDefaultMapper] Default Constraint Value: " + constraintValue);

        return new TableDefault(constraintColumn, constraintValue);
    }
}
