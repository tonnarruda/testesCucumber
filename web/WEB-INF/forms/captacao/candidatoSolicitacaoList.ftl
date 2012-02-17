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
	.btnTriagem, .btnInserirEtapasEmGrupo, .btnResultadoAvaliacao, .btnVoltar{
		margin: 5px 5px 0px 0px;
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
			<@ww.select label="Situação" name="visualizar" list=r"#{'T':'Todas','A':'Aptos/Indiferente','N':'Não Aptos'}" />
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
	<#assign jaResponderam = false/>

	<@display.table name="candidatoSolicitacaos" id="candidatoSolicitacao" class="dados" >
	
		<#if candidatoSolicitacao?exists && candidatoSolicitacao.aptoBoolean>
			<#assign classe=""/>
		<#else>
			<#assign classe="apto"/>
		</#if>

		<#-- decide contratação (se é só candidato) ou promoção (se candidato já é colaborador) -->
		<#if candidatoSolicitacao?exists && candidatoSolicitacao.candidato?exists >
			<#if candidatoSolicitacao.candidato.contratado || candidatoSolicitacao.status == 'A'>
				<#assign titleContrata="Promover"/>
				<#assign alertContrata="Deseja realmente promover o candidato"/>
				<#assign actionContrata="/geral/colaborador/preparePromoverCandidato.action?candidato.id=${candidatoSolicitacao.candidato.id}&solicitacao.id=${solicitacao.id}&candidatoSolicitacaoId=${candidatoSolicitacao.id}"/>
			<#else>
				<#assign titleContrata="Contratar o Candidato"/>
				<#assign alertContrata="Deseja realmente contratar o candidato"/>
				<#assign actionContrata="/geral/colaborador/prepareContrata.action?candidato.id=${candidatoSolicitacao.candidato.id}&solicitacao.id=${solicitacao.id}&candidatoSolicitacaoId=${candidatoSolicitacao.id}"/>
			</#if>
		</#if>
		<#assign actionContrataCandidatoOutraEmpresa="/geral/colaborador/prepareContrata.action?candidato.id=${candidatoSolicitacao.candidato.id}&solicitacao.id=${solicitacao.id}&candidatoSolicitacaoId=${candidatoSolicitacao.id}"/>
		
		<#if candidatoSolicitacao?exists && candidatoSolicitacao.status == 'C'>
			<#assign titleAceito="Candidato já contratado"/>
		</#if>
		<#if candidatoSolicitacao?exists && candidatoSolicitacao.status == 'P'>
			<#assign titleAceito="Colaborador já promovido"/>
		</#if>
		
		<#if candidatoSolicitacao?exists && (candidatoSolicitacao.status == 'P' || candidatoSolicitacao.status == 'C')>
			<#assign classe="contratado"/>
		</#if>
		
		<@display.column title="Ações" media="html" class="acao" style="width: 140px;">
		<@authz.authorize ifAllGranted="ROLE_MOV_SOLICITACAO_SELECAO">
			<#if !solicitacao.encerrada>
				<a href="../historicoCandidato/list.action?candidatoSolicitacao.id=${candidatoSolicitacao.id}"><img border="0" title="Histórico" src="<@ww.url includeParams="none" value="/imgs/page_user.gif"/>"></a>
			<#else>
				<img border="0" title="Esta Solicitação já foi encerrada" src="<@ww.url includeParams="none" value="/imgs/page_user.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			</#if>
		</@authz.authorize>

		<a href="../nivelCompetencia/prepareCompetenciasByCandidato.action?&candidato.id=${candidatoSolicitacao.candidato.id}&faixaSalarial.id=${solicitacao.faixaSalarial.id}&solicitacao.id=${solicitacao.id}"><img border="0" title="Competências" src="<@ww.url value="/imgs/competencias.gif"/>"></a>

		<@authz.authorize ifAllGranted="ROLE_INFORM_CANDIDATO">
        	<a href="javascript:popup('../candidato/infoCandidato.action?candidato.id=${candidatoSolicitacao.candidato.id}&solicitacao.id=${solicitacao.id}', 580, 750)"><img border="0" title="Visualizar Currículo" src="<@ww.url includeParams="none" value="/imgs/page_curriculo.gif"/>"></a>
		</@authz.authorize>

		<@authz.authorize ifAllGranted="ROLE_MOV_SOLICITACAO_SELECAO">
			<#if !solicitacao.encerrada>
				<#assign nomeFormatado=stringUtil.removeApostrofo(candidatoSolicitacao.candidato.nome)>
				
				<#if candidatoSolicitacao?exists && (candidatoSolicitacao.status == 'P' || candidatoSolicitacao.status == 'C')>
					<img border="0" style="opacity:0.3;filter:alpha(opacity=30)" title="${titleAceito}" src="<@ww.url includeParams="none" value="/imgs/contrata_colab.gif"/>">
				<#else>
					<#if candidatoSolicitacao?exists && candidatoSolicitacao.candidato.empresa.id != solicitacao.empresa.id>
						<#if candidatoSolicitacao.candidato.disponivel>
							<a href="javascript:newConfirm('Deseja realmente contratar o candidato ${nomeFormatado}?', function(){window.location='<@ww.url includeParams="none" value="${actionContrataCandidatoOutraEmpresa}"/>'});"><img border="0" title="Contratar o Candidato" src="<@ww.url includeParams="none" value="/imgs/contrata_colab.gif"/>"></a>
						<#else>
							<img border="0" style="opacity:0.3;filter:alpha(opacity=30)" title="Colaborador contratado na empresa ${candidatoSolicitacao.candidato.empresa.nome}. Desligue-o para continuar o processo de transferência." src="<@ww.url includeParams="none" value="/imgs/desliga_colab.gif"/>">
						</#if>
					<#else>
						<a href="javascript:newConfirm('${alertContrata} ${nomeFormatado}?', function(){window.location='<@ww.url includeParams="none" value="${actionContrata}"/>'});"><img border="0" title="${titleContrata}" src="<@ww.url includeParams="none" value="/imgs/contrata_colab.gif"/>"></a>
					</#if>
				</#if>
				
				<#if (!candidatoSolicitacao.etapaSeletiva?exists || !candidatoSolicitacao.etapaSeletiva.id?exists) && (candidatoSolicitacao?exists && candidatoSolicitacao.candidato?exists && !candidatoSolicitacao.candidato.contratado)>
					<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?solicitacao.id=${solicitacao.id}&candidatoSolicitacao.id=${candidatoSolicitacao.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
				<#else>
					<img border="0" style="opacity:0.2;filter:alpha(opacity=20)" title="Este candidato já possui históricos. Não é possível removê-lo da seleção." src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>">
				</#if>
				
				<a href="../../geral/documentoAnexo/list.action?documentoAnexo.origem=C&documentoAnexo.origemId=${candidatoSolicitacao.candidato.id}&solicitacaoId=${solicitacao.id}" title="Documentos Anexos"><img border="0"  src="<@ww.url includeParams="none" value="/imgs/anexos.gif"/>"></a>
			</#if>
			
			<#if solicitacao.avaliacao.id?exists>
				<#if candidatoSolicitacao.colaboradorQuestionarioId?exists>
					<#assign jaResponderam = true/>
					<a href="prepareUpdateAvaliacaoSolicitacao.action?solicitacao.id=${solicitacao.id}&colaboradorQuestionario.id=${candidatoSolicitacao.colaboradorQuestionarioId}&candidato.id=${candidatoSolicitacao.candidato.id}"><img border="0" title="Editar Respostas" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				<#else>
					<a href="prepareInsertAvaliacaoSolicitacao.action?solicitacao.id=${solicitacao.id}&colaboradorQuestionario.avaliacao.id=${solicitacao.avaliacao.id}&candidato.id=${candidatoSolicitacao.candidato.id}"><img border="0" title="Responder Avaliação" src="<@ww.url value="/imgs/page_new.gif"/>"></a>
				</#if>
			</#if>
		</@authz.authorize>
		</@display.column>

		<@display.column title="Nome" class="${classe}">
			${candidatoSolicitacao.candidato.nome}
			<#if candidatoSolicitacao.candidato.pessoal?exists && candidatoSolicitacao.candidato.pessoal.indicadoPor?exists && candidatoSolicitacao.candidato.pessoal.indicadoPor?trim != "">
				<span href=# style="cursor: hand;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'Indicado por: <br>${candidatoSolicitacao.candidato.pessoal.indicadoPor?j_string}');return false">
					<img border="0" src="<@ww.url includeParams="none" value="/imgs/favourites.gif"/>">
				</span>
			</#if>
			<#if candidatoSolicitacao.candidato.idF2RH?exists>
				<a title="Visualizar Currículo F2RH" href="javascript:popup('http://www.f2rh.com.br/curriculos/${candidatoSolicitacao.candidato.idF2RH?string}?s=${candidatoSolicitacao.candidato.s}', 780, 750)">
					<img border="0" src="<@ww.url includeParams="none" value="/imgs/page_curriculo.gif"/>">
				</a>
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

	<div class="buttonGroup" style="margin: 0px;">
		<#if !solicitacao.encerrada>
			<@authz.authorize ifAllGranted="ROLE_MOV_SOLICITACAO_SELECAO">
				<button class="btnTriagem" onclick="window.location='../candidato/prepareBusca.action?solicitacao.id=${solicitacao.id}'"></button>
			</@authz.authorize>
		</#if>
		
		<button onclick="window.location='../historicoCandidato/prepareInsert.action?solicitacao.id=${solicitacao.id}'" class="btnInserirEtapasEmGrupo" accesskey="M"></button>

		<#if jaResponderam>
			<button onclick="window.location='imprimeRankingAvaliacao.action?solicitacao.id=${solicitacao.id}&solicitacao.avaliacao.id=${solicitacao.avaliacao.id}'" class="btnResultadoAvaliacao"></button>
		</#if>

		<@authz.authorize ifAllGranted="ROLE_MOV_SOLICITACAO_SELECAO">
			<button onclick="window.location='prepareMover.action?solicitacao.id=${solicitacao.id}'" class="btnTransferirCandidatos" accesskey="M"></button>
			<#if !solicitacao.encerrada>
				<button onclick="window.location='listTriagem.action?solicitacao.id=${solicitacao.id}'" class="btnTriagemModuloExterno" accesskey="T"></button>
			</#if>
		</@authz.authorize>

		<button onclick="window.location='../solicitacao/list.action'" class="btnVoltar" accesskey="V"></button>
		
		<@authz.authorize ifAllGranted="ROLE_MOV_SOLICITACAO_CANDIDATO">
			<br /><br />
			<#if existeCompetenciaParaFaixa>
				<button onclick="window.location='../nivelCompetencia/imprimirMatrizCompetenciasCandidatos.action?faixaSalarial.id=${solicitacao.faixaSalarial.id}&solicitacao.id=${solicitacao.id}'" class="btnMatrizCompetencia"></button>
			<#else>
				<img border="0" title="Não existe Competência configurada para a Faixa Salarial" style="opacity:0.2;filter:alpha(opacity=20);" src="<@ww.url includeParams="none" value="/imgs/btnMatrizCompetencia.gif"/>">
			</#if>
		</@authz.authorize>
	</div>
	<div style="clear: both;"></div>

	<script>
		var obj = document.getElementById("legendas");
		obj.innerHTML += "&nbsp;&nbsp;<span style='background-color: #555;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Aptos/Indiferente";
		obj.innerHTML += "&nbsp;&nbsp;<span style='background-color: #F00;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Não Aptos";
		obj.innerHTML += "&nbsp;&nbsp;<span style='background-color: #008000;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Contratados/Promovidos";
	</script>
</body>
</html>