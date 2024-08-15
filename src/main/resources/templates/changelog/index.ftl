<#if constraint?? >
<#include "/common/xml-changeset-top.ftl">
        <createIndex
            indexName="<#if keepNames?? && keepNames == true>${constraint.realName}<#else>${constraint.newName}</#if>"
<#if table.schema?? && addSchema?? && addSchema == true>
            schemaName="<#if keepNames?? && keepNames == true>${table.realSchema}<#else>${table.newSchema}</#if>"
</#if>
            tableName="<#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if>"
            unique="${constraint.isUnique?c}"
        >
<#if constraint.columns?? >
<#list constraint.columns?split(",") as column>
            <column name="${column}"/>
</#list>
</#if>

        </createIndex>

        <rollback>
            <dropIndex
<#if table.schema?? && addSchema?? && addSchema == true >
                schemaName="<#if keepNames?? && keepNames == true>${table.realSchema}<#else>${table.newSchema}</#if>"
</#if>
                tableName="<#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if>"
                indexName="<#if keepNames?? && keepNames == true>${constraint.realName}<#else>${constraint.newName}</#if>"
            />
        </rollback>
<#include "/common/xml-changeset-bottom.ftl">
</#if>

