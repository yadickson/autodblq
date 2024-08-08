<?xml version="1.0" encoding="UTF-8"?>

<!-- @GENERATOR.NAME@ -->
<!-- @GENERATOR.VERSION@ -->
<#if dbversion?? && addDbVersion?? && addDbVersion == true >
<!-- ${dbversion} -->
</#if>

<databaseChangeLog
    xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
<#if lqpro?? && lqpro >
    xmlns:pro="http://www.liquibase.org/xml/ns/pro"
</#if>
    xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
    xsi:schemaLocation="
        http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd
<#if lqpro?? && lqpro >
        http://www.liquibase.org/xml/ns/pro http://www.liquibase.org/xml/ns/pro/liquibase-pro-latest.xsd
</#if>
        http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd">
<#assign step = 1>

    <!-- Add check -->

    <changeSet id="${step?string["0000"]}" author="${author}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>
    </changeSet>
<#if checks?? >
<#list checks as table >
<#if table.constraints?? && table.constraints?has_content >
<#list table.constraints as constraint >

<#assign step++ >
<#assign columnNames = constraint.realColumnNames?split(" ") >
<#if keepNames?? && keepNames == true>
<#assign newColumnNames = constraint.realColumnNames?split(" ") >
<#else>
<#assign newColumnNames = constraint.newColumnNames?split(" ") >
</#if>
<#assign constraintValue = "${constraint.value}" >
<#list 0..columnNames?size-1 as index>
<#assign columnName = "${columnNames[index]}" >
<#assign newColumnName = "${newColumnNames[index]}" >
<#assign constraintValue = "${constraintValue?replace(columnName, newColumnName)}" >
</#list>
    <changeSet id="${step?string["0000"]}" author="${author}" <#if addDbms?? && addDbms == true>dbms="${driverName}" </#if>runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>

        <sql>
            <![CDATA[ ALTER TABLE <#if table.schema?? && addSchema?? && addSchema == true ><#if keepNames?? && keepNames == true>${table.realSchema}<#else>${table.newSchema}</#if>.</#if><#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if> ADD CONSTRAINT <#if keepNames?? && keepNames == true>${constraint.realName}<#else>${constraint.newName}</#if> CHECK (${constraintValue}); ]]>
        </sql>

        <rollback>
            <![CDATA[ ALTER TABLE <#if table.schema?? && addSchema?? && addSchema == true ><#if keepNames?? && keepNames == true>${table.realSchema}<#else>${table.newSchema}</#if>.</#if><#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if> DROP CONSTRAINT <#if keepNames?? && keepNames == true>${constraint.realName}<#else>${constraint.newName}</#if>; ]]>
        </rollback>

    </changeSet>
</#list>
</#if>
</#list>
</#if>

</databaseChangeLog>
