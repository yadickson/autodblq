/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.writer.template;

import java.io.*;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.logger.LoggerManager;
import com.github.yadickson.autodblq.writer.DefinitionGeneratorType;

import freemarker.template.*;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class TemplateGeneratorManager {

    private final LoggerManager loggerManager;
    private final TemplateConfiguration templateConfiguration;

    @Inject
    public TemplateGeneratorManager(LoggerManager loggerManager, final TemplateConfiguration templateConfiguration) {
        this.loggerManager = loggerManager;
        this.templateConfiguration = templateConfiguration;
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
        loggerManager.debug("[TemplateGenerator] File Template: " + fileName);
        return fileName;
    }

    private void processTemplate(final String fileName, final Map<String, Object> input, final Writer out) throws TemplateException, IOException {
        templateConfiguration.getTemplate(fileName, input, out);
    }
}
