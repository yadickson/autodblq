package com.github.yadickson.autodblq.db.support;

import com.github.yadickson.autodblq.db.connection.driver.Driver;

public abstract class SupportType implements Support {

    private final Driver type;

    public SupportType(final Driver type) {
        this.type = type;
    }

    public Driver getType()
    {
        return type;
    }
}
