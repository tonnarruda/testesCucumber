<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>

	<#if documentoAnexo.origem?string == "C">
		<title>Documentos do Candidato - ${nome}</title>
		<#if solicitacaoId?exists>
			<#assign voltar="../../captacao/candidatoSolicitacao/list.action?solicitacao.id=${solicitacaoId}"/>
			<#assign inserir="prepareInsertCandidato.action?origemTmp=${documentoAnexo.origem}&origemIdTmp=${documentoAnexo.origemId}&solicitacaoId=${solicitacaoId}"/>
		<#else>
			<#assign voltar="../../captacao/candidato/list.action"/>
			<#assign inserir="prepareInsertCandidato.action?origemTmp=${documentoAnexo.origem}&origemIdTmp=${documentoAnexo.origemId}"/>
		</#if>
	<#elseif documentoAnexo.origem?string == "D">
		<title>Documentos do Colaborador - ${nome}</title>
		<#assign inserir="prepareInsertColaborador.action?origemTmp=${documentoAnexo.origem}&origemIdTmp=${documentoAnexo.origemId}"/>
		<#assign voltar="../colaborador/list.action"/>
	</#if>
	
	<#assign origemDocumento="${documentoAnexo.origem}"/>
	<#assign origemIdDocumento="${documentoAnexo.origemId}"/>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
</head>
<body>
	<@ww.actionerror/>
	<@ww.actionmessage/>
	
	<@display.table name="documentoAnexos" id="documentoAnexo" pagesize=10 class="dados" defaultsort=2 sort="list">
		<#if documentoAnexo.origem?string == "C">
			<#if solicitacaoId?exists>
				<#assign editar="prepareUpdateCandidato.action?solicitacaoId=${solicitacaoId}&"/>
				<#assign deletar="deleteCandidato.action?solicitacaoId=${solicitacaoId}&"/>
			<#else>
				<#assign editar="prepareUpdateCandidato.action?"/>
				<#assign deletar="deleteCandidato.action?"/>
			</#if>
		<#elseif documentoAnexo.origem?string == "D">
			<#assign editar="prepareUpdateColaborador.action?"/>
			<#assign deletar="deleteColaborador.action?"/>
		</#if>
		
		<@display.column title="Ações" class="acao" style="width:90px">
			<a href="javascript: executeLink('../documentoAnexo/showDocumento.action?documentoAnexo.id=${documentoAnexo.id}');" title="Visualizar documento" target="_blank"><img border="0" title="Visualizar documento" src="<@ww.url value="/imgs/olho.jpg"/>"></a>
			<a href="javascript: executeLink('${editar}documentoAnexo.id=${documentoAnexo.id}&documentoAnexo.origem=${documentoAnexo.origem}&documentoAnexo.origemId=${documentoAnexo.origemId}&origemDocumento=${origemDocumento}&origemIdDocumento=${origemIdDocumento}');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){executeLink('${deletar}documentoAnexo.id=${documentoAnexo.id}&documentoAnexo.origem=${origemDocumento}&documentoAnexo.origemId=${origemIdDocumento}');});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="descricao" style="width: 270px" title="Descrição"/>
		<#if origemDocumento == "C">
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
		<button class="btnInserir" onclick="javascript: executeLink('${inserir}');"></button>
		<button class="btnCancelar" onclick="window.location='${voltar}'"></button>
	</div>
</body>
</html>