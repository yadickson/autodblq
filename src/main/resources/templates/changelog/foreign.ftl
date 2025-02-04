<#if constraint?? >
<#include "/common/xml-changeset-top.ftl">
        <addForeignKeyConstraint
            constraintName="<#if keepNames?? && keepNames == true>${constraint.realName}<#else>${constraint.newName}</#if>"
<#if table.schema?? && addSchema?? && addSchema == true >
            baseTableSchemaName="<#if keepNames?? && keepNames == true>${table.realSchema}<#else>${table.newSchema}</#if>"
</#if>
            baseTableName="<#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if>"
            baseColumnNames="${constraint.columns}"
<#if constraint.referenceSchema?? && addSchema?? && addSchema == true >
            referencedTableSchemaName="${constraint.referenceSchema}"
</#if>
            referencedTableName="${constraint.referenceName}"
            referencedColumnNames="${constraint.referenceColumns}"
            onDelete="<#if constraint.deleteAction?? >${constraint.deleteAction}<#else>NO ACTION</#if>"
            onUpdate="<#if constraint.updateAction?? >${constraint.updateAction}<#else>NO ACTION</#if>"
        />

        <rollback>
            <dropForeignKeyConstraint
<#if table.schema?? && addSchema?? && addSchema == true >
                baseTableSchemaName="<#if keepNames?? && keepNames == true>${table.realSchema}<#else>${table.newSchema}</#if>"
</#if>
                baseTableName="<#if keepNames?? && keepNames == true>${table.realName}<#else>${table.newName}</#if>"
                constraintName="<#if keepNames?? && keepNames == true>${constraint.realName}<#else>${constraint.newName}</#if>"
            />
        </rollback>
<#include "/common/xml-changeset-bottom.ftl">
</#if>

