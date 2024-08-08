/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.writer.template;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.github.yadickson.autodblq.writer.DefinitionGeneratorType;

/**
 *
 * @author Yadickson Soto
 */
@Named
public class TemplateGenerator {

    private final TemplateGeneratorManager templateGeneratorManager;

    @Inject
    public TemplateGenerator(TemplateGeneratorManager templateGeneratorManager) {
        this.templateGeneratorManager = templateGeneratorManager;
    }

    public void execute(final DefinitionGeneratorType templateGeneratorType, final Map<String, Object> values, final String filename, boolean append) {

        try (OutputStream stream = new FileOutputStream(filename, append); Writer out = new OutputStreamWriter(stream, StandardCharsets.UTF_8)) {

            templateGeneratorManager.execute(templateGeneratorType, values, out);

        } catch (IOException | RuntimeException ex) {
            throw new TemplateGeneratorException(ex);
        }
    }

}
