<#include "/common/xml-header.ftl">
<#include "/common/xml-top.ftl">
<#include "/common/xml-step-init.ftl">
<#include "/common/xml-file-tag.ftl">
<#if dataTables?? && dataTables?has_content>
<#list dataTables as table >
<#if table?? && table.files?? && table.files?has_content>
<#list table.files as sqlfile >
<#include "/common/xml-step-next.ftl">
<#include "/changelog/dataset.ftl">
</#list>
</#if>
</#list>
</#if>
<#include "/common/xml-bottom.ftl">