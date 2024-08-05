${csvComment} Table: ${table.newName}
${csvComment}
${csvComment} @GENERATOR.NAME@
${csvComment} @GENERATOR.VERSION@
<#if dbversion?? && addDbVersion?? && addDbVersion == true >
${csvComment} ${dbversion}
</#if>
${csvComment}
<#if table?? && table.columns?? >
<#list table.columns as column >${csvQuotchar}${column.name}${csvQuotchar}<#sep>${csvSeparator}</#sep></#list>
</#if>
