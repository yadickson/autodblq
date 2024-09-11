/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.writer.template;

import freemarker.template.*;

import javax.inject.Singleton;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 *
 * @author Yadickson Soto
 */
@Singleton
public class TemplateConfiguration {

    private final Configuration configuration;

    public TemplateConfiguration() {

        Version version = new Version(2, 3, 23);
        configuration = new Configuration(version);
        configuration.setObjectWrapper(new DefaultObjectWrapper(version));

        configuration.setClassForTemplateLoading(this.getClass(), "/templates");
        configuration.setDefaultEncoding("UTF-8");
    }

    public void getTemplate(final String fileName, final Map<String, Object> input, final Writer out) throws IOException, TemplateException {
        configuration.getTemplate(fileName).process(input, out);
    }
}
