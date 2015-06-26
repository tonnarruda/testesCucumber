<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
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

<@display.table name="candidatoSolicitacaos" id="candidatoSolicitacao" pagesize=15 class="dados" >
	<@display.column title="Ações" media="html" class="acao" style="width: 60px;">
		<a href="javascript: newConfirm('Confirma inclusão na seleção?', function(){window.location='removerTriagem.action?candidatoSolicitacao.id=${candidatoSolicitacao.id}&solicitacao.id=${solicitacao.id}'});"><img border="0" title="Incluir na Seleção" src="<@ww.url includeParams="none" value="/imgs/add.gif"/>"></a>
		<a href="javascript: newConfirm('Confirma exclusão?', function(){window.location='delete.action?candidatoSolicitacao.id=${candidatoSolicitacao.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
	</@display.column>
	<@display.column title="Nome" >
		<a href="javascript:popup('<@ww.url includeParams="none" value="/captacao/candidato/infoCandidato.action?candidato.id=${candidatoSolicitacao.candidato.id}"/>', 580, 750)">
			${candidatoSolicitacao.candidato.nome}
		</a>
	</@display.column>
	<@display.column property="candidato.pessoal.escolaridadeDescricao" title="Escolaridade" />
	<@display.column title="Cidade/UF" >
		<#if candidatoSolicitacao.candidato.endereco.cidade?exists>
			${candidatoSolicitacao.candidato.endereco.cidade.nome}/${candidatoSolicitacao.candidato.endereco.uf.sigla}
		</#if>
	</@display.column>
	<@display.column property="candidato.dataAtualizacao" title="Últ. Atualização" format="{0,date,dd/MM/yyyy}" />
	<@display.column property="candidato.origemDescricao" title="Origem" />
</@display.table>


<div class="buttonGroup">
	<!--<button onclick="window.location='../solicitacaoBDS/prepareImportacaoBDS.action?solicitacao.id=${solicitacao.id}'" class="btnImportarbds" accesskey="I"></button>-->
	<button onclick="window.location='list.action?solicitacao.id=${solicitacao.id}'" class="btnVoltar" accesskey="V"></button>
</div>
</body>
</html>