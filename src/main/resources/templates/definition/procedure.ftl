-- Procedure: <#if keepNames?? && keepNames == true>${procedure.realName}<#else>${procedure.newName}</#if>

-- @GENERATOR.NAME@
-- @GENERATOR.VERSION@
<#if dbversion?? && addDbVersion?? && addDbVersion == true >
-- ${dbversion}
</#if>

<#if procedure.realContent?? >
<#if keepNames?? && keepNames == true>${procedure.realContent}<#else>${procedure.newContent}</#if>
</#if>
