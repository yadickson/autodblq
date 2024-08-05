/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.util;

import javax.inject.Inject;
import javax.inject.Named;
import com.github.vertical_blank.sqlformatter.SqlFormatter;
import com.github.vertical_blank.sqlformatter.core.FormatConfig;

import java.util.function.Function;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class StringToContentUtil implements Function<String, String> {

    private final StringToSnakeCaseUtil stringToSnakeCaseUtil;

    @Inject
    public StringToContentUtil(final StringToSnakeCaseUtil stringToSnakeCaseUtil) {
        this.stringToSnakeCaseUtil = stringToSnakeCaseUtil;
    }

    @Override
    public String apply(final String input) {
        return SqlFormatter.format(stringToSnakeCaseUtil.apply(input),
                FormatConfig.builder()
                        .indent("    ")
                        .linesBetweenQueries(2)
                        .maxColumnLength(1000)
                        .skipWhitespaceNearBlockParentheses(true)
                        .build()
        );
    }

}
