-- Procedure: ${procedure.fullName}

-- @GENERATOR.NAME@
-- @GENERATOR.VERSION@
<#if dbversion?? >
<!-- ${dbversion} -->
</#if>

<#if procedure.text?? >
${procedure.text}
</#if>
