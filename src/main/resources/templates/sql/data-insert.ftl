<#if tables?? && tables?has_content>
<#list tables as table >
<#if table?? && table.columns?? && table.columns?has_content>
insert into <#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if> (<#list table.columns as column ><#if keepNames?? && keepNames == true>${column.realName}<#else>${column.newName}</#if><#sep>, </#sep></#list>) values (<#list table.columns as column ><#if column.propertyType??>${r"${"}${column.propertyType?lower_case}${r"}"}<#else><#if column.defaultValue?? && column.defaultValue?has_content ><#if typeUtil.isString(column.defaultType) >'</#if>${column.defaultValue?replace("'", "''")}<#if typeUtil.isString(column.defaultType) >'</#if><#else>NULL</#if></#if><#sep>, </#sep></#list>);
</#if>
</#list>
</#if>