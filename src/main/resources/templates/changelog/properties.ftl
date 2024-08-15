<#include "/common/xml-header.ftl">
<#include "/common/xml-top.ftl">
<#if properties?? >
<#assign keys = properties?keys >
<#list keys as driver>
<#assign elements = properties[driver] >
<#if elements?? && elements?has_content >

<#list elements as element >
    <property name="${element.name?lower_case}" value="${element.value?lower_case}" dbms="${driver?lower_case}" />
</#list>
</#if>
</#list>
</#if>
<#assign step = 1>

    <changeSet id="${step?string["0000"]}" author="${author}" runOnChange="false">
        <ext:tagDatabase tag="${version}-${file?string["00"]}.${step?string["0000"]}"/>
    </changeSet>
<#include "/common/xml-bottom.ftl">