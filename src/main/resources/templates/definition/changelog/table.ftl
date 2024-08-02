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

    <!-- Table definitions -->

    <changeSet id="${step?string["0000"]}" author="${author}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>
    </changeSet>
<#if tables?? >
<#list tables as table >

<#assign step++ >
    <changeSet id="${step?string["0000"]}" author="${author}" <#if addDbms?? && addDbms == true>dbms="${driverName}" </#if>runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>

        <createTable tableName="${table.name}"<#if table.schema?? && addSchema?? && addSchema == true > schemaName="${table.schema}"</#if><#if table.remarks?? > remarks="${table.remarks}"</#if>>
<#if table.columns?? >
<#list table.columns as column >
            <column name="${column.name}" type="<#if column.propertyType??>${r"${"}${column.propertyType?lower_case}${r"}"}<#else>${column.type}<#if typeUtil.isString(column.type) >(${column.length})</#if></#if>"<#if column.remarks?? > remarks="${column.remarks}"</#if> <#if addNullable?? && addNullable == true><#else>/</#if>>
<#if column.nullable?? && addNullable?? && addNullable == true>
                <constraints nullable="${column.nullable?c}"/>
            </column>
</#if>
</#list>
</#if>
        </createTable>

        <rollback>
            <dropTable tableName="${table.name}"<#if table.schema?? && addSchema?? && addSchema == true > schemaName="${table.schema}"</#if>/>
        </rollback>

    </changeSet>
</#list>
</#if>

</databaseChangeLog>
