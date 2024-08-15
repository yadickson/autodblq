<#include "/common/xml-header.ftl">
<#include "/common/xml-top.ftl">
<#if files?? && files?has_content >
<#list files as file >
    <include file="${changelogpath}/${file}" relativeToChangelogFile="true"/>
</#list>
</#if>
<#include "/common/xml-bottom.ftl">