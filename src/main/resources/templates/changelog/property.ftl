<#assign elements = properties[driver] >
<#if elements?? && elements?has_content >
<#list elements as element >
    <property name="${element.name?lower_case}" value="${element.value?lower_case}" dbms="${driver?lower_case}" />
</#list>

</#if>
