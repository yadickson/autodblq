<#if table?? && table.columns?? >
<#list table.columns as column >${quotchar}${column.name}${quotchar}<#sep>${separator}</#sep></#list>
</#if>
