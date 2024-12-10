/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.function.parameters;

import java.util.List;

import com.github.yadickson.autodblq.db.function.base.model.FunctionBase;
import com.github.yadickson.autodblq.db.property.DataBaseProperty;

/**
 *
 * @author Yadickson Soto
 */
public final class DataBaseFunctionParametersWrapper extends FunctionBase {

    private final List<DataBaseProperty> parameters;

    public DataBaseFunctionParametersWrapper(final FunctionBase function, final List<DataBaseProperty> parameters) {
        super(function);
        this.parameters = parameters;
    }

    public List<DataBaseProperty> getParameters() {
        return parameters;
    }

}
