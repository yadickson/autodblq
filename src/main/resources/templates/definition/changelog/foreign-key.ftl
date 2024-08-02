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

    <!-- Add foreing keys -->

    <changeSet id="${step?string["0000"]}" author="${author}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>
    </changeSet>

<#if foreignKeys?? >
<#list foreignKeys as table >

<#assign step++ >
    <changeSet id="${step?string["0000"]}" author="${author}" <#if addDbms?? && addDbms == true>dbms="${driverName}" </#if>runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>

        <addForeignKeyConstraint
            constraintName="${table.constraintName}"
<#if table.schema?? && addSchema?? && addSchema == true >
            baseTableSchemaName="${table.schema}"
</#if>
            baseTableName="${table.name}"
            baseColumnNames="${table.columnNames}"
<#if table.referenceSchema?? >
            referencedTableSchemaName="${table.referenceSchema}"
</#if>
            referencedTableName="${table.referenceName}" 
            referencedColumnNames="${table.referenceColumnNames}"
            onDelete="NO ACTION" 
            onUpdate="NO ACTION"
        />

        <rollback>
            <dropForeignKeyConstraint
<#if table.schema?? && addSchema?? && addSchema == true >
                baseTableSchemaName="${table.schema}"
</#if>
                baseTableName="${table.name}"
                constraintName="${table.constraintName}"
            />
        </rollback>

    </changeSet>
</#list>
</#if>

</databaseChangeLog>
