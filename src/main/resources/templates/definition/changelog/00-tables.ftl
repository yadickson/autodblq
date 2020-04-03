<?xml version="1.0" encoding="UTF-8"?>

<databaseChangeLog
    xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
    xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
    http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-${lqversion}.xsd
    http://www.liquibase.org/xml/ns/dbchangelog-ext
    http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd">
<#assign step = 1>

    <!-- Table definitions -->

    <changeSet id="${step?string["00"]}" author="${author}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["00"]}"/>
    </changeSet>

<#list tables as table >
<#assign step++ >
    <changeSet id="${step?string["00"]}" author="${author}" dbms="${driverName}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["00"]}"/>

        <createTable tableName="${table.name}"<#if table.schema?? > schemaName="${table.schema}"</#if><#if table.remarks?? > remarks="${table.remarks}"</#if>>
<#if table.fields?? >
<#list table.fields as column >
            <column name="${column.name}" type="${column.type}<#if column.isString >(${column.length})</#if>"<#if column.remarks?? > remarks="${column.remarks}"</#if>>
                <constraints nullable="<#if column.nullable?? >${column.nullable?c}<#else>false</#if>"/>
            </column>
</#list>
</#if>
        </createTable>

        <rollback>
            <dropTable tableName="${table.name}"<#if table.schema?? > schemaName="${table.schema}"</#if>/>
        </rollback>

    </changeSet>

</#list>
</databaseChangeLog>
