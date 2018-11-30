package ${packageName};

import java.sql.Timestamp;

/**
 * ${classComment}
<#if author?? && author != "">
 * @author ${author}
</#if>
 */
public class ${className} {

<#list fieldList as field>
	private ${(field.type)!""} ${(field.name)!""};  <#if field.comment?? && field.comment != "">//${(field.comment)}</#if>
</#list>

<#list fieldList as field>
    <#if field.comment?? && field.comment != "">
    /**
     * ${(field.comment)}
     */
    </#if>
	public ${(field.type)!""} get${(field.name)?cap_first!""}() {
        return ${(field.name)!""};
	}

    <#if field.comment?? && field.comment != "">
    /**
     * ${(field.comment)}
     */
    </#if>
	public void set${(field.name)?cap_first!""}(${(field.type)!""} ${(field.name)!""}) {
        this.${(field.name)!""} = ${(field.name)!""};
	}

</#list>
}