package com.github.yadickson.autodblq.writer.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TableColumnTypeUtilTest {

    private TableColumnTypeUtil util;

    @BeforeEach
    void setUp() {
        util = new TableColumnTypeUtil();
    }

    @ParameterizedTest
    @ValueSource(strings = { "cHar", "VarChaR", "nVarChar", "UuId", "UniQueIDeNtiFieR", "tExT" })
    void should_check_is_string(String input) {
        Assertions.assertTrue(util.isString(input));
    }

    @ParameterizedTest
    @ValueSource(strings = { "InT", "flOAt", "ReAl" })
    void should_check_is_numeric(String input) {
        Assertions.assertTrue(util.isNumeric(input));
    }

    @ParameterizedTest
    @ValueSource(strings = { "DaTe", "TimeStAmP" })
    void should_check_is_date(String input) {
        Assertions.assertTrue(util.isDate(input));
    }
}