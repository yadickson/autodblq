-- View: ${view.fullName}

-- @GENERATOR.NAME@
-- @GENERATOR.VERSION@
<#if dbversion?? >
-- ${dbversion}
</#if>

<#if view.content?? >
${view.content}
</#if>
