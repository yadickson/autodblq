/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.writer.template;

import com.github.yadickson.autodblq.writer.DefinitionGeneratorType;
import freemarker.template.*;
import org.apache.log4j.Logger;

import javax.inject.Named;
import java.io.*;
import java.util.Map;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class TemplateGeneratorManager {

    private static final Logger LOGGER = Logger.getLogger(TemplateGeneratorManager.class);

    private final Configuration configuration;

    public TemplateGeneratorManager() {

        Version version = new Version(2, 3, 23);
        configuration = new Configuration(version);
        configuration.setObjectWrapper(new DefaultObjectWrapper(version));

        configuration.setClassForTemplateLoading(this.getClass(), "/templates");
        configuration.setDefaultEncoding("UTF-8");
    }

    public void execute(final DefinitionGeneratorType templateGeneratorType, final Map<String, Object> values, final Writer out) {

        try {

            String filename = makeFileName(templateGeneratorType);
            processTemplate(filename, values, out);

        } catch (TemplateException | IOException | RuntimeException ex) {
            throw new TemplateGeneratorException(ex);
        }
    }

    private String makeFileName(final DefinitionGeneratorType templateGeneratorType) {
        String fileName = templateGeneratorType.getTemplate().replace(File.separatorChar, '/');
        LOGGER.debug("[TemplateGenerator] File Template: " + fileName);
        return fileName;
    }

    private void processTemplate(final String fileName, final Map<String, Object> input, final Writer out) throws TemplateException, IOException {
        configuration.getTemplate(fileName).process(input, out);
    }

}
