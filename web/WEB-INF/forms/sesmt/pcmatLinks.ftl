<style type="text/css">
	#menuPcmat
	{
		margin: -16px;
		color: #FFCB03;
		background-color: #265773;
	}
	#menuPcmat a
	{
		float: left;
		display: block;
		padding: 5px 15px;
		font-weight: bold!important;
		font-family: Arial, Helvetica, sans-serif;
		font-size: 12px;
		text-align: center;
		text-decoration: none;
		color: #FFF;
		border-left: 1px solid #327195; /* Sombra clara */
		border-right: 1px solid #1C4055; /* Sombra escura */
	}
	#menuPcmat a.last
	{
		border-right: none;
	}
	#menuPcmat a:hover
	{
		background-color: #6391AB;
	}
</style>

<#if pcmat?exists && pcmat.id?exists>
	<#assign pcmatId = pcmat.id/>
<#elseif fasePcmat?exists && fasePcmat.pcmat?exists && fasePcmat.pcmat.id?exists>
	<#assign pcmatId = fasePcmat.pcmat.id/>
</#if>

<#if pcmatId?exists>
	<div id="menuPcmat">
		<a href="../pcmat/prepareUpdate.action?pcmat.id=${pcmatId}" id="menuGeral">Geral</a>
		<a href="../fasePcmat/list.action?pcmat.id=${pcmatId}" id="menuFases">Fases</a>
		<a href="../areaVivenciaPcmat/list.action?pcmat.id=${pcmatId}" id="menuAreasVivencia">Áreas de Vivência</a>
		<a href="../atividadeSegurancaPcmat/list.action?pcmat.id=${pcmatId}" id="menuAtividadesSeguranca">Atividades de Segurança</a>
		<a href="../epiPcmat/list.action?pcmat.id=${pcmatId}" id="epi" id="menuEPIs">EPIs</a>
		<a href="#" id="menuEPCs">EPCs</a>
		<a class="last">&nbsp;</a>
		<div style="clear: both"></div>
	</div>

	<br clear="all"/><br />
</#if>