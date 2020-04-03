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

    <!-- Add unique -->

    <changeSet id="${step?string["00"]}" author="${author}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["00"]}"/>
    </changeSet>

<#list tables as table >
<#if table.unqFields?? >
<#list table.unqFields as unq >
<#assign step++ >
    <changeSet id="${step?string["00"]}" author="${author}" dbms="${driverName}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["00"]}"/>

        <addUniqueConstraint
            constraintName="${unq.name}"
<#if table.schema?? >
            schemaName="${table.schema}"
</#if>
            tableName="${table.name}"
            columnNames="${unq.columns}"
        />

        <rollback>
            <dropUniqueConstraint
<#if table.schema?? >
                schemaName="${table.schema}"
</#if>
                tableName="${table.name}"
                constraintName="${unq.name}"
            />
        </rollback>

    </changeSet>

</#list>
</#if>
</#list>
</databaseChangeLog>
