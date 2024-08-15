<#include "/common/xml-header.ftl">
<#include "/common/xml-top.ftl">
<#include "/common/xml-step-init.ftl">
<#include "/common/xml-file-tag.ftl">
<#if uniques?? && uniques?has_content>
<#list uniques as table >
<#if table.constraints?? && table.constraints?has_content >
<#list table.constraints as constraint >
<#include "/common/xml-step-next.ftl">
<#include "/changelog/unique.ftl">
</#list>
</#if>
</#list>
</#if>
<#include "/common/xml-bottom.ftl">