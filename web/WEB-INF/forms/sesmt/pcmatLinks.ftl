<#if pcmat?exists && pcmat.id?exists>
	<#assign pcmatId = pcmat.id/>
<#elseif fasePcmat.pcmat?exists && fasePcmat.pcmat.id?exists>
	<#assign pcmatId = fasePcmat.pcmat.id/>
</#if>

<#if pcmatId?exists>
	<ul>
		<li><a href="../pcmat/prepareUpdate.action?pcmat.id=${pcmatId}">Geral</a></li>
		<li><a href="../fasePcmat/list.action?pcmat.id=${pcmatId}">Fases</a></li>
	</ul>

	<br />
</#if>