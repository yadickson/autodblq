<?xml version="1.0" encoding="UTF-8"?>

<!-- @GENERATOR.NAME@ -->
<!-- @GENERATOR.VERSION@ -->
<#if dbversion?? && addDbVersion?? && addDbVersion == true >
<!-- ${dbversion} -->
</#if>

<databaseChangeLog
    xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
    xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
    http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-${lqversion}.xsd
    http://www.liquibase.org/xml/ns/dbchangelog-ext
    http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd">
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

        <loadData
<#if table.schema?? && addSchema?? && addSchema == true >
            schemaName="${table.schema}"
</#if>
            tableName="${table.name}"
            encoding="${encode}"
            file="../table/${table.name}.csv"
            relativeToChangelogFile="true"
            commentLineStartsWith= "${csvComment}"
            quotchar=<#if csvQuotchar == "\"">'<#else>"</#if>${csvQuotchar}<#if csvQuotchar == "\"">'<#else>"</#if>
            separator=<#if csvSeparator == "\"">'<#else>"</#if>${csvSeparator}<#if csvSeparator == "\"">'<#else>"</#if>
        />

        <rollback>
            <delete tableName="${table.name}"<#if table.schema?? && addSchema?? && addSchema == true > schemaName="${table.schema}"</#if>/>
        </rollback>

    </changeSet>
</#list>
</#if>

</databaseChangeLog>
