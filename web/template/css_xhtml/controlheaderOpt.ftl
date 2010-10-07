<#--
	Only show message if errors are available.
	This will be done if ActionSupport is used.
-->
<#assign hasFieldErrors = parameters.name?exists && fieldErrors?exists && fieldErrors[parameters.name]?exists/>
<li <#rt/><#if parameters.id?exists>id="wwgrp_${parameters.id}"<#rt/></#if> <#if parameters.liClass?exists>class="${parameters.liClass}"<#else>class="wwgrp"</#if>>
<fieldset class="fieldsetopt">
<#if parameters.label?exists>
		<legend><label <#t/>
        <#if parameters.id?exists>
                for="${parameters.id?html}" <#t/>
        </#if>
        <#if hasFieldErrors>
                class="desc error"<#t/>
        <#else>
                class="desc"<#t/>
        </#if>
        ><#t/>
            ${parameters.label?html}
        <#--if parameters.required?default(false)>
                <span class="req">*</span><#t/>
        </#if-->
        </label></legend><#t/>
        <#if hasFieldErrors>
        <#list fieldErrors[parameters.name] as error>
            <span class="fieldError"><img src="<@ww.url includeParams="none" value="/imgs/iconWarning.gif"/>" class="icon" /> ${error?html}</span><#lt/>
        </#list>
        </#if>
</#if>

<#--if parameters.labelposition?default("top") == 'top'>
<div <#rt/>
<#else>
<span <#rt/>
</#if>
<#if parameters.id?exists>id="wwctrl_${parameters.id}"<#rt/></#if> class="wwctrl"-->
