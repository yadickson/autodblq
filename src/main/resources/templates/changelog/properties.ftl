<#include "/common/xml-header.ftl">
<#include "/common/xml-top.ftl">
<#include "/common/xml-step-init.ftl">
<#if properties??>
<#list properties?keys as driver >
<#include "/common/xml-step-next.ftl">
<#include "/changelog/property.ftl">
</#list>
</#if>
<#include "/common/xml-file-tag.ftl">
<#include "/common/xml-bottom.ftl">