<#if table?? >
<#include "/common/xml-changeset-top.ftl">
        <sqlFile
            encoding="${encode}"
            path="../${datasetsDirectory}/<#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if>.sql"
            relativeToChangelogFile="true"
            splitStatements="false"
            stripComments="true"
        />

        <rollback>
            <delete tableName="<#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if>"<#if table.schema?? && addSchema?? && addSchema == true > schemaName="<#if keepNames?? && keepNames == true>${table.realSchema}<#else>${table.newSchema}</#if>"</#if>/>
        </rollback>
<#include "/common/xml-changeset-bottom.ftl">
</#if>

