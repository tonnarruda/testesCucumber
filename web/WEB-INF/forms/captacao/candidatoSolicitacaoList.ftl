<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Candidatos da Seleção</title>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		
		#legendas { float: right; margin-bottom: 10px; }
		.naoApto { color: #F00 !important; }
		.apto { color: #0000FF !important; }
		.indiferente { color: #555 !important; }
		.contratado { color: #008000 !important; }
		.contratadoOutros { color: #990066 !important; }
		.btnTriagem, .btnInserirEtapasEmGrupo, .btnResultadoAvaliacao, .btnVoltar { margin: 5px 5px 0px 0px; }
	</style>
	
	<#assign baseUrl><@ww.url includeParams="none" value="/"/></#assign>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
	<script type="text/javascript">
		function contrataCandOutraEmpresa(candidatoId, candidatoSolicitacaoId, nomeCandidato)
		{
			ColaboradorDWR.verificaDesligadoByCandidato(candidatoId, function(retorno) {  
				if(retorno.desligado){
					var link = "geral/colaborador/prepareContrata.action?candidato.id=" + candidatoId + "&solicitacao.id=${solicitacao.id}&candidatoSolicitacaoId=" + candidatoSolicitacaoId;
					contrataCandidato(nomeCandidato,'Contratar', link );			
				} else
					jAlert("Colaborador contratado na empresa " + retorno.empresa + ".\nDesligue-o para continuar o processo de transferência.");
			});
			
		}
		
		function contrataCandidato(nomeCandidato, acao, link)
		{
			var link = "${baseUrl}"+link;
			var msg = 'Deseja realmente '+acao.toLowerCase()+' '+nomeCandidato+' ? <br /><br />';
			
			if(${solicitacao.quantidade} == ${solicitacao.qtdVagasPreenchidas}+1) {
				msg += '<b>*Esta é a última vaga da solicitação a ser preenchida.</b>';
			} else if (${solicitacao.quantidade} == ${solicitacao.qtdVagasPreenchidas}){
				msg += '<b>*Com esta operação as vagas da solicitação serão excedidas.</b>'
			} else if (${solicitacao.quantidade} < ${solicitacao.qtdVagasPreenchidas}){
				msg += '<b>*As vagas da solicitação já foram excedidas.</b>'
			}
			
			$('<div>'+ msg +'</div>').dialog({title: acao,
													modal: true, 
													height: 170,
													width: 500,
													buttons: [
													    {
													        text: acao,
													        click: function() { window.location=link+'&encerrarSolicitacao=N'; }
													    },
														<#if solicitacao.qtdVagasPreenchidas?exists && solicitacao.quantidade?exists && solicitacao.quantidade <= solicitacao.qtdVagasPreenchidas+1>
														    {
														        text: acao+" e encerrar solicitação",
														        click: function() { window.location=link+'&encerrarSolicitacao=S'; }
														    },
													    </#if>
													    {
													        text: "Cancelar",
													        click: function() { $(this).dialog("close"); }
													    }
													] 
													});
			
		}
		
		function getMenuAvaliacoes(event, solicitacaoId, candidatoId)
		{
			evt = event;
			
			$('#popup').dialog({modal: true, 
								width: 320, 
								position: [event.pageX, event.pageY],
								create: function (event, ui) {
							        $(".ui-dialog-titlebar").hide();
							    }
							}).load('<@ww.url includeParams="none" value="/captacao/candidatoSolicitacao/popupAvaliacoesCandidatoSolicitacao.action"/>', { 'solicitacao.id': solicitacaoId, 'candidato.id': candidatoId });
		}
		
	</script>
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#assign validarRelatorioListagem = "$('#ImprimirCandSol').attr('disabled', true); return validaFormulario('formImprimirListagemCandidatoSolicitacao', new Array(), null)"/>

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
			<td>Vagas preenchidas: <#if solicitacao.qtdVagasPreenchidas?exists>${solicitacao.qtdVagasPreenchidas}</#if></td>
		</tr>
	</table>
	<br/>

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="form" id="form" action="list.action" validate="true" method="POST">
			<@ww.select label="Etapa" name="etapaSeletivaId" list="etapas" listKey="id" listValue="nome" headerKey="" headerValue="Todas" liClass="liLeft"/>
			<@ww.select label="Situação" name="visualizar" list=r"#{'T':'Todas','A':'Aptos', 'I':'Indiferente', 'N':'Não Aptos', 'P':'Contratados/Promovidos'}" />
			<@ww.textfield label="Nome" name="nomeBusca"/>
			<@ww.textfield label="Indicado por" name="indicadoPor"/>
			<@ww.textfield label="Observações do RH" name="observacaoRH"  cssStyle="width: 240px;"/>
			<@ww.hidden name="solicitacao.id" />
			<@ww.hidden id="pagina" name="page"/>

			<button class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;document.form.submit();" accesskey="B"></button>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>
	
	<div id="legendas">
		<span style='background-color: #0000FF;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Aptos
		<span style='background-color: #555;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Indiferente
		<span style='background-color: #F00;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Não Aptos
		<span style='background-color: #008000;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Contratados/Promovidos por esta solicitação
		<span style='background-color: #990066;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Contratados/Promovidos por outras movimentações
	</div>
	
	<#assign jaResponderam = false/>

	<@display.table name="candidatoSolicitacaos" id="candidatoSolicitacao" class="dados" >
		<#if candidatoSolicitacao?exists && candidatoSolicitacao.aptoBoolean>
			<#if candidatoSolicitacao.apto == 'I'>
				<#assign classe="indiferente"/>
			<#else>
				<#assign classe="apto"/>
			</#if>			
		<#else>
			<#assign classe="naoApto"/>
		</#if>

		<#-- decide contratação (se é só candidato) ou promoção (se candidato já é colaborador) -->
		<#if candidatoSolicitacao?exists && candidatoSolicitacao.candidato?exists >
			<#if candidatoSolicitacao.candidato.contratado || candidatoSolicitacao.status == 'A'>
				<#assign classe="contratadoOutros"/>
				<#assign titleContrata="Promover"/>
				<#assign actionContrata="geral/colaborador/preparePromoverCandidato.action?candidato.id=${candidatoSolicitacao.candidato.id}&solicitacao.id=${solicitacao.id}&candidatoSolicitacaoId=${candidatoSolicitacao.id}"/>
			<#else>
				<#assign titleContrata="Contratar"/>
				<#assign actionContrata="geral/colaborador/prepareContrata.action?candidato.id=${candidatoSolicitacao.candidato.id}&solicitacao.id=${solicitacao.id}&candidatoSolicitacaoId=${candidatoSolicitacao.id}"/>
			</#if>
		</#if>
		
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
		
			<@frt.link verifyRole="ROLE_CAND_SOLICITACAO_HISTORICO" href="../historicoCandidato/list.action?candidatoSolicitacao.id=${candidatoSolicitacao.id}" imgTitle="Histórico" imgName="page_user.gif" disabled=solicitacao.encerrada imgTitleDisabled="Esta solicitação já foi encerrada." />
	
			<@frt.link verifyRole="ROLE_CAND_SOLICITACAO_COMPETENCIAS" href="../nivelCompetencia/prepareCompetenciasByCandidato.action?&candidato.id=${candidatoSolicitacao.candidato.id}&faixaSalarial.id=${solicitacao.faixaSalarial.id}&solicitacao.id=${solicitacao.id}" imgTitle="Competências" imgName="competencias.gif" />
	
			<@frt.link verifyRole="ROLE_CAND_SOLICITACAO_VISUALIZARCURRICULO" href="javascript:popup('../candidato/infoCandidato.action?candidato.id=${candidatoSolicitacao.candidato.id}&solicitacao.id=${solicitacao.id}&origemList=CA', 580, 750)" imgTitle="Visualizar Currículo" imgName="page_curriculo.gif" />
	
			<@authz.authorize ifAllGranted="ROLE_CAND_SOLICITACAO_CONTRATAR">
				<#if !solicitacao.encerrada>
					<#assign nomeFormatado=stringUtil.removeApostrofo(candidatoSolicitacao.candidato.nome)>
					
					<#if candidatoSolicitacao?exists && (candidatoSolicitacao.status == 'P' || candidatoSolicitacao.status == 'C')>
						<img border="0" style="opacity:0.3;filter:alpha(opacity=30)" title="${titleAceito}" src="<@ww.url includeParams="none" value="/imgs/contrata_colab.gif"/>">
					<#else>
						<#if candidatoSolicitacao?exists && candidatoSolicitacao.candidato.empresa.id != solicitacao.empresa.id>
							<a href="javascript:contrataCandOutraEmpresa(${candidatoSolicitacao.candidato.id}, ${candidatoSolicitacao.id}, '${nomeFormatado}');">
								<img border="0" title="Contratar candidato de outra empresa?" src="<@ww.url includeParams="none" value="/imgs/contrata_colab.gif"/>">
							</a>
						<#else>
							<a href="javascript:contrataCandidato('${nomeFormatado}', '${titleContrata}', '${actionContrata}');">
								<img border="0" title="${titleContrata}" src="<@ww.url includeParams="none" value="/imgs/contrata_colab.gif"/>">
							</a>
						</#if>
					</#if>
				</#if>
			</@authz.authorize>
			
			<#assign disabledExcluir = solicitacao.encerrada || ((candidatoSolicitacao.etapaSeletiva?exists && candidatoSolicitacao.etapaSeletiva.id?exists) || (candidatoSolicitacao?exists && candidatoSolicitacao.candidato?exists && candidatoSolicitacao.candidato.contratado && candidatoSolicitacao.status != 'A'))/>
			
			<#assign msgDisabledExcluir = ""/>
			<#if disabledExcluir>
				<#if solicitacao.encerrada>
					<#assign msgDisabledExcluir="Esta solicitação já foi encerrada."/>
				<#elseif (candidatoSolicitacao.etapaSeletiva?exists && candidatoSolicitacao.etapaSeletiva.id?exists)> 
					<#assign msgDisabledExcluir="Este candidato já possui históricos. Não é possível removê-lo da seleção."/>
				<#else>
					<#if candidatoSolicitacao.status == 'C'>
						<#assign msgDisabledExcluir="Este candidato foi contratado. Não é possível removê-lo da seleção."/>
					<#else>
						<#assign msgDisabledExcluir="Este candidato foi promovido. Não é possível removê-lo da seleção."/>
					</#if>
				</#if>
			</#if>
			
			<@frt.link verifyRole="ROLE_CAND_SOLICITACAO_EXCLUIR" href="javascript:;" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?solicitacao.id=${solicitacao.id}&candidatoSolicitacao.id=${candidatoSolicitacao.id}'});" imgTitle="Excluir" imgName="delete.gif" disabled=disabledExcluir imgTitleDisabled=msgDisabledExcluir/>
			
			<@frt.link verifyRole="ROLE_CAND_SOLICITACAO_DOCUMENTOANEXO" href="../../geral/documentoAnexo/listCandidato.action?documentoAnexo.origem=C&documentoAnexo.origemId=${candidatoSolicitacao.candidato.id}&solicitacaoId=${solicitacao.id}" imgTitle="Documentos Anexos" imgName="anexos.gif" disabled=solicitacao.encerrada imgTitleDisabled="Esta solicitação já foi encerrada."/>
			
			<@frt.link verifyRole="ROLE_CAND_SOLICITACAO_AVALIACOES" href="javascript:;" onclick="getMenuAvaliacoes(event, ${solicitacao.id}, ${candidatoSolicitacao.candidato.id})" imgTitle="Avaliações da Solicitação" imgName="form.gif" disabled=!(solicitacaoAvaliacaos?exists && (solicitacaoAvaliacaos?size > 0)) imgTitleDisabled="Não há avaliações definidas para essa solicitação"/>
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
		<@display.column property="candidato.contato.foneContatoFormatado" title="Telefone Fixo/Celular" class="${classe}" style="width: 160px;"/>
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

		<#if !etapaSeletivaId?exists>
			<#assign _etapaSeletivaId= "" />
		<#else>
			<#assign _etapaSeletivaId= etapaSeletivaId />		
		</#if>
		
		<#if !nomeBusca?exists>
			<#assign _nomeBusca= "" />
		<#else>
			<#assign _nomeBusca= nomeBusca />		
		</#if>
		
		<#if !indicadoPor?exists>
			<#assign _indicadoPor= "" />
		<#else>
			<#assign _indicadoPor= indicadoPor/>		
		</#if>
		
		<#if !observacaoRH?exists>
			<#assign _observacaoRH= "" />
		<#else>
			<#assign _observacaoRH= observacaoRH/>		
		</#if>
		
		<@authz.authorize ifAllGranted="ROLE_CAND_SOLICITACAO_IMPRIMIR">
			<button onclick="window.location='../candidatoSolicitacao/imprimirListagemCandidatoSolicitacao.action?solicitacao.id=${solicitacao.id}&etapaSeletivaId=${_etapaSeletivaId}&visualizar=${visualizar}&nomeBusca=${_nomeBusca}&indicadoPor=${_indicadoPor}&observacaoRH=${_observacaoRH}'" class="btnImprimir"></button>
		</@authz.authorize>
		
		<@authz.authorize ifAllGranted="ROLE_CAND_SOLICITACAO_TRIAGEM">
			<#if !solicitacao.encerrada>
				<button class="btnTriagem" onclick="window.location='../candidato/prepareBusca.action?solicitacao.id=${solicitacao.id}'"></button>
			</#if>
		</@authz.authorize>
		
		<@authz.authorize ifAllGranted="ROLE_CAND_SOLICITACAO_INSERIRETAPAGRUPO">
			<button onclick="window.location='../historicoCandidato/prepareInsert.action?solicitacao.id=${solicitacao.id}'" class="btnInserirEtapasEmGrupo" accesskey="M"></button>
		</@authz.authorize>
		
		<@authz.authorize ifAllGranted="ROLE_CAND_SOLICITACAO_RESULTADOAVALIACAO">
			<button onclick="$('#popupImpressao').dialog({ modal:true });" class="btnResultadoAvaliacao"></button>
		</@authz.authorize>

		<@authz.authorize ifAllGranted="ROLE_CAND_SOLICITACAO_TRANSFCANDIDATO">
			<button onclick="window.location='prepareMover.action?solicitacao.id=${solicitacao.id}'" class="btnTransferirCandidatos" accesskey="M"></button>
		</@authz.authorize>
		
		<@authz.authorize ifAllGranted="ROLE_CAND_SOLICITACAO_TRIAGEMMODULOEXTERNO">
			<#if !solicitacao.encerrada>
				<button onclick="window.location='listTriagem.action?solicitacao.id=${solicitacao.id}'" class="btnTriagemModuloExterno" accesskey="T"></button>
			</#if>
		</@authz.authorize>

		<@authz.authorize ifAllGranted="ROLE_CAND_SOL_MATRIZ_COMPETENCIA_SOLICITACAO">
			<#if existeCompetenciaParaFaixa>
				<button onclick="window.location='../nivelCompetencia/imprimirMatrizCompetenciasCandidatos.action?faixaSalarial.id=${solicitacao.faixaSalarial.id}&solicitacao.id=${solicitacao.id}'" class="btnMatrizCompetencia"></button>
			<#else>
				<img border="0" title="Não existe Competência configurada para a Faixa Salarial" style="opacity:0.2;filter:alpha(opacity=20);" src="<@ww.url includeParams="none" value="/imgs/btnMatrizCompetencia.gif"/>">
			</#if>
		</@authz.authorize>

		<button onclick="window.location='../solicitacao/list.action'" class="btnVoltar" accesskey="V"></button>
	</div>
	
	<div style="clear: both;"></div>
	
	<div id="popup"></div>
	
	<div id="popupImpressao" title="Selecione a avaliação" style="display:none;">
		<@display.table name="solicitacaoAvaliacaos" id="solicitacaoAvaliacao" class="dados" >
			<@display.column title="Ações" style="width:30px;text-align:center;">
				<a href="imprimeRankingAvaliacao.action?solicitacao.id=${solicitacao.id}&colaboradorQuestionario.avaliacao.id=${solicitacaoAvaliacao.avaliacao.id}" title="Imprimir"><img src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"/></a>
			</@display.column>
			
			<@display.column property="avaliacao.titulo" title="Avaliação" />
		</@display.table>
			
		<div style="text-align:right;">
			<button onclick="$('#popupImpressao').dialog('close');" class="btnFechar"></button>
		</div>
	</div>
	
</body>
</html>