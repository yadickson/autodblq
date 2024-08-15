<#include "/common/sql-header.ftl">
-- Procedure: <#if keepNames?? && keepNames == true>${procedure.realName}<#else>${procedure.newName}</#if>

<#if procedure.realContent?? >
<#if keepNames?? && keepNames == true>${procedure.realContent}<#else>${procedure.newContent}</#if>
</#if>
