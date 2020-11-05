-- Function: ${function.fullName}

-- @GENERATOR.NAME@
-- @GENERATOR.VERSION@
<#if dbversion?? >
-- ${dbversion}
</#if>

<#if function.content?? >
${function.content}
</#if>
