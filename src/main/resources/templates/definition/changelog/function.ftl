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
            functionName="<#if keepNames?? && keepNames == true>${func.realName}<#else>${func.newName}</#if>"
            path="../function/<#if keepNames?? && keepNames == true>${func.realName}<#else>${func.newName}</#if>.sql"
<#else>
        <pro:createProcedure
            procedureName="<#if keepNames?? && keepNames == true>${func.realName}<#else>${func.newName}</#if>"
            path="../procedure/<#if keepNames?? && keepNames == true>${func.realName}<#else>${func.newName}</#if>.sql"
</#if>
<#if func.schema?? && addSchema?? && addSchema == true >
            schemaName="${func.schema}"
</#if>
            encoding="${encode}"
            replaceIfExists="true"
            relativeToChangelogFile="true"
        />
<#else>
        <sqlFile
            encoding="${encode}"
            path="../<#if func.isFunction >function<#else>procedure</#if>/<#if keepNames?? && keepNames == true>${func.realName}<#else>${func.newName}</#if>.sql"
            relativeToChangelogFile="true"
            splitStatements="false"
            stripComments="true"
        />
</#if>

        <rollback>
<#if lqpro?? && lqpro >
            <pro:drop<#if func.isFunction >Function function<#else>Prodecure procedure</#if>Name="<#if keepNames?? && keepNames == true>${func.realName}<#else>${func.newName}</#if>"<#if func.schema?? && addSchema?? && addSchema == true> schemaName="<#if keepNames?? && keepNames == true>${func.realSchema}<#else>${func.newSchema}</#if>"</#if>/>
<#else>
            <sql><![CDATA[ DROP <#if func.isFunction >FUNCTION<#else>PROCEDURE</#if> <#if func.schema?? && addSchema?? && addSchema == true>"<#if keepNames?? && keepNames == true>${func.realSchema}<#else>${func.newSchema}</#if>".</#if>"<#if keepNames?? && keepNames == true>${func.realName}<#else>${func.newName}</#if>" ]]></sql>
</#if>
        </rollback>

    </changeSet>
</#list>
</#if>

</databaseChangeLog>
