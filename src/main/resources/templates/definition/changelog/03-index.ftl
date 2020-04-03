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

    <!-- Add index -->

    <changeSet id="${step?string["00"]}" author="${author}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["00"]}"/>
    </changeSet>

<#list tables as table >
<#if table.idxFields?? >
<#list table.idxFields as idx >
<#assign step++ >
    <changeSet id="${step?string["00"]}" author="${author}" dbms="${driverName}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["00"]}"/>

        <createIndex
            indexName="${idx.name}"
<#if table.schema?? >
            schemaName="${table.schema}"
</#if>
            tableName="${table.name}"
            unique="false"
        >

            <column name="${idx.column}"/>

        </createIndex>

        <rollback>
            <dropIndex
<#if table.schema?? >
                schemaName="${table.schema}"
</#if>
                tableName="${table.name}"
                indexName="${idx.name}"
            />
        </rollback>

    </changeSet>

</#list>
</#if>
</#list>
</databaseChangeLog>
