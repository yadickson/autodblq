<#include "/common/xml-header.ftl">
<#include "/common/xml-top.ftl">
<#include "/common/xml-step-init.ftl">
<#include "/common/xml-file-tag.ftl">
<#if sequences?? && sequences?has_content>
<#list sequences as sequence >
<#include "/common/xml-step-next.ftl">
<#include "/changelog/sequence.ftl">
</#list>
</#if>
<#include "/common/xml-bottom.ftl">