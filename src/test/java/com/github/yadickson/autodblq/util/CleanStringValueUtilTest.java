package com.github.yadickson.autodblq.util;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CleanStringValueUtilTest {

    private final CleanStringValueUtil util = new CleanStringValueUtil(new StringTrimUtil());
    private final Faker faker = new Faker();

    @Test
    void should_clear_all_parentheses_from_input_number() {

        String name = faker.number().digits(2);
        String input = String.format("([(%s)])()", name);

        String response = util.apply(input);

        Assertions.assertEquals(name, response);
    }

    @Test
    void should_clear_all_parentheses_from_input_text() {

        String name = faker.name().name();
        String input = String.format("([(%s)])()", name);

        String response = util.apply(input);

        Assertions.assertEquals(name, response);
    }

}