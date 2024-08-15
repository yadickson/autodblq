<#if constraint?? >
<#include "/common/xml-changeset-top.ftl">
        <addPrimaryKey
            constraintName="<#if keepNames?? && keepNames == true>${constraint.realName}<#else>${constraint.newName}</#if>"
<#if table.schema?? && addSchema?? && addSchema == true >
            schemaName="<#if keepNames?? && keepNames == true>${table.realSchema}<#else>${table.newSchema}</#if>"
</#if>
            tableName="<#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if>"
            columnNames="${constraint.columns}"
        />

        <rollback>
            <dropPrimaryKey
<#if table.schema?? && addSchema?? && addSchema == true >
                schemaName="<#if keepNames?? && keepNames == true>${table.realSchema}<#else>${table.newSchema}</#if>"
</#if>
                tableName="<#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if>"
                constraintName="<#if keepNames?? && keepNames == true>${constraint.realName}<#else>${constraint.newName}</#if>"
            />
        </rollback>
<#include "/common/xml-changeset-bottom.ftl">
</#if>

