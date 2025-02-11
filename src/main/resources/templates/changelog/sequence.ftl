<#if sequence?? >
<#include "/common/xml-changeset-top.ftl">
        <createSequence
<#if sequence.schema?? && addSchema?? && addSchema == true >
            schemaName="<#if keepNames?? && keepNames == true>${sequence.realSchema}<#else>${sequence.newSchema}</#if>"
</#if>
            sequenceName="<#if keepNames?? && keepNames == true>${sequence.realName}<#else>${sequence.newName}</#if>"
            startValue="${sequence.startValue!1}"
            incrementBy="${sequence.incrementBy!1}"
<#if sequence.type?? && sequence.type?has_content>
            dataType="${sequence.type}"
</#if>
            cycle="${sequence.cycle!?c}"
        />

        <rollback>
            <dropSequence
<#if sequence.schema?? && addSchema?? && addSchema == true >
                schemaName="<#if keepNames?? && keepNames == true>${sequence.realSchema}<#else>${sequence.newSchema}</#if>"
</#if>
                sequenceName="<#if keepNames?? && keepNames == true>${sequence.realName}<#else>${sequence.newName}</#if>"
            />
        </rollback>
<#include "/common/xml-changeset-bottom.ftl">
</#if>

