<#if pcmat?exists && pcmat.id?exists>
	<ul>
		<li><a href="../pcmat/prepareUpdate.action?pcmat.id=${pcmat.id}">Geral</a></li>
		<li><a href="../fasePcmat/list.action?pcmat.id=${pcmat.id}">Fases</a></li>
	</ul>

	<br />
</#if>