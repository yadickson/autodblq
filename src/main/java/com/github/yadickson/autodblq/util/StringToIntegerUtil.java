/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.util;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.function.Function;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class StringToIntegerUtil implements Function<String, Integer> {

    private final StringTrimUtil stringTrimUtil;

    @Inject
    public StringToIntegerUtil(final StringTrimUtil stringTrimUtil) {
        this.stringTrimUtil = stringTrimUtil;
    }
    
    @Override
    public Integer apply(final String input) {
        try {
            return Integer.parseInt(stringTrimUtil.apply(input));
        }
        catch (Exception ex) {
            return 0;
        }
    }

}
