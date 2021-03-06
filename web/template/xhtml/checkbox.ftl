<#assign hasFieldErrors = fieldErrors?exists && fieldErrors[parameters.name]?exists />
<#if hasFieldErrors>
<#list fieldErrors[parameters.name] as error>
<tr<#rt/>
<#if parameters.id?exists>
 errorFor="${parameters.id}"<#rt/>
</#if>
>
    <td align="left" valign="top" colspan="2"><#rt/>
        <span class="errorMessage">${error?html}</span><#t/>
    </td><#lt/>
</tr>
</#list>
</#if>
<tr>
    <td valign="top">
    &nbsp;
    </td>
    <td valign="top">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td valign="middle"><#include "/${parameters.templateDir}/simple/checkbox.ftl" /></td>
                <td width="100%" valign="middle"><#rt/>
                    <label<#t/>
<#if parameters.id?exists>
 for="${parameters.id?html}"<#rt/>
</#if>
<#if hasFieldErrors>
 class="checkboxErrorLabel"<#rt/>
<#else>
 class="checkboxLabel"<#rt/>
</#if>
>${parameters.label?html}</label><#rt/>
                </td><#lt/>
            </tr>
        </table>
    <#include "/${parameters.templateDir}/xhtml/controlfooter.ftl" /><#nt/>