<?xml version="1.0" encoding="UTF-8"?>

<!-- @GENERATOR.NAME@ -->
<!-- @GENERATOR.VERSION@ -->

<databaseChangeLog
    xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
    xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
    http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-${lqversion}.xsd
    http://www.liquibase.org/xml/ns/dbchangelog-ext
    http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd">
<#assign step = 1>

    <!-- Add default values -->

    <changeSet id="${step?string["0000"]}" author="${author}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>
    </changeSet>

<#list tables as table >
<#if table.fields?? >
<#list table.fields as column >
<#if column.defaultValue?? >
<#if column.isNumeric || column.isString >
<#assign step++ >
    <changeSet id="${step?string["0000"]}" author="${author}" dbms="${driverName}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>

        <addDefaultValue
<#if table.schema?? >
            schemaName="${table.schema}"
</#if>
            tableName="${table.name}"
            columnName="${column.name}"
<#if column.isNumeric >
            defaultValueNumeric="${column.defaultValue}"
<#else>
            defaultValue="${column.defaultValue}"
</#if>
        />

        <rollback>
            <dropDefaultValue
<#if table.schema?? >
                schemaName="${table.schema}"
</#if>
                tableName="${table.name}"
                columnName="${column.name}"
            />
        </rollback>

    </changeSet>

</#if>
</#if>
</#list>
</#if>
</#list>
</databaseChangeLog>
