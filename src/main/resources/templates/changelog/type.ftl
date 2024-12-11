<#if type?? >
<#include "/common/xml-changeset-top.ftl">
        <sql>
            <![CDATA[ CREATE TYPE <#if type.schema?? && addSchema?? && addSchema == true ><#if keepNames?? && keepNames == true>${type.realSchema}<#else>${type.newSchema}</#if>.</#if><#if keepNames?? && keepNames == true>${type.realName}<#else>${type.newName}</#if> AS ${type.type} (<#if keepNames?? && keepNames == true>${type.realContent}<#else>${type.newContent}</#if>); ]]>
        </sql>

        <rollback>
            <![CDATA[ DROP TYPE <#if type.schema?? && addSchema?? && addSchema == true ><#if keepNames?? && keepNames == true>${type.realSchema}<#else>${type.newSchema}</#if>.</#if><#if keepNames?? && keepNames == true>${type.realName}<#else>${type.newName}</#if>; ]]>
        </rollback>
<#include "/common/xml-changeset-bottom.ftl">
</#if>
