<#--
	if the label position is top,
	then give the label it's own row in the table
-->
<#assign hasFieldErrors = parameters.name?exists && fieldErrors?exists && fieldErrors[parameters.name]?exists/>
<#if parameters.label?exists>
    <label <#t/>
    <#if parameters.id?exists>
        for="${parameters.id?html}" <#t/>
    </#if>
    <#if hasFieldErrors>
        class="desc error"><#t/>
    <#else>
        class="desc"><#t/>
    </#if>
${parameters.label?html}<#t/>
<#--if parameters.required?default(false)> <span class="req">*</span></#if--></label><#t/>
</#if>
<#if hasFieldErrors>
<#list fieldErrors[parameters.name] as error>
    <span class="fieldError"><img src="<@ww.url includeParams="none" value="/imgs/iconWarning.gif"/>" class="icon" /> ${error?html}</span><#lt/>
</#list>
</#if>
