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

    <!-- Add foreing keys -->

    <changeSet id="${step?string["0000"]}" author="${author}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>
    </changeSet>

<#if tables?? >
<#list tables as table >
<#if table.fkFields?? >
<#list table.fkFields as fk >
<#assign step++ >
    <changeSet id="${step?string["0000"]}" author="${author}" dbms="${driverName}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>

        <addForeignKeyConstraint
            constraintName="${fk.name}"
<#if table.schema?? >
            baseTableSchemaName="${table.schema}"
</#if>
            baseTableName="${table.name}"
            baseColumnNames="${fk.columns}"
<#if fk.tschema?? >
            referencedTableSchemaName="${fk.tschema}"
</#if>
            referencedTableName="${fk.tname}" 
            referencedColumnNames="${fk.tcolumns}"
            onDelete="NO ACTION" 
            onUpdate="NO ACTION"
        />

        <rollback>
            <dropForeignKeyConstraint
<#if table.schema?? >
                baseTableSchemaName="${table.schema}"
</#if>
                baseTableName="${table.name}"
                constraintName="${fk.name}"
            />
        </rollback>

    </changeSet>

</#list>
</#if>
</#list>
</#if>
</databaseChangeLog>
