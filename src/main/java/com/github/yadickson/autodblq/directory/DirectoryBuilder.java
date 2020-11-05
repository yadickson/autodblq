/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq.directory;

import java.io.File;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 *
 * @author Yadickson Soto
 */
@Named
@Singleton
public class DirectoryBuilder {

    public void execute(final String path) {
        File directory = new File(path);

        if (!directory.exists() && !directory.mkdirs()) {
            throw new DirectoryBuilderException("Fail to make " + directory + " directory.");
        }
    }
}
