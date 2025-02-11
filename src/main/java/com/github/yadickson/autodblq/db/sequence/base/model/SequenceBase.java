/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.sequence.base.model;

import com.github.yadickson.autodblq.db.property.DataBaseProperty;

/**
 *
 * @author Yadickson Soto
 */
public class SequenceBase extends DataBaseProperty {

    private final String realSchema;
    private final String schema;
    private final String newSchema;

    private final Boolean cycle;
    private final Integer startValue;
    private final Integer incrementBy;

    public SequenceBase(final SequenceBase sequence) {
        this(sequence.getRealSchema(), sequence.getRealName(), sequence.getSchema(), sequence.getName(), sequence.getNewSchema(), sequence.getNewName(), sequence.getType(), sequence.getCycle(), sequence.getStartValue(), sequence.getIncrementBy());
    }

    public SequenceBase(final String realSchema, final String realName, final String schema, final String name, final String newSchema, final String newName, final String type, final Boolean cycle, final Integer startValue, final Integer incrementBy) {
        super(realName, name, newName, type);
        this.realSchema = realSchema;
        this.schema = schema;
        this.newSchema = newSchema;
        this.startValue = startValue;
        this.incrementBy = incrementBy;
        this.cycle = cycle;
    }

    public String getRealSchema() {
        return realSchema;
    }

    public String getSchema() {
        return schema;
    }

    public String getNewSchema() {
        return newSchema;
    }

    public Integer getStartValue() {
        return startValue;
    }

    public Integer getIncrementBy() {
        return incrementBy;
    }

    public Boolean getCycle() {
        return cycle;
    }
}
