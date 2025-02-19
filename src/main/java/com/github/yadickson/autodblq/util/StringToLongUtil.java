/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.util;

import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class StringToLongUtil implements Function<String, Long> {

    private final StringTrimUtil stringTrimUtil;

    @Inject
    public StringToLongUtil(final StringTrimUtil stringTrimUtil) {
        this.stringTrimUtil = stringTrimUtil;
    }
    
    @Override
    public Long apply(final String input) {
        try {
            return Long.parseLong(stringTrimUtil.apply(input));
        }
        catch (Exception ex) {
            return 0L;
        }
    }

}
