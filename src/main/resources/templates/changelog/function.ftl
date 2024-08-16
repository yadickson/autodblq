<#if function?? >
<#include "/common/xml-changeset-top.ftl">
        <sqlFile
            encoding="${encode}"
            path="../${functionsDirectory}/<#if keepNames?? && keepNames == true>${function.realName}<#else>${function.newName}</#if>.sql"
            relativeToChangelogFile="true"
            splitStatements="false"
            stripComments="true"
        />

        <rollback>
            <sql><![CDATA[ DROP FUNCTION <#if function.schema?? && addSchema?? && addSchema == true>"<#if keepNames?? && keepNames == true>${function.realSchema}<#else>${function.newSchema}</#if>".</#if>"<#if keepNames?? && keepNames == true>${function.realName}<#else>${function.newName}</#if>" ]]></sql>
        </rollback>
<#include "/common/xml-changeset-bottom.ftl">
</#if>

