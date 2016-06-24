<style type="text/css">
	#menuPcmat
	{
		margin: -16px;
		color: #FFCB03;
		background-color: #E0DFDF;
	}
	#menuPcmat a
	{
		float: left;
		display: block;
		padding: 7px 15px;
		font-family: Arial, Helvetica, sans-serif;
		font-size: 12px;
		text-align: center;
		text-decoration: none;
		color: #5C5C5A;
		border-right: 1px solid #C6C6C6;
	}
	#menuPcmat a.last
	{
		border-right: none;
	}
	#menuPcmat a:hover
	{
		color: #5292C0;
	}
</style>

<#if pcmat?exists && pcmat.id?exists>
	<#assign pcmatId = pcmat.id/>
<#elseif fasePcmat?exists && fasePcmat.pcmat?exists && fasePcmat.pcmat.id?exists>
	<#assign pcmatId = fasePcmat.pcmat.id/>
</#if>

<#if pcmatId?exists>
	<div id="menuPcmat">
		<a href="javascript: executeLink('../pcmat/prepareUpdate.action?pcmat.id=${pcmatId}&ultimoPcmatId=${ultimoPcmatId}');" id="menuGeral">Geral</a>
		<a href="javascript: executeLink('../fasePcmat/list.action?pcmat.id=${pcmatId}&ultimoPcmatId=${ultimoPcmatId}');" id="menuFases">Fases</a>
		<a href="javascript: executeLink('../areaVivenciaPcmat/list.action?pcmat.id=${pcmatId}&ultimoPcmatId=${ultimoPcmatId}');" id="menuAreasVivencia">Áreas de Vivência</a>
		<a href="javascript: executeLink('../atividadeSegurancaPcmat/list.action?pcmat.id=${pcmatId}&ultimoPcmatId=${ultimoPcmatId}');" id="menuAtividadesSeguranca">Atividades de Segurança</a>
		<a href="javascript: executeLink('../epiPcmat/list.action?pcmat.id=${pcmatId}&ultimoPcmatId=${ultimoPcmatId}');" id="menuEPIs">EPIs</a>
		<a href="javascript: executeLink('../epcPcmat/list.action?pcmat.id=${pcmatId}&ultimoPcmatId=${ultimoPcmatId}');" id="menuEPCs">EPCs</a>
		<a href="javascript: executeLink('../sinalizacaoPcmat/list.action?pcmat.id=${pcmatId}&ultimoPcmatId=${ultimoPcmatId}');" id="menuSinalizacao">Sinalização</a>
		<a class="last">&nbsp;</a>
		<div style="clear: both"></div>
	</div>

	<br clear="all"/><br />
</#if>