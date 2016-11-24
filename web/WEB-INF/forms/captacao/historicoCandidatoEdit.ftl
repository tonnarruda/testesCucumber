<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
<#if historicoCandidato.id?exists>
	<title>Editar Histórico</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Histórico do Candidato</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

<#if !candidatoSol?exists || !candidatoSol.id?exists>
	<#assign formAction="insertGrupoCandidatos.action"/>
</#if>


<#assign validarCampos="enviaForm()"/>

<#if historicoCandidato?exists>
	<#if historicoCandidato.data?exists>
		<#assign data = historicoCandidato.dataFormatada/>
	<#else>
		<#assign data = ""/>
	</#if>
</#if>
	
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/HistoricoCandidatoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');
	</style>
		
<#include "../ftl/mascarasImports.ftl" />
<script type='text/javascript'>

		function getCandidatoAptoByEtapa(etapaId, solicitacaoId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			HistoricoCandidatoDWR.getCandidatoAptoByEtapa(createListCandidatos, etapaId, solicitacaoId);
		}

		function createListCandidatos(data)
		{
			addChecks('candidatosCheck',data)
		}

		function liberarIndisp()
		{
			if(document.getElementById('apto').value == 'S' || document.getElementById('apto').value == 'T')
				document.getElementById('indisp').disabled = true;
			else
				document.getElementById('indisp').disabled = false;
		}

		function enviaForm()
		{
			var camposObrigatorios = new Array('fase','data','horaIni','horaFim','resp');
			<#if !candidatoSol?exists || !candidatoSol.id?exists>
				camposObrigatorios.push('@candidatosCheck');
			</#if>
		
				if(validaFormulario('form', camposObrigatorios, new Array('data','horaIni','horaFim'), true))
				{
					if($('#horaIni').val() <= $('#horaFim').val())
					{
						document.form.submit();
					}  
					else
					{
						$('#horaFim').css("background-color", "#FF6347");
						jAlert("Hora Final menor que Hora Inicial.");
					}
				}
		}

		$(document).ready(function($)
		{
		
			<#if historicoCandidato.id?exists>
				$('#fase').val(${historicoCandidato.etapaSeletiva.id});
			</#if>

			$("#resp").autocomplete(${responsaveis});
			
			$(".mascaraHora").blur(function() {
				$(this).val( $(this).val().replace(/ /g, '0') );
			});
		});
		
</script>
</head>
<body >
<table width="100%">
	<tr>
		<td>Área: <#if solicitacao.areaOrganizacional?exists && solicitacao.areaOrganizacional.nome?exists>${solicitacao.areaOrganizacional.nome}</#if></td>
		<td>Cargo: <#if solicitacao.faixaSalarial?exists && solicitacao.faixaSalarial.cargo?exists && solicitacao.faixaSalarial.cargo.nome?exists>${solicitacao.faixaSalarial.cargo.nome}</#if></td>
		<td>Solicitante: <#if solicitacao.solicitante?exists && solicitacao.solicitante.nome?exists>${solicitacao.solicitante.nome}</#if></td>
		<td>Vagas: <#if solicitacao.quantidade?exists>${solicitacao.quantidade}</#if></td>
	</tr>
	<#if candidatoSol?exists && candidatoSol.candidato?exists && candidatoSol.candidato.nome?exists>
		<tr>
			<td colspan="4">
			<b>${candidatoSol.candidato.nome}</b>
			</td>
		</tr>
	</#if>
</table>
<br/>
<@ww.actionerror />

	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<#if !candidatoSol?exists || !candidatoSol.id?exists>
			<li>
				<@ww.div cssClass="divFiltro">
					<ul>
						<@ww.select label="Filtrar Candidatos por Etapa" name="etapaSeletivaIdFiltro" id="etapaSeletiva" list="etapas" onchange="javascript:getCandidatoAptoByEtapa(this.value, ${solicitacao.id})" listKey="id" listValue="nome"  headerKey="-1" headerValue="" />
						<@frt.checkListBox label="Candidatos*" name="candidatosCheck" list="candidatosCheckList" id="candidatosCheck" filtro="true"/>
					</ul>
				</@ww.div>
			</li>
			<br>
		</#if>
		
		<li class="liLeft" id="wwgrp_fase">
			<div class="wwlbl" id="wwlbl_fase">
				<label class="desc" for="fase"> Etapa:<span class="req">* </span></label>
			</div> 
			<div class="wwctrl" id="wwctrl_fase">
				<select style="width: 200px;" id="fase" name="historicoCandidato.etapaSeletiva.id">
					<#list etapas as etapa >
						<#assign mudaCor=""/>
					
						<#list etapaCargos as etapaCargo >
							<#if etapaCargo.id == etapa.id>
								<#assign mudaCor="color:blue"/>
								<#break/>
							</#if>
						</#list>
						<option style="${mudaCor}" value="${etapa.id}">${etapa.nome}</option>
					</#list>
				</select>
			</div>
		</li>
		
		<#--Veja complemento destes datepickers no final da página-->
		<@ww.datepicker label="Data" name="historicoCandidato.data" id="data" required="true"  liClass="liLeft" cssClass="mascaraData" value = "${data}" />
		<@ww.textfield label="Início" required="true" name="historicoCandidato.horaIni" id="horaIni" cssStyle="width: 38px;" liClass="liLeft" cssClass="mascaraHora"/>
		<@ww.textfield label="Fim" required="true" name="historicoCandidato.horaFim" id="horaFim" cssStyle="width: 38px;" cssClass="mascaraHora"/>
		<@ww.textfield required="true" label="Responsável" name="historicoCandidato.responsavel" id="resp" liClass="liLeft" cssStyle="width: 260px;" maxLength="100" />
		<@ww.select label="Apto" name="historicoCandidato.apto" id="apto"  list="aptos" liClass="liLeft" onchange="javascript:liberarIndisp()"/>
		<@ww.select label="Indisponível" name="blacklist" id="indisp" list=r"#{true:'Sim', false:'Não'}" liClass="liLeft" cssStyle="width: 82px;"/>
		<@ww.select label="Exibir na Agenda" name="historicoCandidato.exibirNaAgenda" id="exibiNaAgenda" list=r"#{true:'Sim', false:'Não'}" cssStyle="width: 113px;"/>
		<@ww.textarea label="Observação" name="historicoCandidato.observacao" cssStyle="width: 599px;"/>

		<@ww.hidden name="candidatoSol.id"/>
		<@ww.hidden name="solicitacao.id"/>
		<@ww.hidden name="solicitacao.data"/>
		<@ww.hidden name="historicoCandidato.id"/>
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}">
		</button>
		<#if candidatoSol?exists && candidatoSol.id?exists>
			<button onclick="window.location='list.action?candidatoSolicitacao.id=${candidatoSol.id}'" class="btnCancelar"></button>
		<#else>
			<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}'" class="btnCancelar"></button>
		</#if>
	</div>

	<script type="text/javascript">
		liberarIndisp();
	</script>

</body>
</html>