<#if table?? >
<#include "/common/xml-changeset-top.ftl">
        <createTable tableName="<#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if>"<#if table.schema?? && addSchema?? && addSchema == true > schemaName="<#if keepNames?? && keepNames == true>${table.realSchema}<#else>${table.newSchema}</#if>"</#if><#if table.remarks?? > remarks="${table.remarks}"</#if>>
<#if table.columns?? && table.columns?has_content >
<#list table.columns as column >
<#include "/changelog/table-column.ftl">
</#list>
</#if>
        </createTable>

        <rollback>
            <dropTable tableName="<#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if>"<#if table.schema?? && addSchema?? && addSchema == true > schemaName="<#if keepNames?? && keepNames == true>${table.realSchema}<#else>${table.newSchema}</#if>"</#if>/>
        </rollback>
<#include "/common/xml-changeset-bottom.ftl">
</#if>

