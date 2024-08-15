<#include "/common/xml-header.ftl">
<#include "/common/xml-top.ftl">
<#include "/common/xml-step-init.ftl">
<#include "/common/xml-file-tag.ftl">
<#if views?? && views?has_content>
<#list views as view >
<#include "/common/xml-step-next.ftl">
<#include "/changelog/view.ftl">
</#list>
</#if>
<#include "/common/xml-bottom.ftl">