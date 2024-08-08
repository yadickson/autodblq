/*
 * Copyright (C) 2020 Yadickson Soto
 *
 * See <http://www.gnu.org/licenses/gpl-3.0.html>.
 */
package com.github.yadickson.autodblq;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 *
 * @author Yadickson Soto
 */
@Singleton
public class ParametersPlugin {

    private String driver;
    private String url;
    private String username;
    private String password;

    private String author;
    private String version;
    private String encode;

    private String outputDirectory;

    private List<String> tables;
    private List<String> dataTables;
    private List<String> views;
    private List<String> functions;

    private Boolean liquibaseProductionEnabled;
    private Boolean addDbVersion;
    private Boolean addSchema;
    private Boolean addDbms;
    private Boolean addNullable;
    private Boolean addIdentity;
    private Boolean keepNames;

    public String getDriver() {
        return driver;
    }

    public ParametersPlugin setDriver(String driver) {
        this.driver = driver;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ParametersPlugin setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public ParametersPlugin setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ParametersPlugin setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ParametersPlugin setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public ParametersPlugin setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getEncode() {
        return encode;
    }

    public ParametersPlugin setEncode(String encode) {
        this.encode = encode;
        return this;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public ParametersPlugin setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
        return this;
    }

    public Boolean getLiquibaseProductionEnabled() {
        return liquibaseProductionEnabled;
    }

    public ParametersPlugin setLiquibaseProductionEnabled(Boolean liquibaseProductionEnabled) {
        this.liquibaseProductionEnabled = liquibaseProductionEnabled;
        return this;
    }

    public List<String> getTables() {
        return tables;
    }

    public ParametersPlugin setTables(List<String> tables) {
        this.tables = getNotNullList(tables);
        return this;
    }

    public List<String> getDataTables() {
        return dataTables;
    }

    public ParametersPlugin setDataTables(List<String> dataTables) {
        this.dataTables = getNotNullList(dataTables);
        return this;
    }

    public List<String> getViews() {
        return views;
    }

    public ParametersPlugin setViews(List<String> views) {
        this.views = getNotNullList(views);
        return this;
    }

    public List<String> getFunctions() {
        return functions;
    }

    public ParametersPlugin setFunctions(List<String> functions) {
        this.functions = getNotNullList(functions);
        return this;
    }

    public Boolean getAddDbVersion() {
        return addDbVersion;
    }

    public ParametersPlugin setAddDbVersion(Boolean addDbVersion) {
        this.addDbVersion = addDbVersion;
        return this;
    }

    public Boolean getAddSchema() {
        return addSchema;
    }

    public ParametersPlugin setAddSchema(Boolean addSchema) {
        this.addSchema = addSchema;
        return this;
    }

    public Boolean getAddDbms() {
        return addDbms;
    }

    public ParametersPlugin setAddDbms(Boolean addDbms) {
        this.addDbms = addDbms;
        return this;
    }

    public Boolean getAddNullable() {
        return addNullable;
    }

    public ParametersPlugin setAddNullable(Boolean addNullable) {
        this.addNullable = addNullable;
        return this;
    }

    public Boolean getAddIdentity() {
        return addIdentity;
    }

    public ParametersPlugin setAddIdentity(Boolean addIdentity) {
        this.addIdentity = addIdentity;
        return this;
    }

    public Boolean getKeepNames() {
        return keepNames;
    }

    public ParametersPlugin setKeepNames(Boolean keepNames) {
        this.keepNames = keepNames;
        return this;
    }

    private List<String> getNotNullList(List<String> list) {
        return list == null ? new ArrayList<>() : list.stream().map(value -> value.toLowerCase(Locale.US)).collect(Collectors.toList());
    }
}
