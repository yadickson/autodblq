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

    <!-- Add default values -->

    <changeSet id="${step?string["0000"]}" author="${author}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>
    </changeSet>
<#if defaults?? >
<#list defaults as table >
<#if table.constraints?? && table.constraints?has_content >
<#list table.constraints as constraint >

<#assign step++ >
    <changeSet id="${step?string["0000"]}" author="${author}" <#if addDbms?? && addDbms == true>dbms="${driverName}" </#if>runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>

        <addDefaultValue
<#if table.newSchema?? && addSchema?? && addSchema == true >
            schemaName="${table.newSchema}"
</#if>
            tableName="${table.newName}"
            columnName="${constraint.name}"
            defaultValueNumeric="<#if constraint.propertyType??>${r"${"}${constraint.propertyType?lower_case}${r"}"}<#else>${constraint.value}</#if>"
        />

        <rollback>
            <dropDefaultValue
<#if table.newSchema?? && addSchema?? && addSchema == true >
                schemaName="${table.newSchema}"
</#if>
                tableName="${table.newName}"
                columnName="${constraint.name}"
            />
        </rollback>

    </changeSet>
</#list>
</#if>
</#list>
</#if>

</databaseChangeLog>
