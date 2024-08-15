<#include "/common/xml-header.ftl">
<#include "/common/xml-top.ftl">
<#include "/common/xml-step-init.ftl">
<#include "/common/xml-file-tag.ftl">
<#if dataTables?? && dataTables?has_content>
<#list dataTables as table >
<#include "/common/xml-step-next.ftl">
<#include "/changelog/dataset.ftl">
</#list>
</#if>
<#include "/common/xml-bottom.ftl">