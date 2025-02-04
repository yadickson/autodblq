package com.github.yadickson.autodblq.writer.template;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;
import com.github.yadickson.autodblq.db.table.columns.model.Column;
import com.github.yadickson.autodblq.writer.util.TableColumnTypeUtil;

import freemarker.template.TemplateException;

@ExtendWith(MockitoExtension.class)
class TableColumnTemplateConfigurationTest {

    private static final String TABLE_FILE = "changelog" + File.separator + "table.ftl";
    private static final String TABLE_COLUMN_FILE = "changelog" + File.separator + "table-column.ftl";
    private static final String TABLE_COLUMN_NAME = "column";
    private static final String KEEP_NAMES = "keepNames";
    private static final String TABLE_COLUMN_UTIL = "typeUtil";
    private static final String ADD_NULLABLE = "addNullable";
    private static final String ADD_IDENTITY = "addIdentity";
    private static final String KEEP_TYPES = "keepTypes";

    private TemplateConfiguration configuration;

    @Mock
    private Column columnMock;

    @BeforeEach
    void setUp() {
        configuration = new TemplateConfiguration();
    }

    @Test
    void should_check_table_column_transformation_when_input_is_empty() throws IOException, TemplateException {
        Map<String, Object> input = new HashMap<>();
        String response = getTemplateProcessed(TABLE_COLUMN_FILE, input);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void should_check_table_column_transformation_when_input_table_column_is_null() throws IOException, TemplateException {
        Map<String, Object> input = new HashMap<>();

        input.put(TABLE_COLUMN_NAME, null);

        String response = getTemplateProcessed(TABLE_COLUMN_FILE, input);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void should_check_table_column_transformation_when_input_table_column_is_not_null() throws IOException, TemplateException {
        Map<String, Object> input = new HashMap<>();

        input.put(TABLE_COLUMN_NAME, columnMock);

        String response = getTemplateProcessed(TABLE_COLUMN_FILE, input);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.contains("<column name=\"-\" type=\"-\"/>"));
    }

    @Test
    void should_check_table_column_transformation_real_name_when_keep_names_is_true() throws IOException, TemplateException {
        Map<String, Object> input = new HashMap<>();
        String realName = new Faker().name().fullName();

        Mockito.when(columnMock.getRealName()).thenReturn(realName);

        input.put(KEEP_NAMES, true);
        input.put(TABLE_COLUMN_NAME, columnMock);

        String response = getTemplateProcessed(TABLE_COLUMN_FILE, input);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.contains(String.format("<column name=\"%s\" type=\"-\"/>", realName)));

        Mockito.verify(columnMock, Mockito.times(1)).getRealName();
        Mockito.verify(columnMock, Mockito.never()).getNewName();
    }

    @Test
    void should_check_table_column_transformation_real_name_when_keep_names_is_false() throws IOException, TemplateException {
        Map<String, Object> input = new HashMap<>();
        String newName = new Faker().name().firstName();

        Mockito.when(columnMock.getNewName()).thenReturn(newName);

        input.put(KEEP_NAMES, false);
        input.put(TABLE_COLUMN_NAME, columnMock);

        String response = getTemplateProcessed(TABLE_COLUMN_FILE, input);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.contains(String.format("<column name=\"%s\" type=\"-\"/>", newName)));

        Mockito.verify(columnMock, Mockito.times(1)).getNewName();
        Mockito.verify(columnMock, Mockito.never()).getRealName();
    }

    @Test
    void should_check_table_column_transformation_type_when_property_type_and_keep_types_is_false() throws IOException, TemplateException {
        Map<String, Object> input = new HashMap<>();
        String propertyType = new Faker().letterify("abcd_xyz", true);

        Mockito.when(columnMock.getPropertyType()).thenReturn(propertyType);

        input.put(KEEP_TYPES, false);
        input.put(TABLE_COLUMN_NAME, columnMock);

        String response = getTemplateProcessed(TABLE_COLUMN_FILE, input);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.contains(String.format("<column name=\"-\" type=\"${%s}\"/>", propertyType.toLowerCase(Locale.US))));

        Mockito.verify(columnMock, Mockito.atLeast(2)).getPropertyType();
    }

    @Test
    void should_check_table_column_transformation_type_when_property_type_and_keep_types_is_true() throws IOException, TemplateException {
        Map<String, Object> input = new HashMap<>();
        String type = new Faker().internet().uuid();

        Mockito.when(columnMock.getType()).thenReturn(type);

        input.put(KEEP_TYPES, true);
        input.put(TABLE_COLUMN_NAME, columnMock);

        String response = getTemplateProcessed(TABLE_COLUMN_FILE, input);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.contains(String.format("<column name=\"-\" type=\"%s\"/>", type)));

        Mockito.verify(columnMock, Mockito.atLeast(1)).getType();
    }

    @Test
    void should_check_table_column_transformation_type_when_type_is_present_and_has_length() throws IOException, TemplateException {
        Map<String, Object> input = new HashMap<>();
        String type = new Faker().internet().uuid();
        Integer length = new Faker().number().numberBetween(10, 20);

        Mockito.when(columnMock.getType()).thenReturn(type);
        Mockito.when(columnMock.getLength()).thenReturn(length);

        input.put(KEEP_NAMES, false);
        input.put(TABLE_COLUMN_NAME, columnMock);

        String response = getTemplateProcessed(TABLE_COLUMN_FILE, input);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.contains(String.format("<column name=\"-\" type=\"%s(%d)\"/>", type, length)));

        Mockito.verify(columnMock, Mockito.atLeast(1)).getType();
        Mockito.verify(columnMock, Mockito.atLeast(2)).getLength();
    }

    @Test
    void should_check_table_column_transformation_type_when_type_is_present_but_has_not_length() throws IOException, TemplateException {
        Map<String, Object> input = new HashMap<>();
        String type = new Faker().internet().uuid();

        Mockito.when(columnMock.getType()).thenReturn(type);
        Mockito.when(columnMock.getLength()).thenReturn(null);

        input.put(KEEP_NAMES, false);
        input.put(TABLE_COLUMN_NAME, columnMock);

        String response = getTemplateProcessed(TABLE_COLUMN_FILE, input);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.contains(String.format("<column name=\"-\" type=\"%s\"/>", type)));

        Mockito.verify(columnMock, Mockito.atLeast(1)).getType();
        Mockito.verify(columnMock, Mockito.times(1)).getLength();
    }

    @Test
    void should_check_table_column_transformation_type_when_type_is_present_but_length_is_zero() throws IOException, TemplateException {
        Map<String, Object> input = new HashMap<>();
        String type = new Faker().internet().uuid();

        Mockito.when(columnMock.getType()).thenReturn(type);
        Mockito.when(columnMock.getLength()).thenReturn(0);

        input.put(KEEP_NAMES, false);
        input.put(TABLE_COLUMN_NAME, columnMock);

        String response = getTemplateProcessed(TABLE_COLUMN_FILE, input);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.contains(String.format("<column name=\"-\" type=\"%s\"/>", type)));

        Mockito.verify(columnMock, Mockito.atLeast(1)).getType();
        Mockito.verify(columnMock, Mockito.atLeast(2)).getLength();
    }

    @Test
    void should_check_table_column_transformation_remarks() throws IOException, TemplateException {
        Map<String, Object> input = new HashMap<>();
        String remarks = new Faker().internet().uuid();

        Mockito.when(columnMock.getRemarks()).thenReturn(remarks);

        input.put(KEEP_NAMES, false);
        input.put(TABLE_COLUMN_NAME, columnMock);

        String response = getTemplateProcessed(TABLE_COLUMN_FILE, input);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.contains(String.format("<column name=\"-\" type=\"-\" remarks=\"%s\"/>", remarks)));

        Mockito.verify(columnMock, Mockito.times(2)).getRemarks();
    }

    @ParameterizedTest
    @ValueSource(booleans = { true, false })
    void should_check_table_column_add_nullable(boolean nullable) throws IOException, TemplateException {
        Map<String, Object> input = new HashMap<>();

        Mockito.when(columnMock.getNullable()).thenReturn(nullable);

        input.put(KEEP_NAMES, false);
        input.put(ADD_NULLABLE, true);
        input.put(TABLE_COLUMN_NAME, columnMock);

        String response = getTemplateProcessed(TABLE_COLUMN_FILE, input);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.contains("<column name=\"-\" type=\"-\">"));
        Assertions.assertTrue(response.contains(String.format("<constraints nullable=\"%b\"/>", nullable)));
        Assertions.assertTrue(response.contains("</column>"));

        Mockito.verify(columnMock, Mockito.times(2)).getNullable();
    }

    @Test
    void should_check_table_column_add_identity_by_default() throws IOException, TemplateException {
        Map<String, Object> input = new HashMap<>();

        Mockito.when(columnMock.getIdentity()).thenReturn(true);
        Mockito.when(columnMock.getStartWith()).thenReturn(null);
        Mockito.when(columnMock.getIncrementBy()).thenReturn(null);

        input.put(KEEP_NAMES, false);
        input.put(ADD_IDENTITY, true);
        input.put(TABLE_COLUMN_NAME, columnMock);

        String response = getTemplateProcessed(TABLE_COLUMN_FILE, input);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.contains("<column name=\"-\" type=\"-\" autoIncrement=\"true\" startWith=\"1\" incrementBy=\"1\" />"));
    }

    @Test
    void should_check_table_column_add_identity_but_disable() throws IOException, TemplateException {
        Map<String, Object> input = new HashMap<>();

        input.put(KEEP_NAMES, false);
        input.put(ADD_IDENTITY, true);
        input.put(TABLE_COLUMN_NAME, columnMock);

        String response = getTemplateProcessed(TABLE_COLUMN_FILE, input);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.contains("<column name=\"-\" type=\"-\"/>"));
    }

    @Test
    void should_check_table_column_add_identity() throws IOException, TemplateException {
        Map<String, Object> input = new HashMap<>();

        int startWith = new Faker().number().numberBetween(10, 20);
        int incrementBy = new Faker().number().numberBetween(30, 40);

        Mockito.when(columnMock.getIdentity()).thenReturn(true);
        Mockito.when(columnMock.getStartWith()).thenReturn(startWith);
        Mockito.when(columnMock.getIncrementBy()).thenReturn(incrementBy);

        input.put(KEEP_NAMES, false);
        input.put(ADD_IDENTITY, true);
        input.put(TABLE_COLUMN_NAME, columnMock);

        String response = getTemplateProcessed(TABLE_COLUMN_FILE, input);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.contains(String.format("<column name=\"-\" type=\"-\" autoIncrement=\"true\" startWith=\"%d\" incrementBy=\"%d\" />", startWith, incrementBy)));
    }

    @Test
    void should_check_table_transformation_when_input_is_null() throws IOException, TemplateException {
        Map<String, Object> input = new HashMap<>();
        String response = getTemplateProcessed(TABLE_FILE, input);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.trim().isEmpty());
    }

    private String getTemplateProcessed(String filename, Map<String, Object> input) throws IOException, TemplateException {
        try (Writer out = new StringWriter())
        {
            configuration.getTemplate(filename, input, out);
            return out.toString();
        }
    }
}
