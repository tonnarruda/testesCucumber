<#if pcmat?exists && pcmat.id?exists>
	<#assign pcmatId = pcmat.id/>
<#elseif fasePcmat?exists && fasePcmat.pcmat?exists && fasePcmat.pcmat.id?exists>
	<#assign pcmatId = fasePcmat.pcmat.id/>
</#if>

<#if pcmatId?exists>
	<ul>
		<li><a href="../pcmat/prepareUpdate.action?pcmat.id=${pcmatId}">Geral</a></li>
		<li><a href="#">Cronograma</a></li>
		<li><a href="../fasePcmat/list.action?pcmat.id=${pcmatId}">Fases</a></li>
		<li><a href="../areaVivenciaPcmat/list.action?pcmat.id=${pcmatId}">Área de Vivência</a></li>
		<li><a href="#">Calend. das Ativ. Segurança</a></li>
		<li><a href="#">Medidas de Controle Coletivo</a></li>
	</ul>

	<br />
</#if>