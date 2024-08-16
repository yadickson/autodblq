/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.function.parameters;

import com.github.yadickson.autodblq.db.function.base.model.FunctionBase;

/**
 *
 * @author Yadickson Soto
 */
public interface DataBaseFunctionParametersQuery {

    String get(FunctionBase function);

}
