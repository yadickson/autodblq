<#if constraint?? >
<#include "/common/xml-changeset-top.ftl">
        <addDefaultValue
<#if table.schema?? && addSchema?? && addSchema == true >
            schemaName="<#if keepNames?? && keepNames == true>${table.realSchema}<#else>${table.newSchema}</#if>"
</#if>
            tableName="<#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if>"
            columnName="<#if keepNames?? && keepNames == true>${constraint.realName}<#else>${constraint.newName}</#if>"
            defaultValueNumeric="<#if constraint.propertyType??>${r"${"}${constraint.propertyType?lower_case}${r"}"}<#else>${constraint.value}</#if>"
        />

        <rollback>
            <dropDefaultValue
<#if table.schema?? && addSchema?? && addSchema == true >
                schemaName="<#if keepNames?? && keepNames == true>${table.realSchema}<#else>${table.newSchema}</#if>"
</#if>
                tableName="<#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if>"
                columnName="<#if keepNames?? && keepNames == true>${constraint.realName}<#else>${constraint.newName}</#if>"
            />
        </rollback>
<#include "/common/xml-changeset-bottom.ftl">
</#if>

