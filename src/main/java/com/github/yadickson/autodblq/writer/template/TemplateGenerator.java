/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.writer.template;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.github.yadickson.autodblq.writer.DefinitionGeneratorType;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class TemplateGenerator {

    private static final Logger LOGGER = Logger.getLogger(TemplateGenerator.class);

    private final Configuration configuration;

    private String fileName;
    private Template template;

    public TemplateGenerator() {

        Version version = new Version(2, 3, 23);
        configuration = new Configuration(version);
        configuration.setObjectWrapper(new DefaultObjectWrapper(version));

        configuration.setClassForTemplateLoading(this.getClass(), "/templates");
        configuration.setDefaultEncoding("UTF-8");
    }

    public void execute(final DefinitionGeneratorType templateGeneratorType, final Map<String, Object> values, final String filename) {

        try (OutputStream stream = new FileOutputStream(filename); Writer out = new OutputStreamWriter(stream, Charset.forName("UTF-8"))) {

            makeFileName(templateGeneratorType);
            processTemplate(values, out);

        } catch (TemplateException | IOException | RuntimeException ex) {
            throw new TemplateGeneratorException(ex);
        }
    }

    private void makeFileName(final DefinitionGeneratorType templateGeneratorType) {
        fileName = templateGeneratorType.getTemplate().replace(File.separatorChar, '/');
        LOGGER.debug("[TemplateGenerator] File Template: " + fileName);
    }

    private void processTemplate(final Map<String, Object> input, final Writer out) throws TemplateException, IOException {
        template = configuration.getTemplate(fileName);
        template.process(input, out);
    }

}
