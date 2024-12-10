/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.util;

import java.util.function.Function;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.RegExUtils;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class CleanStringValueUtil implements Function<String, String> {

    private final StringTrimUtil stringTrimUtil;

    @Inject
    public CleanStringValueUtil(StringTrimUtil stringTrimUtil) {
        this.stringTrimUtil = stringTrimUtil;
    }

    @Override
    public String apply(final String input) {
        String fullInput = stringTrimUtil.apply(input);
        String clean = RegExUtils.removeAll(fullInput, Pattern.compile("[\\[\\]]"));
        String removeEndLine = RegExUtils.replaceAll(clean, Pattern.compile("[\n\r]"), " ");
        return RegExUtils.removeAll(removeEndLine, Pattern.compile("::text"));
    }

}
