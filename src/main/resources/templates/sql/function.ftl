<#include "/common/sql-header.ftl">
-- Function: <#if keepNames?? && keepNames == true>${function.realName}<#else>${function.newName}</#if>

<#if function.realContent?? >
<#if keepNames?? && keepNames == true>${function.realContent}<#else>${function.newContent}</#if>
</#if>
