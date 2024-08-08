<?xml version="1.0" encoding="UTF-8"?>

<!-- @GENERATOR.NAME@ -->
<!-- @GENERATOR.VERSION@ -->
<#if dbversion?? && addDbVersion?? && addDbVersion == true >
<!-- ${dbversion} -->
</#if>

<databaseChangeLog
    xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
<#if lqpro?? && lqpro >
    xmlns:pro="http://www.liquibase.org/xml/ns/pro"
</#if>
    xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
    xsi:schemaLocation="
        http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd
<#if lqpro?? && lqpro >
        http://www.liquibase.org/xml/ns/pro http://www.liquibase.org/xml/ns/pro/liquibase-pro-latest.xsd
</#if>
        http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd">
<#assign step = 1>

    <!-- Load Data Tables definitions -->

    <changeSet id="${step?string["0000"]}" author="${author}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>
    </changeSet>
<#if dataTables?? >
<#list dataTables as table >

<#assign step++ >
    <changeSet id="${step?string["0000"]}" author="${author}" <#if addDbms?? && addDbms == true>dbms="${driverName}" </#if>runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>

        <sqlFile
            encoding="${encode}"
            path="../data/<#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if>.sql"
            relativeToChangelogFile="true"
            splitStatements="false"
            stripComments="true"
        />

        <rollback>
            <delete tableName="<#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if>"<#if table.schema?? && addSchema?? && addSchema == true > schemaName="<#if keepNames?? && keepNames == true>${table.realSchema}<#else>${table.newSchema}</#if>"</#if>/>
        </rollback>

    </changeSet>
</#list>
</#if>

</databaseChangeLog>
