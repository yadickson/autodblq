<#if func?? >
<#include "/common/xml-changeset-top.ftl">
        <sqlFile
            encoding="${encode}"
            path="../<#if func.isFunction >${functionsDirectory}<#else>${proceduresDirectory}</#if>/<#if keepNames?? && keepNames == true>${func.realName}<#else>${func.newName}</#if>.sql"
            relativeToChangelogFile="true"
            splitStatements="false"
            stripComments="true"
        />

        <rollback>
            <sql><![CDATA[ DROP <#if func.isFunction >FUNCTION<#else>PROCEDURE</#if> <#if func.schema?? && addSchema?? && addSchema == true>"<#if keepNames?? && keepNames == true>${func.realSchema}<#else>${func.newSchema}</#if>".</#if>"<#if keepNames?? && keepNames == true>${func.realName}<#else>${func.newName}</#if>" ]]></sql>
        </rollback>
<#include "/common/xml-changeset-bottom.ftl">
</#if>

