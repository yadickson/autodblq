/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.view.base.model;

/**
 *
 * @author Yadickson Soto
 */
public final class ViewBase {

    private final String schema;
    private final String name;
    private final String content;

    public ViewBase(ViewBase viewBase) {
        this(viewBase.getSchema(), viewBase.getName(), viewBase.getContent());
    }

    public ViewBase(String schema, String name, String content) {
        this.schema = schema;
        this.name = name;
        this.content = content;
    }

    public String getSchema() {
        return schema;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

}
