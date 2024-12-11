<#include "/common/xml-header.ftl">
<#include "/common/xml-top.ftl">
<#include "/common/xml-step-init.ftl">
<#include "/common/xml-file-tag.ftl">
<#if types?? && types?has_content>
<#list types as type >
<#include "/common/xml-step-next.ftl">
<#include "/changelog/type.ftl">
</#list>
</#if>
<#include "/common/xml-bottom.ftl">