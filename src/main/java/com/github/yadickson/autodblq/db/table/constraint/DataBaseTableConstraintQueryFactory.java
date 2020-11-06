/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.table.constraint;

import java.util.function.Function;

import com.github.yadickson.autodblq.db.connection.driver.Driver;

/**
 *
 * @author Yadickson Soto
 */
public interface DataBaseTableConstraintQueryFactory extends Function<Driver, DataBaseTableConstraintQuery> {

}
