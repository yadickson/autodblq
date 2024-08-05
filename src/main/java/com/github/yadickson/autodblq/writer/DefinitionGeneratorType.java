/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.writer;

/**
 *
 * @author Yadickson Soto
 */
public enum DefinitionGeneratorType {

    PROPERTIES("/definition/changelog/properties.ftl", "%02d-properties.xml"),
    TABLES("/definition/changelog/table.ftl", "%02d-tables.xml"),
    PRIMARY_KEYS("/definition/changelog/primary-key.ftl", "%02d-primary-keys.xml"),
    FOREIGN_KEYS("/definition/changelog/foreign-key.ftl", "%02d-foreign-keys.xml"),
    UNIQUES("/definition/changelog/unique.ftl", "%02d-unique.xml"),
    INDEXES("/definition/changelog/index.ftl", "%02d-index.xml"),
    DEFAULTS("/definition/changelog/default-value.ftl", "%02d-default-values.xml"),
    VIEWS("/definition/changelog/view.ftl", "%02d-views.xml"),
    FUNCTIONS("/definition/changelog/function.ftl", "%02d-functions.xml"),
    DATA_TABLES("/definition/changelog/load-data.ftl", "%02d-load-data.xml"),
    MASTER_CHANGLE_LOG("/definition/masterchangelog.ftl", "masterchangelog.xml"),
    VIEW("/definition/view.ftl", "%s.sql"),
    FUNCTION("/definition/function.ftl", "%s.sql"),
    PROCEDURE("/definition/procedure.ftl", "%s.sql"),
    DATA_TABLE("/definition/csv/data.ftl", "%s.csv");

    private final String template;
    private final String filename;

    private DefinitionGeneratorType(final String template, final String filename) {
        this.template = template;
        this.filename = filename;
    }

    public String getTemplate() {
        return template;
    }

    public String getFilename() {
        return filename;
    }
    
    
}
