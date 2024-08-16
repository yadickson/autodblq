<#include "/common/xml-header.ftl">
<#include "/common/xml-top.ftl">
<#include "/common/xml-step-init.ftl">
<#include "/common/xml-file-tag.ftl">
<#if procedures?? && procedures?has_content>
<#list procedures as procedure >
<#include "/common/xml-step-next.ftl">
<#include "/changelog/procedure.ftl">
</#list>
</#if>
<#include "/common/xml-bottom.ftl">