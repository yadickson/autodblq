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
public class StringToSnakeCaseUtil implements Function<String, String> {

    private final StringTrimUtil stringTrimUtil;

    @Inject
    public StringToSnakeCaseUtil(final StringTrimUtil stringTrimUtil) {
        this.stringTrimUtil = stringTrimUtil;
    }

    @Override
    public String apply(final String input) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";

        return stringTrimUtil.apply(input)
                .replaceAll(
                        regex, replacement)
                .toLowerCase(Locale.US);
    }
}
