-- @GENERATOR.NAME@
-- @GENERATOR.VERSION@
<#if dbversion?? && addDbVersion?? && addDbVersion == true >
-- ${dbversion}
</#if>
-- Author: ${author}
-- Name: <#if keepNames?? && keepNames == true>${view.realName}<#else>${view.newName}</#if>

<#if view.realContent?? >
<#if keepNames?? && keepNames == true>${view.realContent}<#else>${view.newContent}</#if>
</#if>
