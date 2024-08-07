${csvComment} Table: <#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if>
${csvComment}
${csvComment} @GENERATOR.NAME@
${csvComment} @GENERATOR.VERSION@
<#if dbversion?? && addDbVersion?? && addDbVersion == true >
${csvComment} ${dbversion}
</#if>
${csvComment}
<#if table?? && table.columns?? >
<#list table.columns as column >${csvQuotchar}<#if keepNames?? && keepNames == true>${column.realName}<#else>${column.newName}</#if>${csvQuotchar}<#sep>${csvSeparator}</#sep></#list>
</#if>
