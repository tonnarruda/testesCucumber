<#include "/${parameters.templateDir}/${parameters.theme}/controlheader.ftl" />
<#if stack.findString(parameters.name.split('\\.')[0]+".id")?exists && stack.findString(parameters.name)?exists>
	<#assign hasFile="true">
<#else>
	<#assign hasFile="false">	
</#if>
<#if hasFile=="true">
<input type="checkbox" name="${parameters.name}.ignored" value="true" checked="checked" id="${parameters.id}_ignored" onclick="document.getElementById('${parameters.id}_span').style.display=((this.checked)?'none':'')" />
(Manter atual)
<br>
</#if>
<span id="${parameters.id}_span" <#if hasFile="true">style="display:none"</#if>>
<#include "/${parameters.templateDir}/simple/file.ftl" />
</span>
<#include "/${parameters.templateDir}/xhtml/controlfooter.ftl" />
