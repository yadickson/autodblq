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

    <!-- Add index -->

    <changeSet id="${step?string["00"]}" author="${author}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["00"]}"/>
    </changeSet>

<#list tables as table >
<#if table.indFields?? >
<#list table.indFields as ind >
<#assign step++ >
    <changeSet id="${step?string["00"]}" author="${author}" dbms="${driverName}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["00"]}"/>

        <createIndex
            indexName="${ind.name}"
<#if table.schema?? >
            schemaName="${table.schema}"
</#if>
            tableName="${table.name}"
            unique="${ind.isUnique?c}"
        >

<#list ind.columns?split(",") as icolumn>
            <column name="${icolumn}"/>
</#list>

        </createIndex>

        <rollback>
            <dropIndex
<#if table.schema?? >
                schemaName="${table.schema}"
</#if>
                tableName="${table.name}"
                indexName="${ind.name}"
            />
        </rollback>

    </changeSet>

</#list>
</#if>
</#list>
</databaseChangeLog>
