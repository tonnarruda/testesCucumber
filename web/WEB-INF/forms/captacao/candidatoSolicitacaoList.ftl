<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	.apto
	{
		color: #F00 !important;
	}
	.contratado
	{
		color: #008000 !important;
	}
	</style>

	<title>Candidatos da Seleção</title>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<#include "../ftl/showFilterImports.ftl" />
</head>

<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	<table width="100%">
		<tr>
			<td>Área: <#if solicitacao.areaOrganizacional?exists && solicitacao.areaOrganizacional.nome?exists>${solicitacao.areaOrganizacional.nome}</#if></td>
			<td>Cargo: <#if solicitacao.faixaSalarial?exists && solicitacao.faixaSalarial.cargo?exists && solicitacao.faixaSalarial.cargo.nome?exists>${solicitacao.faixaSalarial.cargo.nome}</#if></td>
			<td>Solicitante: <#if solicitacao.solicitante?exists && solicitacao.solicitante.nome?exists>${solicitacao.solicitante.nome}</#if></td>
			<td>Vagas: <#if solicitacao.quantidade?exists>${solicitacao.quantidade}</#if></td>
		</tr>
	</table>
	<br/>

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="form" id="form" action="list.action" validate="true" method="POST">
			<@ww.select label="Etapa" name="etapaSeletivaId" list="etapas" listKey="id" listValue="nome" headerKey="" headerValue="Todas" liClass="liLeft"/>
			<@ww.select label="Situação" name="visualizar" list=r"#{'T':'Todas','A':'Aptos','N':'Não Aptos'}" />
			<@ww.textfield label="Nome" name="nomeBusca"/>
			<@ww.textfield label="Indicado por" name="indicadoPor"/>
			<@ww.textfield label="Observações do RH" name="observacaoRH"  cssStyle="width: 240px;"/>
			<@ww.hidden name="solicitacao.id" />
			<@ww.hidden id="pagina" name="page"/>

			<button class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;document.form.submit();" accesskey="B"></button>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>
	<div id="legendas" align="right"></div>

	<#if totalSize?exists && totalSize != 0>
		${totalSize} Registros encontrados
	</#if>

	<@display.table name="candidatoSolicitacaos" id="candidatoSolicitacao" class="dados" >
	
		<#if candidatoSolicitacao?exists && candidatoSolicitacao.apto>
			<#assign classe=""/>
		<#else>
			<#assign classe="apto"/>
		</#if>

		<#-- decide contratação (se é só candidato) ou promoção (se candidato já é colaborador) -->
		<#assign titleContrata="Contratar o Candidato"/>
		<#assign alertContrata="Deseja realmente contratar o candidato"/>
		<#if candidatoSolicitacao?exists>
			<#assign actionContrata="/geral/colaborador/prepareContrata.action?candidato.id=${candidatoSolicitacao.candidato.id}&solicitacao.id=${solicitacao.id}"/>
		</#if>
		<#if candidatoSolicitacao?exists && candidatoSolicitacao.candidato?exists && candidatoSolicitacao.candidato.contratado>
			<#assign titleContrata="Promover"/>
			<#assign alertContrata="Deseja realmente promover o candidato"/>
			<#assign classe="contratado"/>
			<#assign actionContrata="/geral/colaborador/preparePromoverCandidato.action?candidato.id=${candidatoSolicitacao.candidato.id}&solicitacao.id=${solicitacao.id}"/>
		</#if>
		
		<@authz.authorize ifAllGranted="ROLE_MOV_SOLICITACAO_SELECAO">
		<@display.column title="Ações" media="html" class="acao" style="width: 100px;">
			<#if !solicitacao.encerrada>
				<a href="../historicoCandidato/list.action?candidatoSolicitacao.id=${candidatoSolicitacao.id}"><img border="0" title="Histórico" src="<@ww.url includeParams="none" value="/imgs/page_user.gif"/>"></a>
			<#else>
				<img border="0" title="Esta Solicitação já foi encerrada" src="<@ww.url includeParams="none" value="/imgs/page_user.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			</#if>
	        <a href="javascript:popup('../candidato/infoCandidato.action?candidato.id=${candidatoSolicitacao.candidato.id}', 580, 750)"><img border="0" title="Visualizar Currículo" src="<@ww.url includeParams="none" value="/imgs/page_curriculo.gif"/>"></a>
			<#if !solicitacao.encerrada>
				<#assign nomeFormatado=stringUtil.removeApostrofo(candidatoSolicitacao.candidato.nome)>
				
				<a href="javascript:if (confirm('${alertContrata} ${nomeFormatado}?')) window.location='<@ww.url includeParams="none" value="${actionContrata}"/>'"><img border="0" title="${titleContrata}" src="<@ww.url includeParams="none" value="/imgs/contrata_colab.gif"/>"></a>
				
				<#if (!candidatoSolicitacao.etapaSeletiva?exists || !candidatoSolicitacao.etapaSeletiva.id?exists) && (candidatoSolicitacao?exists && candidatoSolicitacao.candidato?exists && !candidatoSolicitacao.candidato.contratado)>
					<a href="#" onclick="if (confirm('Confirma exclusão?')) window.location='delete.action?solicitacao.id=${solicitacao.id}&candidatoSolicitacao.id=${candidatoSolicitacao.id}'"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
				<#else>
					<img border="0" style="opacity:0.2;filter:alpha(opacity=20)" title="Este candidato já possui históricos. Não é possível removê-lo da seleção." src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>">
				</#if>
				<a href="../../geral/documentoAnexo/list.action?documentoAnexo.origem=C&documentoAnexo.origemId=${candidatoSolicitacao.candidato.id}&solicitacaoId=${solicitacao.id}" title="Documentos Anexos"><img border="0"  src="<@ww.url includeParams="none" value="/imgs/anexos.gif"/>"></a>
			</#if>
		</@display.column>
		</@authz.authorize>

		<@display.column title="Nome" class="${classe}">
			${candidatoSolicitacao.candidato.nome}
			<#if candidatoSolicitacao.candidato.pessoal?exists && candidatoSolicitacao.candidato.pessoal.indicadoPor?exists && candidatoSolicitacao.candidato.pessoal.indicadoPor?trim != "">
				<span href=# style="cursor: hand;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'Indicado por: <br>${candidatoSolicitacao.candidato.pessoal.indicadoPor?j_string}');return false">
					<img border="0" src="<@ww.url includeParams="none" value="/imgs/favourites.gif"/>">
				</span>
			</#if>
		</@display.column>
		<@display.column property="etapaSeletiva.nome" title="Etapa" class="${classe}"/>
		<@display.column property="responsavel" title="Responsável" class="${classe}"/>
		<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" class="${classe}" style="text-align: center;"/>
		<@display.column title="Obs." class="${classe}" style="text-align: center;">
			<#if candidatoSolicitacao.observacao?exists && candidatoSolicitacao.observacao?trim != "">
				<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${candidatoSolicitacao.observacao?j_string}');return false">...</span>
			</#if>
		</@display.column>
	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="form"/>

	<#if totalSize?exists && totalSize != 0>
		${totalSize} Registros encontrados
	</#if>

	<div class="buttonGroup">
		<div style="float: left;width: 50%;">
			<#if !solicitacao.encerrada>
				<@authz.authorize ifAllGranted="ROLE_MOV_SOLICITACAO_SELECAO">
					<button class="btnTriagem" onclick="window.location='../candidato/prepareBuscaSimples.action?solicitacao.id=${solicitacao.id}'" accesskey="I"></button>
				</@authz.authorize>
			</#if>
				<button onclick="window.location='../historicoCandidato/prepareInsert.action?solicitacao.id=${solicitacao.id}'" class="btnInserirEtapasEmGrupo" accesskey="M"></button>
				<button onclick="window.location='../solicitacao/list.action'" class="btnVoltar" accesskey="V"></button>
		</div>

		<@authz.authorize ifAllGranted="ROLE_MOV_SOLICITACAO_SELECAO">
			<div style="text-align: right;">
				<button onclick="window.location='prepareMover.action?solicitacao.id=${solicitacao.id}'" class="btnTransferirCandidatos" accesskey="M"></button>
				<#if !solicitacao.encerrada>
					<button onclick="window.location='listTriagem.action?solicitacao.id=${solicitacao.id}'" class="btnTriagemModuloExterno" accesskey="T"></button>
				</#if>
			</div>
		</@authz.authorize>
	</div>

	<script>
		var obj = document.getElementById("legendas");
		obj.innerHTML += "&nbsp;&nbsp;<span style='background-color: #555;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Aptos";
		obj.innerHTML += "&nbsp;&nbsp;<span style='background-color: #F00;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Não Aptos";
		obj.innerHTML += "&nbsp;&nbsp;<span style='background-color: #008000;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Contratados";
	</script>
</body>
</html>