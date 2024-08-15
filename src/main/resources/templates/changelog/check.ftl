<#if constraint?? >
<#assign columnNames = constraint.realColumnNames?split(" ") >
<#if keepNames?? && keepNames == true>
<#assign newColumnNames = constraint.realColumnNames?split(" ") >
<#else>
<#assign newColumnNames = constraint.newColumnNames?split(" ") >
</#if>
<#assign constraintValue = "${constraint.value}" >
<#list 0..columnNames?size-1 as index>
<#assign columnName = "${columnNames[index]}" >
<#assign newColumnName = "${newColumnNames[index]}" >
<#assign constraintValue = "${constraintValue?replace(columnName, newColumnName)}" >
</#list>
<#include "/common/xml-changeset-top.ftl">
        <sql>
            <![CDATA[ ALTER TABLE <#if table.schema?? && addSchema?? && addSchema == true ><#if keepNames?? && keepNames == true>${table.realSchema}<#else>${table.newSchema}</#if>.</#if><#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if> ADD CONSTRAINT <#if keepNames?? && keepNames == true>${constraint.realName}<#else>${constraint.newName}</#if> CHECK (${constraintValue}); ]]>
        </sql>

        <rollback>
            <![CDATA[ ALTER TABLE <#if table.schema?? && addSchema?? && addSchema == true ><#if keepNames?? && keepNames == true>${table.realSchema}<#else>${table.newSchema}</#if>.</#if><#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if> DROP CONSTRAINT <#if keepNames?? && keepNames == true>${constraint.realName}<#else>${constraint.newName}</#if>; ]]>
        </rollback>
<#include "/common/xml-changeset-bottom.ftl">
</#if>

