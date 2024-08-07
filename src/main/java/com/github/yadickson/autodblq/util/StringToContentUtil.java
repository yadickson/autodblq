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
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;
import java.util.regex.Pattern;

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
        String clearBlockComments = RegExUtils.removeAll(stringTrimUtil.apply(input), "/\\*.*?\\*/");
        String clearInlineComments = RegExUtils.removeAll(clearBlockComments, Pattern.compile("(.*)--.*"));
        return SqlFormatter.format(clearInlineComments,
                FormatConfig.builder()
                        .indent("    ")
                        .linesBetweenQueries(2)
                        .maxColumnLength(1000)
                        .skipWhitespaceNearBlockParentheses(true)
                        .build()
        );
    }

}
