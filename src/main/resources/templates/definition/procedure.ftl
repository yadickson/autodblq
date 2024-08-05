-- Procedure: ${procedure.newName}

-- @GENERATOR.NAME@
-- @GENERATOR.VERSION@
<#if dbversion?? && addDbVersion?? && addDbVersion == true >
-- ${dbversion}
</#if>

<#if procedure.content?? >
${procedure.content}
</#if>
