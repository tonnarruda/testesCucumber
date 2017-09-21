<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<title>${titulo}</title>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	
	<#if solicitacaoId?exists>
		<#assign addSolicitacaoId="solicitacaoId=${solicitacaoId}&"/>
	<#else>
		<#assign addSolicitacaoId=""/>
	</#if>
	
	<#if colaboradorId?exists>
		<#assign addcolaboradorId = "&colaboradorId=${colaboradorId}"/>
	<#else>
		<#assign addcolaboradorId = ""/>
	</#if>
	
	<#if origem?exists>
		<#assign addOrigem = "origem=${documentoAnexo.origem}&"/>
	<#else>
		<#assign addOrigem = ""/>
	</#if>
	
	<!--Tem que ser aqui para pegar dados do documentoAnexo da URL, pois a listagem é de candidato + colaborador-->
	<#assign insert="prepareInsert.action?${addSolicitacaoId}${addOrigem}documentoAnexo.origem=${documentoAnexo.origem}&documentoAnexo.origemId=${documentoAnexo.origemId}"/>
	
</head>
<body>
	<@ww.actionerror/>
	<@ww.actionmessage/>
	
	<@display.table name="documentoAnexos" id="documentoAnexo" pagesize=10 class="dados" defaultsort=2 sort="list">
		<@display.column title="Ações" class="acao" style="width:90px">
			<a href="../documentoAnexo/showDocumento.action?documentoAnexo.id=${documentoAnexo.id}&${addOrigem}documentoAnexo.origem=${documentoAnexo.origem}${addcolaboradorId}" title="Visualizar documento" target="_blank"><img border="0" title="Visualizar documento" src="<@ww.url value="/imgs/olho.jpg"/>"></a>
			<a href="prepareUpdate.action?${addSolicitacaoId}${addOrigem}documentoAnexo.id=${documentoAnexo.id}&documentoAnexo.origem=${documentoAnexo.origem}&documentoAnexo.origemId=${documentoAnexo.origemId}${addcolaboradorId}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?${addSolicitacaoId}documentoAnexo.id=${documentoAnexo.id}&${addOrigem}documentoAnexo.origem=${documentoAnexo.origem}&documentoAnexo.origemId=${documentoAnexo.origemId}${addcolaboradorId}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="descricao" style="width: 270px" title="Descrição"/>
		
		<#if documentoAnexo.origem == "C">
			<@display.column property="etapaSeletiva.nome" style="width: 200px" title="Etapa Seletiva"/>
		</#if>
		
		<@display.column property="data" title="Data" style="text-align: center;width: 50px" format="{0,date,dd/MM/yyyy}"/>
		<@display.column title="Obs." style="text-align: center;width: 50px">
			<#if documentoAnexo.observacao?exists && documentoAnexo.observacao?trim != "">
				<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${documentoAnexo.observacao?j_string}');return false">...</span>
			</#if>
		</@display.column>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='${insert}'"></button>
		<button class="btnCancelar" onclick="window.location='${voltar}'"></button>
	</div>
</body>
</html>