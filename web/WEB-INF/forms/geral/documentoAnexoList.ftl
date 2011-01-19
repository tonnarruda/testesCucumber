<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>

	<#if documentoAnexo.origem?string == "C">
		<title>Documentos do Candidato - ${nome}</title>
		<#assign diretorio="documentosCandidatos">
		<#if solicitacaoId?exists>
			<#assign voltar="../../captacao/candidatoSolicitacao/list.action?solicitacao.id=${solicitacaoId}"/>
			<#assign inserir="prepareInsertCandidato.action?origemTmp=${documentoAnexo.origem}&origemIdTmp=${documentoAnexo.origemId}&solicitacaoId=${solicitacaoId}"/>
			<#assign editar="prepareUpdateCandidato.action?solicitacaoId=${solicitacaoId}&"/>
			<#assign deletar="deleteCandidato.action?solicitacaoId=${solicitacaoId}&"/>
		<#else>
			<#assign voltar="../../captacao/candidato/list.action"/>
			<#assign inserir="prepareInsertCandidato.action?origemTmp=${documentoAnexo.origem}&origemIdTmp=${documentoAnexo.origemId}"/>
			<#assign editar="prepareUpdateCandidato.action?"/>
			<#assign deletar="deleteCandidato.action?"/>
		</#if>
	<#elseif documentoAnexo.origem?string == "D">
		<title>Documentos do Colaborador - ${nome}</title>
		<#assign diretorio="documentosColaboradores">
		<#assign inserir="prepareInsertColaborador.action?origemTmp=${documentoAnexo.origem}&origemIdTmp=${documentoAnexo.origemId}"/>
		<#assign editar="prepareUpdateColaborador.action?"/>
		<#assign deletar="deleteColaborador.action?"/>
		<#assign voltar="../colaborador/list.action"/>
	</#if>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>
</head>
<body>
	<@ww.actionerror/>
	<@ww.actionmessage/>
	
	<@display.table name="documentoAnexos" id="documentoAnexo" pagesize=10 class="dados" defaultsort=2 sort="list">
		<@display.column title="Ações" class="acao" style="width:90px">
			<a href="../documentoAnexo/showDocumento.action?documentoAnexo.id=${documentoAnexo.id}" title="Visualizar documento" target="_blank"><img border="0" title="Visualizar documento" src="<@ww.url value="/imgs/olho.jpg"/>"></a>
			<a href="${editar}documentoAnexo.id=${documentoAnexo.id}&documentoAnexo.origem=${documentoAnexo.origem}&documentoAnexo.origemId=${documentoAnexo.origemId}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='${deletar}documentoAnexo.id=${documentoAnexo.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="descricao" style="width: 270px" title="Descrição"/>
		<#if documentoAnexo.origem?string == "C">
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
		<button class="btnInserir" onclick="window.location='${inserir}'"></button>
		<button class="btnCancelar" onclick="window.location='${voltar}'"></button>
	</div>
</body>
</html>