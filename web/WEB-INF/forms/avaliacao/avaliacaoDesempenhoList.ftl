<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />

<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
	
		#resultadoDialog { display: none; }
		#resultadoDialog li { list-style: none; }
		#formDialog { display: none; width: 600px; }
		#liberarEmLoteDialog { display: none; width: 600px; }
		.buscaEmLote li{list-style:none;}
	</style>

	<title>Avaliações de Desempenho</title>
	
	<#include "../ftl/showFilterImports.ftl" />
	<#include "../ftl/mascarasImports.ftl" />
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AvaliacaoDesempenhoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript'>
		function clonar(avaliacaoDesempenhoId, titulo)
		{
			$('#avaliacaoDesempenhoId').val(avaliacaoDesempenhoId);
			$('#formDialog').dialog({ modal: true, width: 530, title: 'Clonar: ' + titulo });
		}
		
		function buscarAvaliacoes()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			AvaliacaoDesempenhoDWR.getAvaliacoesNaoLiberadasByTitulo(createListAvaliacaoDesmpenho, <@authz.authentication operation="empresaId"/>, $('#tituloBuscaEmLote').val());
		}
		
		function createListAvaliacaoDesmpenho(data)
		{
			addChecks('avaliacoesCheck',data)
		}
		
		function submitLiberar(avaliacaoDesempenhoId)
		{
			AvaliacaoDesempenhoDWR.verificaAvaliadosSemCompetencia(avaliacaoDesempenhoId, function(msg) {
				if (msg != '')
				{
					$('#liberaDialog').html(msg).dialog({ 	modal: true, 
															width: 500,
															maxHeight: 360,
															buttons: 
															[
															    {
															        text: "Liberar",
															        click: function() { 
																		$('#btnLiberar').removeAttr("href");
																		window.location='liberar.action?avaliacaoDesempenho.id=' + avaliacaoDesempenhoId;									        
															        }
															    },
															    {
															        text: "Cancelar",
															        click: function() { $(this).dialog("close"); }
															    }
															],
															open: function() {
														        $(this).closest('.ui-dialog').find('.ui-dialog-buttonpane button:eq(1)').focus(); 
															}
														});
				
				} else {
					newConfirm('Deseja liberar esta Avaliação?', function(){
						$('#btnLiberar').removeAttr("href");
						window.location='liberar.action?avaliacaoDesempenho.id=' + avaliacaoDesempenhoId;
					});
				}
			});			
		}
		
		function dialogResultado(avaliacaoDesempenhoId, avaliacaoId) {
			if(avaliacaoId == 0 ){
				document.getElementById("analisarResultado").options[0].setAttribute("disabled", "true");
				document.getElementById("analisarResultado").options[1].setAttribute("selected", "selected");
			}
			var disabled = "true";
			<@authz.authorize ifAllGranted="ROLE_RESPONDER_AVALIACAO_DESEMP_POR_OUTRO_USUARIO">	
				disabled = "false";
			</@authz.authorize>
			
			if( disabled == true){
				document.getElementById("analisarResultado").options[1].setAttribute("disabled", disabled);
				document.getElementById("analisarResultado").options[0].setAttribute("selected", "selected");
			}
				 
			$('#resultadoDialog').dialog({
				title: 'Resultado e Respostas da Avaliação',
				modal: true, 
				height: 145,
				width: 300,
				buttons: [ 	{ text: "Ok", click: function() {
				            if ( $('#analisarResultado').val() == 'RESULTADO' ) {
				            	window.location='prepareResultado.action?avaliacaoDesempenho.id='+avaliacaoDesempenhoId;
				            } else {
				            	window.location='avaliacaoDesempenhoRespostasList.action?avaliacaoDesempenho.id='+avaliacaoDesempenhoId;
				            }
							$(this).dialog("close");
						} },
			    		{ text: "Cancelar", click: function() { $(this).dialog("close"); } } ] 
			});
		}
		
		function submitLiberarEmLote()
		{
			var avaliacaoDesempenhoIds =  $("input[name=avaliacoesCheck]:checked").map(function(){return $(this).val();}).get();
			AvaliacaoDesempenhoDWR.verificaAvaliacoesComAvaliadosSemCompetencia(avaliacaoDesempenhoIds, function(msg) {
				if (msg != '')
				{
					$('#liberaDialog').html(msg).dialog({ 	modal: true, 
															width: 500,
															maxHeight: 360,
															buttons: 
															[
															    {
															        text: "Continuar",
															        click: function() { 
																		$("#formModalLiberar").submit();							        
															        }
															    },
															    {
															        text: "Cancelar",
															        click: function() { $(this).dialog("close"); }
															    }
															],
															open: function() {
														        $(this).closest('.ui-dialog').find('.ui-dialog-buttonpane button:eq(1)').focus(); 
															}
														});
				
				} else {
					$("#formModalLiberar").submit();	
				}
			});			
		}
	</script>
	
	<#if periodoInicial?exists>
		<#assign valueDataIni = periodoInicial?date/>
	<#else>
		<#assign valueDataIni = ""/>
	</#if>

	<#if periodoFinal?exists>
		<#assign valueDataFim = periodoFinal?date/>
	<#else>
		<#assign valueDataFim = ""/>
	</#if>
	
	<#assign validarCampos="return validaFormularioEPeriodo('formBusca', new Array(), new Array('periodoInicial','periodoFinal'), true)"/>
	<#assign validarFormModalLiberar="return validaFormulario('formModalLiberar', new Array('@avaliacoesCheck'), null)"/>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" action="list.action" validate="true" onsubmit="${validarCampos}" method="POST" id="formBusca">
			<div>Período:</div>
			<@ww.datepicker name="periodoInicial" id="periodoInicial" liClass="liLeft" value="${valueDataIni}"  cssClass="mascaraData validaDataIni"/>
			<@ww.label value="a" liClass="liLeft"/>
			<@ww.datepicker name="periodoFinal" id="periodoFinal"  value="${valueDataFim}" cssClass="mascaraData validaDataFim"/>
			
			<@ww.textfield label="Título" name="nomeBusca" id="nomeBusca" cssStyle="width: 500px;"/>
			<@ww.select label="Modelo." name="avaliacaoId" list="avaliacaos" listKey="id" listValue="titulo" headerValue="selecione..." headerKey="" cssStyle="width: 500px;"/>
			<@ww.hidden id="pagina" name="page"/>
			<@ww.hidden id="showFilter" name="showFilter"/>
			<input type="submit" value="" class="btnPesquisar grayBGE" onclick="$('#pagina').val(1);">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>
		
	<@display.table name="avaliacaoDesempenhos" id="avaliacaoDesempenho" class="dados">
		<@display.column title="Ações" class="acao" style="width:195px;">
		
			<a href="prepareUpdate.action?avaliacaoDesempenho.id=${avaliacaoDesempenho.id}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="prepareParticipantes.action?avaliacaoDesempenho.id=${avaliacaoDesempenho.id}"><img border="0" title="Participantes" src="<@ww.url includeParams="none" value="/imgs/usuarios.gif"/>"></a>
			
			<#assign avaliacaoId = "0"/>
			<#if avaliacaoDesempenho.avaliacao?exists && avaliacaoDesempenho.avaliacao.id?exists>
				<#assign avaliacaoId = "${avaliacaoDesempenho.avaliacao.id}" />
			</#if>
			
			<#if avaliacaoDesempenho.liberada>
				<a href="javascript:newConfirm('Deseja bloquear esta Avaliação?', function(){window.location='bloquear.action?avaliacaoDesempenho.id=${avaliacaoDesempenho.id}'});"><img border="0" title="Bloquear" src="<@ww.url includeParams="none" value="/imgs/bloquear.gif"/>"></a>
				<a href="javascript:newConfirm('Deseja enviar e-mail de lembrete para os colaboradores que ainda não respoderam esta avaliação desempenho?', function(){window.location='enviarLembrete.action?avaliacaoDesempenho.id=${avaliacaoDesempenho.id}'});"><img border="0" title="Enviar e-mail de Lembrete" src="<@ww.url includeParams="none" value="/imgs/icon_email.gif"/>"></a>
			<#else>
				<a href="javascript:submitLiberar(${avaliacaoDesempenho.id});" id="btnLiberar"><img border="0" title="Liberar" src="<@ww.url includeParams="none" value="/imgs/liberar.gif"/>"></a>
				<img border="0" title="Avaliação bloqueada" src="<@ww.url includeParams="none" value="/imgs/icon_email.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			</#if>
						
			<#if avaliacaoDesempenho.possuiResposta>
				<a href="javascript:;" onclick="dialogResultado(${avaliacaoDesempenho.id}, ${avaliacaoId})"><img border="0" title="Resultado e Respostas da Avaliação" src="<@ww.url includeParams="none" value="/imgs/grafico_pizza.gif"/>"></a>
			<#else>
				<img border="0" title="Avaliação sem respostas" src="<@ww.url includeParams="none" value="/imgs/grafico_pizza.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			</#if>
				
			<#if !avaliacaoDesempenho.avaliacao?exists || (avaliacaoDesempenho.avaliacao?exists && avaliacaoDesempenho.avaliacao.avaliarCompetenciasCargo) >
				<a href="prepareCompetencias.action?avaliacaoDesempenho.id=${avaliacaoDesempenho.id}"><img border="0" title="Competências" src="<@ww.url includeParams="none" value="/imgs/competencias.gif"/>"></a>
			<#else>
				<img border="0" title="Competências" src="<@ww.url includeParams="none" value="/imgs/competencias.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			</#if>
				
			<a href="javascript:;" onclick="javascript:clonar(${avaliacaoDesempenho.id}, '${avaliacaoDesempenho.titulo}')"><img border="0" title="Clonar" src="<@ww.url includeParams="none" value="/imgs/clonar.gif"/>"></a>
			<#if avaliacaoDesempenho.avaliacao?exists && avaliacaoDesempenho.avaliacao.id?exists>
				<a href="../modelo/imprimir.action?avaliacao.id=${avaliacaoDesempenho.avaliacao.id}"><img border="0" title="Imprimir Modelo da Avaliação" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
				<a href="../modelo/imprimir.action?avaliacao.id=${avaliacaoDesempenho.avaliacao.id}&imprimirFormaEconomica=true"><img border="0" title="Imprimir Modelo da Avaliação em formato econômico" src="<@ww.url includeParams="none" value="/imgs/iconPrint.gif"/>"></a>
			<#else>
				<img border="0" title="Imprimir Modelo da Avaliação" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"style="opacity:0.2;filter:alpha(opacity=20);">
				<img border="0" title="Imprimir Modelo da Avaliação em formato econômico" src="<@ww.url includeParams="none" value="/imgs/iconPrint.gif"/>"style="opacity:0.2;filter:alpha(opacity=20);">
			</#if>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?avaliacaoDesempenho.id=${avaliacaoDesempenho.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column title="Título" property="titulo" />
		<@display.column title="Período" property="periodoFormatado" />
		<@display.column title="Modelo" property="avaliacao.titulo" />
	</@display.table>
	
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="formBusca"/>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'"></button>
		<button class="btnLiberarAvalEmLote" onclick="$('#liberarEmLoteDialog').dialog({ modal: true, width: 530 });">
	</div>
	
	<div id="resultadoDialog" title="Configurar Informações para Impressão">
		<@ww.select label="Analisar" name="analisarResultado" id="analisarResultado" list=r"#{'RESULTADO':'Resultado da Avaliação','RESPOSTAS':'Respostas das Avaliação'}"/>
	</div>
	
	<div id="formDialog">
		<@ww.form name="formModal" id="formModal" action="clonar.action" method="POST">
			<@frt.checkListBox label="Selecione as empresas para as quais deseja clonar esta avaliação" name="empresasCheck" list="empresasCheckList" form="document.getElementById('formModal')" filtro="true"/>
			* Caso nenhuma empresa seja selecionada, a avaliação será clonada apenas para a empresa <@authz.authentication operation="empresaNome"/><br>
			<@ww.hidden name="avaliacaoDesempenho.id" id="avaliacaoDesempenhoId"/>
			<br />
			<@ww.checkbox label="Incluir avaliados e avaliadores" name="clonarParticipantes" labelPosition="left"/>
			<button class="btnClonar" type="submit"></button>
		</@ww.form>
	</div>
	
	<div id="liberarEmLoteDialog"  title='Liberar Avaliações em lote'>
		<div class="buscaEmLote">
			<@ww.textfield label="Título" name="tituloBuscaEmLote" id="tituloBuscaEmLote" liClass="liLeft" cssStyle="width: 350px;"/>
			<button onclick="return buscarAvaliacoes();" class="btnPesquisar">
		</div>
		<div style="clear: both"/>

		<@ww.form name="formModalLiberar" id="formModalLiberar" onsubmit="${validarFormModalLiberar}" action="liberarEmLote.action" method="POST">
			<@frt.checkListBox label="Avaliações" name="avaliacoesCheck" list="avaliacoesCheckList" form="document.getElementById('formModalLiberar')" filtro="true"/>
			<input type="button" value="" onclick="submitLiberarEmLote();" class="btnLiberar">
		</@ww.form>
	</div>
	<div id="liberaDialog" title="Confirmar liberação"></div>
</body>
</html>
