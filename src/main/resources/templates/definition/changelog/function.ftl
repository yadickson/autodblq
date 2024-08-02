<?xml version="1.0" encoding="UTF-8"?>

<!-- @GENERATOR.NAME@ -->
<!-- @GENERATOR.VERSION@ -->
<#if dbversion?? && addDbVersion?? && addDbVersion == true >
<!-- ${dbversion} -->
</#if>

<databaseChangeLog
    xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
    xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
<#if lqpro?? && lqpro >
    xmlns:pro="http://www.liquibase.org/xml/ns/pro"
</#if>
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
    http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-${lqversion}.xsd
<#if lqpro?? && lqpro >
    http://www.liquibase.org/xml/ns/pro
    http://www.liquibase.org/xml/ns/pro/liquibase-pro-${lqversion}.xsd
</#if>
    http://www.liquibase.org/xml/ns/dbchangelog-ext
    http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd">
<#assign step = 1>

    <!-- Function definitions -->

    <changeSet id="${step?string["0000"]}" author="${author}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>
    </changeSet>
<#if functions?? >
<#list functions as func >

<#assign step++ >
    <changeSet id="${step?string["0000"]}" author="${author}" <#if addDbms?? && addDbms == true>dbms="${driverName}" </#if>runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>

<#if lqpro?? && lqpro >
<#if func.isFunction >
        <pro:createFunction
            functionName="${func.name}"
            path="../function/${func.name}.sql"
<#else>
        <pro:createProcedure
            procedureName="${func.name}"
            path="../procedure/${func.name}.sql"
</#if>
<#if func.schema?? >
            schemaName="${func.schema}"
</#if>
            encoding="${encode}"
            replaceIfExists="true"
            relativeToChangelogFile="true"
        />
<#else>
        <sqlFile
            encoding="${encode}"
            path="../<#if func.isFunction >function<#else>procedure</#if>/${func.name}.sql"
            relativeToChangelogFile="true"
            splitStatements="false"
            stripComments="true"
        />
</#if>

        <rollback>
<#if lqpro?? && lqpro >
            <pro:drop<#if func.isFunction >Function function<#else>Prodecure procedure</#if>Name="${func.name}"<#if func.schema?? > schemaName="${func.schema}"</#if>/>
<#else>
            <sql><![CDATA[ DROP <#if func.isFunction >FUNCTION<#else>PROCEDURE</#if> <#if func.schema?? >"${func.schema}".</#if>"${func.name}" ]]></sql>
</#if>
        </rollback>

    </changeSet>
</#list>
</#if>

</databaseChangeLog>
