/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.util;

import java.util.Optional;
import java.util.function.Function;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class StringTrimUtil implements Function<String, String> {

    private static final String EMPTY = "";

    @Override
    public String apply(final String input) {
        return Optional.ofNullable(input).orElse(EMPTY).trim();
    }

}
