package com.github.yadickson.autodblq.db.table.data.support;

import java.util.*;

import org.apache.commons.lang.text.StrSubstitutor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;
import com.github.yadickson.autodblq.db.property.DataBaseProperty;
import com.github.yadickson.autodblq.db.table.columns.DataBaseTableColumnsWrapper;
import com.github.yadickson.autodblq.db.table.data.DataBaseDataTableBlockQuery;

@ExtendWith(MockitoExtension.class)
class PostgreSqlDataBaseDataTableBlockQueryTest {

    private DataBaseDataTableBlockQuery queryBuilder;

    @Mock
    private DataBaseTableColumnsWrapper tableMock;

    @Mock
    DataBaseProperty columnOneMock;

    @Mock
    DataBaseProperty columnTwoMock;

    @BeforeEach
    void setUp() {
        queryBuilder = new PostgreSqlDataBaseDataTableBlockQuery();
    }

    @Test
    void should_check_query_columns_getter_values_with_one_column() {

        String tableName = new Faker().lorem().word();
        String columnName = new Faker().lorem().word();
        long pageValue = new Faker().number().numberBetween(20, 40);
        long blocksValue = new Faker().number().numberBetween(80, 100);
        List<DataBaseProperty> columns = Collections.singletonList(columnOneMock);

        Mockito.when(columnOneMock.getName()).thenReturn(columnName);
        Mockito.when(tableMock.getFullName()).thenReturn(tableName);
        Mockito.when(tableMock.getColumns()).thenReturn(columns);

        String template = "SELECT m.${column} FROM ( SELECT ROW_NUMBER() OVER (ORDER BY ${column} ) AS ROW_NUMBER, ${column} FROM ${table} ) m WHERE m.ROW_NUMBER >= ${start} AND m.ROW_NUMBER < ${end} order by ${column}";
        Map<String, Object> data = new HashMap<>();

        long init = pageValue * blocksValue;
        long end = init + blocksValue;

        data.put("column", columnName);
        data.put("table", tableName);
        data.put("start", init + 1);
        data.put("end", end + 1);

        String expected =StrSubstitutor.replace(template, data);

        String response = queryBuilder.get(tableMock, pageValue, blocksValue);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(expected,  response);
    }

    @Test
    void should_check_query_columns_getter_values_with_two_columns() {

        String tableName = new Faker().lorem().word();
        String columnOneName = new Faker().lorem().word();
        String columnTwoName = new Faker().lorem().word();
        long pageValue = new Faker().number().numberBetween(20, 40);
        long blocksValue = new Faker().number().numberBetween(80, 100);
        List<DataBaseProperty> columns = Arrays.asList(columnOneMock, columnTwoMock);

        Mockito.when(columnOneMock.getName()).thenReturn(columnOneName);
        Mockito.when(columnTwoMock.getName()).thenReturn(columnTwoName);
        Mockito.when(tableMock.getFullName()).thenReturn(tableName);
        Mockito.when(tableMock.getColumns()).thenReturn(columns);

        String template = "SELECT m.${columnOne}, m.${columnTwo} FROM ( SELECT ROW_NUMBER() OVER (ORDER BY ${columnOne} ) AS ROW_NUMBER, ${columnOne}, ${columnTwo} FROM ${table} ) m WHERE m.ROW_NUMBER >= ${start} AND m.ROW_NUMBER < ${end} order by ${columnOne}";
        Map<String, Object> data = new HashMap<>();

        long init = pageValue * blocksValue;
        long end = init + blocksValue;

        data.put("columnOne", columnOneName);
        data.put("columnTwo", columnTwoName);
        data.put("table", tableName);
        data.put("start", init + 1);
        data.put("end", end + 1);

        String expected = StrSubstitutor.replace(template, data);

        String response = queryBuilder.get(tableMock, pageValue, blocksValue);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(expected,  response);
    }
}
