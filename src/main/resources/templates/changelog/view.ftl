<#if view?? >
<#include "/common/xml-changeset-top.ftl">
        <createView
            viewName="<#if keepNames?? && keepNames == true>${view.realName}<#else>${view.newName}</#if>"
<#if view.schema?? && addSchema?? && addSchema == true >
            schemaName="<#if keepNames?? && keepNames == true>${view.realSchema}<#else>${view.newSchema}</#if>"
</#if>
            encoding="${encode}"
            replaceIfExists="true"
            fullDefinition="true"
            path="../${viewsDirectory}/<#if keepNames?? && keepNames == true>${view.realName}<#else>${view.newName}</#if>.sql"
            relativeToChangelogFile="true"
        />

        <rollback>
            <dropView viewName="<#if keepNames?? && keepNames == true>${view.realName}<#else>${view.newName}</#if>"<#if view.schema?? && addSchema?? && addSchema == true > schemaName="<#if keepNames?? && keepNames == true>${view.realSchema}<#else>${view.newSchema}</#if>"</#if>/>
        </rollback>
<#include "/common/xml-changeset-bottom.ftl">
</#if>

