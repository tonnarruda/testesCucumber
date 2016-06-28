<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/buscaCandidatoSolicitacao.js?version=${versao}"/>'></script>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
	</style>
	<title>Candidatos da Seleção - Triagem</title>

</head>
<body>

<table width="100%">
	<tr>
		<td>Área: ${solicitacao.areaOrganizacional.nome}</td>
		<td>Cargo: ${solicitacao.faixaSalarial.cargo.nome}</td>
		<td>Solicitante: ${solicitacao.solicitante.nome}</td>
		<td>Vagas: ${solicitacao.quantidade}</td>
	</tr>
</table>
<br/>

<@ww.form name="formCandSolic" action="removerTriagem.action?solicitacao.id=${solicitacao.id}" validate="true" method="POST">
	<@display.table name="candidatoSolicitacaos" id="candidatoSolicitacao" pagesize=15 class="dados" >
		<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcarCandidatoSolicitacao(document.formCandSolic);' />" style="width: 30px; text-align: center;" >
			<input type="checkbox" value="${candidatoSolicitacao.id?string?replace(".", "")?replace(",","")}" name="candidatoSolicitacaoIdsSelecionados" />
		</@display.column>
	
		<@display.column title="Ações" media="html" class="acao" style="width: 40px;">
			<!--<a href="javascript: newConfirm('Confirma inclusão na seleção?', function(){window.location='removerTriagem.action?candidatoSolicitacao.id=${candidatoSolicitacao.id}&solicitacao.id=${solicitacao.id}'});"><img border="0" title="Incluir na Seleção" src="<@ww.url includeParams="none" value="/imgs/add.gif"/>"></a> -->
			<a href="javascript: newConfirm('Confirma exclusão?', function(){javascript: executeLink('delete.action?candidatoSolicitacao.id=${candidatoSolicitacao.id}');});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column title="Nome" >
			<a href="javascript:popup('<@ww.url includeParams="none" value="/captacao/candidato/infoCandidato.action?candidato.id=${candidatoSolicitacao.candidato.id}"/>', 580, 750)">
				${candidatoSolicitacao.candidato.nome}
			</a>
		</@display.column>
		<@display.column property="candidato.pessoal.escolaridadeDescricao" title="Escolaridade" />
		<@display.column title="Cidade/UF" >
			<#if candidatoSolicitacao.candidato.endereco.cidade.nome?exists && candidatoSolicitacao.candidato.endereco.uf.sigla?exists>
				${candidatoSolicitacao.candidato.endereco.cidade.nome}/${candidatoSolicitacao.candidato.endereco.uf.sigla}
			</#if>
		</@display.column>
		<@display.column property="candidato.dataAtualizacao" title="Últ. Atualização" format="{0,date,dd/MM/yyyy}" />
		<@display.column property="candidato.origemDescricao" title="Origem" />
		<@ww.hidden name="solicitacao.id" value="${solicitacao.id}" />
		<@ww.hidden name="internalToken" />
	</@display.table>
	
	
	<div class="buttonGroup">
		<!--<button onclick="window.location='../solicitacaoBDS/prepareImportacaoBDS.action?solicitacao.id=${solicitacao.id}'" class="btnImportarbds" accesskey="I"></button>-->
		<button type="button" onclick="prepareEnviarFormCandSolic();" class="btnInserirSelecionados" accesskey="I"></button>
		<button type="button" onclick="removerCandidatosDaSolicitacao(${solicitacao.id}, document.formCandSolic);" class="btnExcluirSelecionados" ></button>
		<button type="button" onclick="javascript: executeLink('list.action?solicitacao.id=${solicitacao.id}');" class="btnVoltar" accesskey="V"></button>
	</div>
</@ww.form>
</body>
</html>