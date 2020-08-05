-- Function: ${function.fullName}

-- @GENERATOR.NAME@
-- @GENERATOR.VERSION@
<#if dbversion?? >
<!-- ${dbversion} -->
</#if>

<#if function.text?? >
${function.text}
</#if>
