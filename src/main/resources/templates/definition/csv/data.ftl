<#if table?? && table.fields?? >
<#list table.fields as column >${quotchar}${column.name}${quotchar}<#sep>${separator}</#sep></#list>
</#if>
