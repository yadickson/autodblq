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

import com.github.vertical_blank.sqlformatter.SqlFormatter;
import com.github.vertical_blank.sqlformatter.core.FormatConfig;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class StringToContentUtil implements Function<String, String> {

    private final StringTrimUtil stringTrimUtil;

    @Inject
    public StringToContentUtil(StringTrimUtil stringTrimUtil) {
        this.stringTrimUtil = stringTrimUtil;
    }

    @Override
    public String apply(final String input) {
        String fullContent = stringTrimUtil.apply(input);
        return SqlFormatter.format(fullContent,
                FormatConfig.builder()
                        .indent("    ")
                        .linesBetweenQueries(2)
                        .maxColumnLength(1000)
                        .skipWhitespaceNearBlockParentheses(true)
                        .build()
        );

//        String clearBlockComments = RegExUtils.replaceAll(formated, "/\\*.*?\\*/", " ");
//        return RegExUtils.replaceAll(clearBlockComments, Pattern.compile("(.*)--.*"), " ");
    }

}
