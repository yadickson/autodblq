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

    <!-- Add primary keys -->

    <changeSet id="${step?string["0000"]}" author="${author}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>
    </changeSet>

<#if tables?? >
<#list tables as table >
<#if table.pkFields?? >
<#list table.pkFields as pk >
    <!-- ${table.fullName} : ${pk.name} -->
</#list>
</#if>
</#list>

<#list tables as table >
<#if table.pkFields?? >
<#list table.pkFields as pk >
<#assign step++ >
    <changeSet id="${step?string["0000"]}" author="${author}" dbms="${driverName}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>

        <addPrimaryKey
            constraintName="${pk.name}" 
<#if table.schema?? >
            schemaName="${table.schema}"
</#if>
            tableName="${table.name}"
            columnNames="${pk.columns}"
        />

        <rollback>
            <dropPrimaryKey
<#if table.schema?? >
                schemaName="${table.schema}"
</#if>
                tableName="${table.name}"
                constraintName="${pk.name}"
            />
        </rollback>

    </changeSet>

</#list>
</#if>
</#list>
</#if>
</databaseChangeLog>
