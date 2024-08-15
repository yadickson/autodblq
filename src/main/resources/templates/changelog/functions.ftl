<#include "/common/xml-header.ftl">
<#include "/common/xml-top.ftl">
<#include "/common/xml-step-init.ftl">
<#include "/common/xml-file-tag.ftl">
<#if functions?? && functions?has_content>
<#list functions as func >
<#include "/common/xml-step-next.ftl">
<#include "/changelog/function.ftl">
</#list>
</#if>
<#include "/common/xml-bottom.ftl">