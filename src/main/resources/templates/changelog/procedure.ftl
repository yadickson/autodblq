<#if procedure?? >
<#include "/common/xml-changeset-top.ftl">
        <sqlFile
            encoding="${encode}"
            path="../${proceduresDirectory}/<#if keepNames?? && keepNames == true>${procedure.realName}<#else>${procedure.newName}</#if>.sql"
            relativeToChangelogFile="true"
            splitStatements="false"
            stripComments="true"
        />

        <rollback>
            <sql><![CDATA[ DROP PROCEDURE <#if procedure.schema?? && addSchema?? && addSchema == true>"<#if keepNames?? && keepNames == true>${procedure.realSchema}<#else>${procedure.newSchema}</#if>".</#if>"<#if keepNames?? && keepNames == true>${procedure.realName}<#else>${procedure.newName}</#if>" ]]></sql>
        </rollback>
<#include "/common/xml-changeset-bottom.ftl">
</#if>

