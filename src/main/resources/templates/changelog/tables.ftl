<#include "/common/xml-header.ftl">
<#include "/common/xml-top.ftl">
<#include "/common/xml-step-init.ftl">
<#include "/common/xml-file-tag.ftl">
<#if tables?? && tables?has_content>
<#list tables as table >
<#include "/common/xml-step-next.ftl">
<#include "/changelog/table.ftl">
</#list>
</#if>
<#include "/common/xml-bottom.ftl">