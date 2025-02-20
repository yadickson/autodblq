<#if table??>
<#if tables?? && tables?has_content>
<#list tables?chunk(100) as row>
<#if table.columns?? && table.columns?has_content>
insert into <#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if>
  (<#list table.columns as column ><#if keepNames?? && keepNames == true>${column.realName}<#else>${column.newName}</#if><#sep>, </#sep></#list>)
values
<#list row as ctable >
  (<#list ctable.columns as column ><#if keepTypes?? && keepTypes == false && column.propertyType??>${r"${"}${column.propertyType?lower_case}${r"}"}<#else><#if column.defaultValue?? && column.defaultValue?has_content >${column.defaultValue}<#else>NULL</#if></#if><#sep>, </#sep></#list>)<#sep>,</#sep><#if !(ctable?has_next)>;</#if>
</#list>
</#if></#list>
</#if>
</#if>