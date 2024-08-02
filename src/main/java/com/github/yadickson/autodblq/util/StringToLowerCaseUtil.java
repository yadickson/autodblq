/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.util;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Locale;
import java.util.function.Function;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class StringToLowerCaseUtil implements Function<String, String> {

    private final StringTrimUtil stringTrimUtil;

    @Inject
    public StringToLowerCaseUtil(final StringTrimUtil stringTrimUtil) {
        this.stringTrimUtil = stringTrimUtil;
    }

    @Override
    public String apply(final String input) {
        return stringTrimUtil.apply(input).toLowerCase(Locale.US);
    }

}
