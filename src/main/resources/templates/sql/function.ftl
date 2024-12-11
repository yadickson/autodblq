<#include "/common/sql-header.ftl">
-- Function: <#if keepNames?? && keepNames == true>${function.realName}<#else>${function.newName}</#if>

create function <#if keepNames?? && keepNames == true>${function.realName}<#else>${function.newName}</#if>(<#list function.parameters as parameter>${r"${pre_"}${parameter.mode}${r"_mode}"}<#if parameter.mode == 'in'></#if>${r"${pre_value}"}<#if keepNames?? && keepNames == true>${parameter.realName}<#else>${parameter.newName}</#if> <#if keepTypes?? && keepTypes == false && parameter.propertyType??>${r"${"}${parameter.propertyType?lower_case}${r"}"}<#else>${parameter.type}</#if>${r"${post_"}${parameter.mode}${r"_mode}"}<#sep>, </#sep></#list>)
returns <#if keepTypes?? && keepTypes == false && function.propertyType??>${r"${"}${function.propertyType?lower_case}${r"}"}<#else>${function.type}</#if>
as ${r"${init_function}"}
<#if function.realContent?? >
<#if keepNames?? && keepNames == true>${function.realContent}<#else>${function.newContent}</#if>
</#if>
${r"${end_function}"}
