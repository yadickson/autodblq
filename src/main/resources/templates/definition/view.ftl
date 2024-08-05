-- View: ${view.newName}

-- @GENERATOR.NAME@
-- @GENERATOR.VERSION@
<#if dbversion?? && addDbVersion?? && addDbVersion == true >
-- ${dbversion}
</#if>

<#if view.content?? >
${view.content}
</#if>
