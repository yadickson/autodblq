-- Table: <#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if>
--
-- @GENERATOR.NAME@
-- @GENERATOR.VERSION@
<#if dbversion?? && addDbVersion?? && addDbVersion == true >
-- ${dbversion}
</#if>
