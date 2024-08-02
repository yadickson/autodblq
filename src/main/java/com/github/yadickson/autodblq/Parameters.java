/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq;

import java.util.List;

/**
 *
 * @author Yadickson Soto
 */
public class Parameters {

    private final String driver;
    private final String url;
    private final String username;
    private final String password;

    private final String author;
    private final String version;

    private final String encode;
    private final String csvQuotchar;
    private final String csvSeparator;
    private final String csvComment;

    private final String outputDirectory;

    private final String liquibaseVersion;
    private final Boolean liquibaseProductionEnabled;

    private final List<String> tables;
    private final List<String> dataTables;
    private final List<String> views;
    private final List<String> functions;

    private final Boolean addDbVersion;
    private final Boolean addSchema;
    private final Boolean addDbms;

    public Parameters(String driver, String url, String username, String password, String author, String version, String encode, String csvQuotchar, String csvSeparator, String csvComment, String outputDirectory, String liquibaseVersion, Boolean liquibaseProductionEnabled, List<String> tables, List<String> dataTables, List<String> views, List<String> functions, Boolean addDbVersion, Boolean addSchema, Boolean addDbms) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        this.author = author;
        this.version = version;
        this.encode = encode;
        this.csvQuotchar = csvQuotchar;
        this.csvSeparator = csvSeparator;
        this.csvComment = csvComment;
        this.outputDirectory = outputDirectory;
        this.liquibaseVersion = liquibaseVersion;
        this.liquibaseProductionEnabled = liquibaseProductionEnabled;
        this.tables = tables;
        this.dataTables = dataTables;
        this.views = views;
        this.functions = functions;
        this.addDbVersion = addDbVersion;
        this.addSchema = addSchema;
        this.addDbms = addDbms;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthor() {
        return author;
    }

    public String getVersion() {
        return version;
    }

    public String getEncode() {
        return encode;
    }

    public String getCsvQuotchar() {
        return csvQuotchar;
    }

    public String getCsvSeparator() {
        return csvSeparator;
    }

    public String getCsvComment() {
        return csvComment;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public String getLiquibaseVersion() {
        return liquibaseVersion;
    }

    public Boolean getLiquibaseProductionEnabled() {
        return liquibaseProductionEnabled;
    }

    public List<String> getTables() {
        return tables;
    }

    public List<String> getDataTables() {
        return dataTables;
    }

    public List<String> getViews() {
        return views;
    }

    public List<String> getFunctions() {
        return functions;
    }

    public Boolean getAddDbVersion() {
        return addDbVersion;
    }

    public Boolean getAddSchema() {
        return addSchema;
    }

    public Boolean getAddDbms() {
        return addDbms;
    }
}
