package ${packageName};

// ${classComment}
type ${className} struct {
<#list fieldList as field>
    ${(field.name)!""} ${(field.type)!""} <#if field.comment?? && field.comment != "">//${(field.comment)}</#if>
</#list>
}