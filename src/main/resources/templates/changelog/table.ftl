<#if table?? >
<#include "/common/xml-changeset-top.ftl">
        <createTable tableName="<#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if>"<#if table.schema?? && addSchema?? && addSchema == true > schemaName="<#if keepNames?? && keepNames == true>${table.realSchema}<#else>${table.newSchema}</#if>"</#if><#if table.remarks?? > remarks="${table.remarks}"</#if>>
<#if table.columns?? && table.columns?has_content >
<#list table.columns as column >
            <column name="<#if keepNames?? && keepNames == true>${column.realName}<#else>${column.newName}</#if>" type="<#if column.propertyType??>${r"${"}${column.propertyType?lower_case}${r"}"}<#else>${column.type}<#if typeUtil.isString(column.type) >(${column.length})</#if></#if>"<#if column.remarks?? > remarks="${column.remarks}"</#if><#if addIdentity?? && addIdentity == true && column.identity?? && column.identity == true > autoIncrement="true" startWith="${column.startWith}" incrementBy="${column.incrementBy}" </#if><#if addNullable?? && addNullable == true><#else>/</#if>>
<#if column.nullable?? && addNullable?? && addNullable == true>
                <constraints nullable="${column.nullable?c}"/>
            </column>
</#if>
</#list>
</#if>
        </createTable>

        <rollback>
            <dropTable tableName="<#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if>"<#if table.schema?? && addSchema?? && addSchema == true > schemaName="<#if keepNames?? && keepNames == true>${table.realSchema}<#else>${table.newSchema}</#if>"</#if>/>
        </rollback>
<#include "/common/xml-changeset-bottom.ftl">
</#if>

