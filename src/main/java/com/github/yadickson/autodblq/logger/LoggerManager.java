/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.logger;

import javax.inject.Singleton;

import org.apache.maven.plugin.logging.Log;

/**
 *
 * @author Yadickson Soto
 */
@Singleton
public class LoggerManager {

    private Log logger;
    
    public void configure(final Log logger) {
        this.logger = logger;
    }

    public void debug(String message) {
        if (logger != null) {
            logger.debug(message);
        }
    }

    public void info(String message) {
        if (logger != null) {
            logger.info(message);
        }
    }

    public void warn(String message) {
        if (logger != null) {
            logger.warn(message);
        }
    }

    public void error(String message, Throwable ex) {
        if (logger != null) {
            logger.error(message, ex);
        }
    }
}
