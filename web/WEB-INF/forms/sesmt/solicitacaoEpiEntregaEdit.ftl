<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	
	<#if solicitacaoEpiItemEntrega?exists && solicitacaoEpiItemEntrega.id?exists>
		<title>Editar Entrega de EPI</title>
		<#assign formAction="updateEntrega.action"/>
	<#else>
		<title>Inserir Entrega de EPI</title>
		<#assign formAction="insertEntrega.action"/>
	</#if>
	
	<#if solicitacaoEpiItemEntrega?exists && solicitacaoEpiItemEntrega.dataEntrega?exists>
			<#assign data = solicitacaoEpiItemEntrega.dataEntrega?string('dd/MM/yyyy')>
		<#else>
			<#assign data = "">
	</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('dataEntrega','qtdEntregue','epiHistoricoId'), new Array('dataEntrega'))"/>
	<#include "../ftl/mascarasImports.ftl" />
	
	<#-- Não apague, da um erro que nem o babau achou, e ainda mais nem sempre acontece, logo não apague (erro no stack.setValue("#datepicker_js_included", true)) -->
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/webwork/jscalendar/" encode='false'/>calendar.js"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/webwork/jscalendar/lang/" encode='false'/>calendar-${parameters.language?default("en")}.js"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/webwork/jscalendar/" encode='false'/>calendar-setup.js"></script>
	<#-- Não apague, da um erro que nem o babau achou, e ainda mais nem sempre acontece, logo não apague (erro no stack.setValue("#datepicker_js_included", true)) -->

	<script type="text/javascript">
		$(function() {
			<#if solicitacaoEpiItemEntrega?exists && solicitacaoEpiItemEntrega.itensEntregues>	
				$('#dataEntrega').css('background', '#F6F6F6');
				$('#qtdEntregue').css('background', '#F6F6F6');
				$('#dataEntrega_button').remove();
			</#if>					
		});
	</script>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" action="${formAction}" method="POST">
		<#if solicitacaoEpiItemEntrega?exists && solicitacaoEpiItemEntrega.itensEntregues>
			<@ww.datepicker label="Data da Entrega" name="solicitacaoEpiItemEntrega.dataEntrega" id="dataEntrega" disabled="true" value="${data}" cssClass="mascaraData"/>
			<@ww.textfield label="Qtd. Entregue" name="solicitacaoEpiItemEntrega.qtdEntregue" id="qtdEntregue" disabled="true" cssStyle="width: 25px;"/>
			<@ww.hidden name="solicitacaoEpiItemEntrega.dataEntrega" />
			<@ww.hidden name="solicitacaoEpiItemEntrega.qtdEntregue" />
			<@ww.hidden name="solicitacaoEpiItemEntrega.itensEntregues" />
		<#else>
			<@ww.datepicker label="Data da Entrega" name="solicitacaoEpiItemEntrega.dataEntrega" id="dataEntrega" required="true" value="${data}" cssClass="mascaraData"/>
			<@ww.textfield label="Qtd. Entregue" name="solicitacaoEpiItemEntrega.qtdEntregue" id="qtdEntregue" required="true"  onkeypress="return(somenteNumeros(event,''));" cssStyle="width: 25px;" maxLength="3"  />
		</#if>
		
		<@ww.select label="Histórico (data - CA - validade de uso)" name="solicitacaoEpiItemEntrega.epiHistorico.id" id="epiHistoricoId" required="true" list="epiHistoricos" headerKey="" headerValue="Selecione..." cssStyle="width: 300px;" listKey="id" listValue="descricao"/>
		<@ww.hidden name="solicitacaoEpi.id" />
		<@ww.hidden name="solicitacaoEpiItem.id" />
		<@ww.hidden name="solicitacaoEpiItemEntrega.id" />
		<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos}" class="btnGravar"></button>
		<button onclick="window.location='prepareEntrega.action?solicitacaoEpi.id=${solicitacaoEpi.id}'" class="btnVoltar"></button>
	</div>
</body>
</html>