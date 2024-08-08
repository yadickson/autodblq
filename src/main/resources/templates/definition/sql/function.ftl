-- Function: <#if keepNames?? && keepNames == true>${func.realName}<#else>${func.newName}</#if>

-- @GENERATOR.NAME@
-- @GENERATOR.VERSION@
<#if dbversion?? && addDbVersion?? && addDbVersion == true >
-- ${dbversion}
</#if>

<#if function.realContent?? >
<#if keepNames?? && keepNames == true>${function.realContent}<#else>${function.newContent}</#if>
</#if>
