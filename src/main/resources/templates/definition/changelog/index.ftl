<?xml version="1.0" encoding="UTF-8"?>

<!-- @GENERATOR.NAME@ -->
<!-- @GENERATOR.VERSION@ -->
<#if dbversion?? >
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

    <!-- Add index -->

    <changeSet id="${step?string["0000"]}" author="${author}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>
    </changeSet>
<#if indexes?? >
<#list indexes as table >

<#assign step++ >
    <changeSet id="${step?string["0000"]}" author="${author}" dbms="${driverName}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>

        <createIndex
            indexName="${table.indexName}"
<#if table.schema?? >
            schemaName="${table.schema}"
</#if>
            tableName="${table.name}"
            unique="${table.isUnique?c}"
        >

<#list table.columns?split(",") as column>
            <column name="${column}"/>
</#list>

        </createIndex>

        <rollback>
            <dropIndex
<#if table.schema?? >
                schemaName="${table.schema}"
</#if>
                tableName="${table.name}"
                indexName="${table.indexName}"
            />
        </rollback>

    </changeSet>
</#list>
</#if>

</databaseChangeLog>
