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

    PROPERTIES("/changelog/properties.ftl", "%02d-properties.xml"),
    TABLES("/changelog/tables.ftl", "%02d-tables.xml"),
    TYPES("/changelog/types.ftl", "%02d-types.xml"),
    PRIMARY_KEYS("/changelog/primaries.ftl", "%02d-primary-key-constraints.xml"),
    FOREIGN_KEYS("/changelog/foreigns.ftl", "%02d-foreign-key-constraints.xml"),
    UNIQUES("/changelog/uniques.ftl", "%02d-unique-constraints.xml"),
    INDEXES("/changelog/indexes.ftl", "%02d-index-constraints.xml"),
    DEFAULTS("/changelog/defaults.ftl", "%02d-default-constraints.xml"),
    CHECKS("/changelog/checks.ftl", "%02d-check-constraints.xml"),
    VIEWS("/changelog/views.ftl", "%02d-views.xml"),
    FUNCTIONS("/changelog/functions.ftl", "%02d-functions.xml"),
    PROCEDURES("/changelog/procedures.ftl", "%02d-procedures.xml"),
    DATA_TABLES("/changelog/datasets.ftl", "%02d-datasets.xml"),
    MASTER_CHANGELOG("/masterchangelog.ftl", "masterchangelog.xml"),
    VIEW("/sql/view.ftl", "%s.sql"),
    FUNCTION("/sql/function.ftl", "%s.sql"),
    PROCEDURE("/sql/procedure.ftl", "%s.sql"),
    DATA_TABLE("/sql/data.ftl", "%s.sql"),
    DATA_INSERT_TABLE("/sql/data-insert.ftl", "%s.sql");

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
