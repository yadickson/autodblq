/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.logger;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.BasicConfigurator;
import org.apache.maven.plugin.logging.Log;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class MavenLoggerConfiguration {

    public void execute(final Log logger) {
        BasicConfigurator.configure(new MavenLoggerAppender(logger));
    }

}
