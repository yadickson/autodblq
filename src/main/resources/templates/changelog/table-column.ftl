<#if column??>
            <column name="<#if keepNames?? && keepNames == true>${column.realName!'-'}<#else>${column.newName!'-'}</#if>" type="<#if column.propertyType??>${r"${"}${column.propertyType?lower_case}${r"}"}<#else>${column.type!'-'}<#if typeUtil?? && typeUtil.isString(column.type!'-') && column.length?? && column.length gt 0 >(${column.length})</#if></#if>"<#if column.remarks?? > remarks="${column.remarks}"</#if><#if addIdentity?? && addIdentity == true && column.identity?? && column.identity == true > autoIncrement="true" startWith="${column.startWith!1}" incrementBy="${column.incrementBy!1}" </#if><#if addNullable?? && addNullable == true><#else>/</#if>>
<#if column.nullable?? && addNullable?? && addNullable == true>
                <constraints nullable="${column.nullable!?c}"/>
            </column>
</#if>
</#if>
