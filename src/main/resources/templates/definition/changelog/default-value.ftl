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

    <!-- Add default values -->

    <changeSet id="${step?string["0000"]}" author="${author}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>
    </changeSet>
<#if defaults?? >
<#list defaults as table >
<#if typeUtil.isNumeric(table.type) || typeUtil.isString(table.type) || (typeUtil.isDate(table.type) && table.value != 'DEFAULT') >

<#assign step++ >
    <changeSet id="${step?string["0000"]}" author="${author}" dbms="${driverName}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>

        <addDefaultValue
<#if table.schema?? >
            schemaName="${table.schema}"
</#if>
            tableName="${table.name}"
            columnName="${table.columnName}"
            defaultValue<#if typeUtil.isNumeric(table.type) >Numeric<#elseif typeUtil.isDate(table.type) >Computed</#if>="${table.value}"
        />

        <rollback>
            <dropDefaultValue
<#if table.schema?? >
                schemaName="${table.schema}"
</#if>
                tableName="${table.name}"
                columnName="${table.columnName}"
            />
        </rollback>

    </changeSet>
</#if>
</#list>
</#if>

</databaseChangeLog>
