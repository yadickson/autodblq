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

    <!-- Add auto increment -->

    <changeSet id="${step?string["0000"]}" author="${author}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>
    </changeSet>

<#if tables?? >
<#list tables as table >
<#if table.incFields?? >
<#list table.incFields as inc >
    <!-- ${table.fullName} : ${inc.column} - ${inc.type} -->
</#list>
</#if>
</#list>

<#list tables as table >
<#if table.incFields?? >
<#list table.incFields as inc >
<#assign step++ >
    <changeSet id="${step?string["0000"]}" author="${author}" dbms="${driverName}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>

        <addAutoIncrement
<#if table.schema?? >
            schemaName="${table.schema}"
</#if>
            tableName="${table.name}"
            columnName="${inc.column}"
            incrementBy="1"
            startWith="1"
        />

        <rollback>
        </rollback>

    </changeSet>

</#list>
</#if>
</#list>
</#if>
</databaseChangeLog>
