<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/SolicitacaoExameDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

	<style type="text/css">
	<#-- não modifique os nomes. -->
	.NORMAL
	{
		color: #002EB8 !important;
	}
	.ANORMAL
	{
		color: #EF030F !important;
	}
	</style>
	
	<#include "../ftl/mascarasImports.ftl" />

	<script>
	
		function filtrarOpcao()
		{
			value = document.getElementById('vinculo').value;
			if (value == 'CANDIDATO')
				document.getElementById('divMatricula').style.display = 'none';
			else if (value == 'COLABORADOR')
				document.getElementById('divMatricula').style.display = '';
			else if (value == 'TODOS')
				document.getElementById('divMatricula').style.display = 'none';
		}
		
		function marcarComoNormal(solicitacaoExameId)
		{
			newConfirm('Marcar o resultado de todos os exames não informados como normal?', function(){
				SolicitacaoExameDWR.marcarNaoInformadosComoNormal(mudaImagem, solicitacaoExameId);
			});
		}
		
		function mudaImagem(data)
		{
			if(data != null)
			{
				for(i = 0; i < data.length; i++)
				{
					document.getElementById(data[i]).style.color = "#002EB8";
				}
			}
		}
	</script>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#assign validarCampos="return validaFormulario('form', null, new Array('dataIni','dataFim'), true)"/>

	<#include "../ftl/showFilterImports.ftl" />

	<#if dataIni?exists >
		<#assign dateIni = dataIni?date/>
	<#else>
		<#assign dateIni = ""/>
	</#if>
	
	<#if dataFim?exists>
		<#assign dateFim = dataFim?date/>
	<#else>
		<#assign dateFim = ""/>
	</#if>

	<title>Solicitações/Atendimentos Médicos</title>

</head>
<body>

	<#include "../util/topFiltro.ftl" />
	<@ww.form name="form" id="form" action="list.action" onsubmit="${validarCampos}" method="POST">
		
		Período:<br>
		<@ww.datepicker name="dataIni" id="dataIni"  value="${dateIni}" liClass="liLeft" cssClass="mascaraData"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="dataFim" id="dataFim" value="${dateFim}" cssClass="mascaraData" />

		<@ww.select label="Vínculo" id="vinculo" name="vinculo" list=r"#{'TODOS':'Todos','CANDIDATO':'Candidato','COLABORADOR':'Colaborador'}" onchange="filtrarOpcao();"/>

		<@ww.div id="divMatricula">
			<@ww.textfield label="Matrícula" name="matriculaBusca" id="matriculaBusca" cssStyle="width: 60px;"/>
		</@ww.div>
		
		<@ww.textfield label="Nome" name="nomeBusca" id="nomeBusca" cssStyle="width: 260px;"/>

		<@ww.select label="Motivo do Atendimento" name="motivo" id="motivoExame" list="motivos" headerKey="" headerValue="Selecione..." />
		
		<@frt.checkListBox name="examesCheck" label="Exames" list="examesCheckList" filtro="true"/>
		
		<@ww.select label="Resultado do Exame" id="resultado" name="resultado" list=r"#{'':'Todos','NORMAL':'Normal','ANORMAL':'Alterado','NAO_REALIZADO':'Não Informado'}" />
		
		<@ww.hidden id="pagina" name="page"/>
		<input type="submit" value="" onclick="document.getElementById('pagina').value = 1;" class="btnPesquisar grayBGE" />
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br/>

	<@ww.actionerror />
	<@ww.actionmessage />
	
	&nbsp;&nbsp;<span style='background-color: #002EB8;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Normal
	&nbsp;&nbsp;<span style='background-color: #EF030F;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Alterado
	&nbsp;&nbsp;<span style='background-color: #555;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Não Informado
	
	<br/><br/>

	<@display.table name="solicitacaoExames" id="solicitacaoExame" class="dados" >

		<#if solicitacaoExame.colaborador?exists>
			<#assign nomeColaborador = solicitacaoExame.colaborador.nomeComercialDesligado />
		<#elseif solicitacaoExame.candidato?exists>
			<#assign nomeColaborador = solicitacaoExame.candidato.nome />
		</#if>
	
		<@display.column title="Ações" class="acao" style="vertical-align: top;width: 120px;">
			<#if solicitacaoExame.semExames>
				<img border="0" title="Não há exames para esta solicitação/atendimento" src="<@ww.url includeParams="none" value="/imgs/cliper.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			<#else>
				<a href="prepareResultados.action?solicitacaoExame.id=${solicitacaoExame.id}&solicitacaoExame.colaboradorNome=${nomeColaborador}"><img border="0" title="Resultados" src="<@ww.url value="/imgs/cliper.gif"/>"></a>
			</#if>
			<a href="prepareUpdate.action?solicitacaoExame.id=${solicitacaoExame.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?solicitacaoExame.id=${solicitacaoExame.id}&solicitacaoExame.data=${solicitacaoExame.data}&&solicitacaoExame.ordem=${solicitacaoExame.ordem}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			
			<#if solicitacaoExame.semExames>
				<img border="0" title="Não há exames para esta solicitação/atendimento" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				<img border="0" title="Não há exames para esta solicitação/atendimento" src="<@ww.url includeParams="none" value="/imgs/check.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			<#else>
				<a href="imprimirSolicitacaoExames.action?solicitacaoExame.id=${solicitacaoExame.id}"><img border="0" title="Imprimir Solicitação de Exames" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
				<a><img onclick="marcarComoNormal('${solicitacaoExame.id}');" border="0" title="Marcar o resultado de todos os exames não informados como normal" src="<@ww.url includeParams="none" value="/imgs/check.gif"/>" style="cursor:pointer;"></a>
			</#if>
			
			<#if solicitacaoExame.motivo != motivoCONSULTA && solicitacaoExame.motivo != motivoATESTADO>
				<a href="../exame/imprimirAso.action?solicitacaoExame.id=${solicitacaoExame.id}"><img border="0" title="Imprimir ASO" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
			<#else>
				<img border="0" title="Não é possível imprimir ASO para este tipo de atendimento" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">				
			</#if>
		</@display.column>
		
		<@display.column property="dataFormatada" style="width: 70px; vertical-align: top;" title="Data"/>
		
		<@display.column property="ordem" style="width: 30px; vertical-align: top; text-align: right;" title="Ordem"/>

		<@display.column style="width: 330px;" title="Nome" style="vertical-align: top;">
			${nomeColaborador}
		</@display.column>

		<@display.column title="Vínculo/Cargo" style="vertical-align: top;">
			<#if solicitacaoExame.colaborador?exists && solicitacaoExame.cargoNome?exists >
				${solicitacaoExame.cargoNome}
			</#if>
			<#if solicitacaoExame.candidato?exists>
				Candidato
			</#if>
		</@display.column>

		<@display.column property="motivoDic" title="Motivo" style="width: 140px;vertical-align: top;"/>
		<@display.column title="Exames" style="width: 200px;vertical-align: top;">
			<#list exameSolicitacaoExames as exameSolicitacaoExame>
				<#if exameSolicitacaoExame.solicitacaoExame.id == solicitacaoExame.id>
					<span id="${solicitacaoExame.id}_${exameSolicitacaoExame.exame.id}" class="${exameSolicitacaoExame.resultado}">
						${exameSolicitacaoExame.exame.nome}<br/>
					</span>
				</#if>
			</#list>
		</@display.column>
		
		<@display.footer>
		  	<tr>
				<td colspan="7" >Total : ${totalSize} solicitações</td>
	  		</tr>
		</@display.footer>
	</@display.table>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="form"/>

	<div class="buttonGroup">
		<button onclick="window.location='prepareInsert.action?nomeBusca='+escape($('#nomeBusca').val()) + '&vinculo=' +$('#vinculo').val()+'&primeiraExecucao=true'" accesskey="N" class="btnInserir"></button>
	</div>
	<script>
		filtrarOpcao();
	</script>
</body>
</html>