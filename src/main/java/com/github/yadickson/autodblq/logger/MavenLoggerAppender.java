/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.logger;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.maven.plugin.logging.Log;

/**
 *
 * @author Yadickson Soto
 */
public class MavenLoggerAppender extends AppenderSkeleton {

    private final Log logger;
    
    public MavenLoggerAppender(final Log logger) {
        this.logger = logger;
    }

    @Override
    public void append(LoggingEvent event) {

        Level level = event.getLevel();
        String msg = event.getMessage().toString();

        switch (level.toInt()) {
            case Level.DEBUG_INT:
            case Level.TRACE_INT:
                logger.debug(msg);
                break;
            case Level.INFO_INT:
                logger.info(msg);
                break;
            case Level.WARN_INT:
                logger.warn(msg);
                break;
            case Level.ERROR_INT:
            case Level.FATAL_INT:
                logger.error(msg);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    @Override
    public void close() {
        // Nothing
    }

}
