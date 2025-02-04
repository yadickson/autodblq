<#if view?? >
<#include "/common/xml-changeset-top.ftl">
        <sqlFile
            encoding="${encode}"
            path="../${viewsDirectory}/<#if keepNames?? && keepNames == true>${view.realName}<#else>${view.newName}</#if>.sql"
            relativeToChangelogFile="true"
            splitStatements="false"
            stripComments="true"
        />

        <rollback>
            <sql><![CDATA[ DROP VIEW <#if view.schema?? && addSchema?? && addSchema == true>"<#if keepNames?? && keepNames == true>${view.realSchema}<#else>${view.newSchema}</#if>".</#if>"<#if keepNames?? && keepNames == true>${view.realName}<#else>${view.newName}</#if>" ]]></sql>
        </rollback>
<#include "/common/xml-changeset-bottom.ftl">
</#if>

