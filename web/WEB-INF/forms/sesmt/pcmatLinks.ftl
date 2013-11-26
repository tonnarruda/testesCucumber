<#if pcmat?exists && pcmat.id?exists>
	<ul>
		<li><a href="prepareUpdate.action?pcmat.id=${pcmat.id}">Geral</a></li>
		<li><a href="prepareFases.action?pcmat.id=${pcmat.id}">Fases</a></li>
	</ul>

	<br />
</#if>