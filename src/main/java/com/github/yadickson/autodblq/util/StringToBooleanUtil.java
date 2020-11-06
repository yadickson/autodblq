/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.util;

import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class StringToBooleanUtil implements Function<String, Boolean> {

    private final StringTrimUtil stringTrimUtil;
    
    private static final String TRUE = "true";

    @Inject
    public StringToBooleanUtil(final StringTrimUtil stringTrimUtil) {
        this.stringTrimUtil = stringTrimUtil;
    }
    
    @Override
    public Boolean apply(final String input) {
        return TRUE.equalsIgnoreCase(stringTrimUtil.apply(input));
    }

}
