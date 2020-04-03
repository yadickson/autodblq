<#if tables?? && tables?size != 0>
<#assign lq_version = '1.0.0'>
<#assign file = 1>
<#assign step = 1>
    <!-- Table definitions -->

    <changeSet id="${step}" author="yadickson.soto" runOnChange="false">
        <ext:tagDatabase tag="${lq_version}-${file?string["00"]}.${step}"/>
    </changeSet>

<#list tables as table >
<#assign step++ >
    <changeSet id="${step}" author="yadickson.soto" dbms="${driverName}" runOnChange="false">
        <ext:tagDatabase tag="${lq_version}-${file?string["00"]}.${step}"/>

        <createTable tableName="${table.name}"<#if table.schema?? > schemaName="${table.schema}"</#if><#if table.remarks?? > remarks="${table.remarks}"</#if>>
<#if table.fields?? >
<#list table.fields as column >
            <column name="${column.name}" type="${column.type}<#if column.isString >(${column.length})</#if>"<#if column.remarks?? > remarks="${column.remarks}"</#if>>
                <constraints nullable="<#if column.nullable?? >${column.nullable?c}<#else>false</#if>"/>
            </column>
</#list>
</#if>
        </createTable>

        <rollback>
            <dropTable tableName="${table.name}"<#if table.schema?? > schemaName="${table.schema}"</#if>/>
        </rollback>

    </changeSet>

</#list>

<!-- ********************************************** -->

<#assign step = 1>
<#assign file++ >
    <!-- Add default values -->

    <changeSet id="${step}" author="yadickson.soto" runOnChange="false">
        <ext:tagDatabase tag="${lq_version}-${file?string["00"]}.${step}"/>
    </changeSet>

<#list tables as table >
<#if table.fields?? >
<#list table.fields as column >
<#if column.defaultValue?? >
<#if column.isNumeric || column.isString >
<#assign step++ >
    <changeSet id="${step}" author="yadickson.soto" dbms="${driverName}" runOnChange="false">
        <ext:tagDatabase tag="${lq_version}-${file?string["00"]}.${step}"/>

        <addDefaultValue
<#if table.schema?? >
            schemaName="${table.schema}"
</#if>
            tableName="${table.name}"
            columnName="${column.name}"
<#if column.isNumeric >
            defaultValueNumeric="${column.defaultValue}"
<#else>
            defaultValue="${column.defaultValue}"
</#if>
        />

        <rollback>
            <dropDefaultValue
<#if table.schema?? >
                schemaName="${table.schema}"
</#if>
                tableName="${table.name}"
                columnName="${column.name}"
            />
        </rollback>

    </changeSet>

</#if>
</#if>
</#list>
</#if>
</#list>

<!-- ********************************************** -->

<#assign step = 1>
<#assign file++ >
    <!-- Creacion de autoincrementales -->

    <changeSet id="${step}" author="yadickson.soto" runOnChange="false">
        <ext:tagDatabase tag="${lq_version}-${file?string["00"]}.${step}"/>
    </changeSet>

<#list tables as table >
<#if table.fields?? >
<#list table.fields as column >
<#if column.identity?? && column.identity >
<#assign step++ >
    <changeSet id="${step}" author="yadickson.soto" dbms="${driverName}" runOnChange="false">
        <ext:tagDatabase tag="${lq_version}-${file?string["00"]}.${step}"/>

        <addAutoIncrement
<#if table.schema?? >
            schemaName="${table.schema}"
</#if>
            tableName="${table.name}"
            columnName="${column.name}"
            incrementBy="1"
            startWith="1"
        />

        <rollback>
        </rollback>

    </changeSet>

</#if>
</#list>
</#if>
</#list>

<!-- ********************************************** -->

<#assign step = 1>
<#assign file++ >
    <!-- Creacion de indices -->

    <changeSet id="${step}" author="yadickson.soto" runOnChange="false">
        <ext:tagDatabase tag="${lq_version}-${file?string["00"]}.${step}"/>
    </changeSet>

<#list tables as table >
<#if table.fkFields?? >
<#list table.fkFields as fk >
<#assign step++ >
    <changeSet id="${step}" author="yadickson.soto" dbms="${driverName}" runOnChange="false">
        <ext:tagDatabase tag="${lq_version}-${file?string["00"]}.${step}"/>

        <createIndex
            indexName="IDX_${fk.name}" 
<#if table.schema?? >
            schemaName="${table.schema}"
</#if>
            tableName="${table.name}"
            unique="false"
        >

<#list fk.columns?split(",") as column >
            <column name="${column}"/>
</#list>

        </createIndex>

        <rollback>
            <dropIndex 
<#if table.schema?? >
                schemaName="${table.schema}"
</#if>
                tableName="${table.name}"
                indexName="IDX_${fk.name}"
            />
        </rollback>

    </changeSet>

</#list>
</#if>
</#list>

<!-- ********************************************** -->

<#assign step = 1>
<#assign file++ >
    <!-- Add primary keys -->

    <changeSet id="${step}" author="yadickson.soto" runOnChange="false">
        <ext:tagDatabase tag="${lq_version}-${file?string["00"]}.${step}"/>
    </changeSet>

<#list tables as table >
<#if table.pkFields?? >
<#list table.pkFields as pk >
<#assign step++ >
    <changeSet id="${step}" author="yadickson.soto" dbms="${driverName}" runOnChange="false">
        <ext:tagDatabase tag="${lq_version}-${file?string["00"]}.${step}"/>

        <addPrimaryKey
            constraintName="${pk.name}" 
<#if table.schema?? >
            schemaName="${table.schema}"
</#if>
            tableName="${table.name}"
            columnNames="${pk.columns}"
        />

        <rollback>
            <dropPrimaryKey 
<#if table.schema?? >
                schemaName="${table.schema}"
</#if>
                tableName="${table.name}"
                constraintName="pk.name"
            />
        </rollback>

    </changeSet>

</#list>
</#if>
</#list>
</#if>
