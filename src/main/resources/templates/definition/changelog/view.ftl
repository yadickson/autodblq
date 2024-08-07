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

    <!-- View definitions -->

    <changeSet id="${step?string["0000"]}" author="${author}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>
    </changeSet>
<#if views?? >
<#list views as view >

<#assign step++ >
    <changeSet id="${step?string["0000"]}" author="${author}" <#if addDbms?? && addDbms == true>dbms="${driverName}" </#if>runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>

        <createView
            viewName="<#if keepNames?? && keepNames == true>${view.realName}<#else>${view.newName}</#if>"
<#if view.schema?? && addSchema?? && addSchema == true >
            schemaName="<#if keepNames?? && keepNames == true>${view.realSchema}<#else>${view.newSchema}</#if>"
</#if>
            encoding="${encode}"
            replaceIfExists="true"
            fullDefinition="true"
            path="../view/<#if keepNames?? && keepNames == true>${view.realName}<#else>${view.newName}</#if>.sql"
            relativeToChangelogFile="true"
        />

        <rollback>
            <dropView viewName="<#if keepNames?? && keepNames == true>${view.realName}<#else>${view.newName}</#if>"<#if view.schema?? && addSchema?? && addSchema == true > schemaName="<#if keepNames?? && keepNames == true>${view.realSchema}<#else>${view.newSchema}</#if>"</#if>/>
        </rollback>

    </changeSet>
</#list>
</#if>

</databaseChangeLog>
