<#include "/common/sql-header.ftl">
-- Procedure: <#if keepNames?? && keepNames == true>${procedure.realName}<#else>${procedure.newName}</#if>

create procedure <#if keepNames?? && keepNames == true>${procedure.realName}<#else>${procedure.newName}</#if>(<#list procedure.parameters as parameter>${r"${pre_"}${parameter.mode}${r"_mode}"}<#if parameter.mode == 'in'></#if>${r"${pre_value}"}<#if keepNames?? && keepNames == true>${parameter.realName}<#else>${parameter.newName}</#if> <#if parameter.propertyType??>${r"${"}${parameter.propertyType?lower_case}${r"}"}<#else>${parameter.type}</#if>${r"${post_"}${parameter.mode}${r"_mode}"}<#sep>, </#sep></#list>)
as ${r"${init_function}"}
<#if procedure.realContent?? >
<#if keepNames?? && keepNames == true>${procedure.realContent}<#else>${procedure.newContent}</#if>
</#if>
${r"${end_function}"}
