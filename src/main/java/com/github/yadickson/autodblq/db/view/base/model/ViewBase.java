/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.db.view.base.model;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Yadickson Soto
 */
public final class ViewBase {

    private final String schema;
    private final String name;
    private final String content;
    private final String fullName;

    public ViewBase(ViewBase viewBase) {
        this(viewBase.getSchema(), viewBase.getName(), viewBase.getContent());
    }

    public ViewBase(String schema, String name, String content) {
        this.schema = schema;
        this.name = name;
        this.content = content;
        this.fullName = StringUtils.isEmpty(schema) ? name : schema + "." + name;
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

    public String getFullName() {
        return fullName;
    }

}
