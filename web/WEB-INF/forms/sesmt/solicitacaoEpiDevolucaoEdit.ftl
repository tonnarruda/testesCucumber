<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	
	<#if solicitacaoEpiItemDevolucao?exists && solicitacaoEpiItemDevolucao.id?exists>
		<title>Editar Devolucao de EPI</title>
		<#assign formAction="updateDevolucao.action"/>
	<#else>
		<title>Inserir Devolucao de EPI</title>
		<#assign formAction="insertDevolucao.action"/>
	</#if>
	
	<#if solicitacaoEpiItemDevolucao?exists && solicitacaoEpiItemDevolucao.dataDevolucao?exists>
			<#assign data = solicitacaoEpiItemDevolucao.dataDevolucao?string('dd/MM/yyyy')>
		<#else>
			<#assign data = "">
	</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('dataDevolucao','qtdDevolvida'), new Array('dataDevolucao'))"/>
	<#include "../ftl/mascarasImports.ftl" />
	
	<#-- Não apague, da um erro que nem o babau achou, e ainda mais nem sempre acontece, logo não apague (erro no stack.setValue("#datepicker_js_included", true)) -->
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/webwork/jscalendar/" encode='false'/>calendar.js"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/webwork/jscalendar/lang/" encode='false'/>calendar-${parameters.language?default("en")}.js"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/webwork/jscalendar/" encode='false'/>calendar-setup.js"></script>
	<#-- Não apague, da um erro que nem o babau achou, e ainda mais nem sempre acontece, logo não apague (erro no stack.setValue("#datepicker_js_included", true)) -->
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" action="${formAction}" method="POST">
		<@ww.datepicker label="Data da Devolução" name="solicitacaoEpiItemDevolucao.dataDevolucao" id="dataDevolucao" required="true" value="${data}" cssClass="mascaraData"/>
		<@ww.textfield label="Qtd. Devolvida" name="solicitacaoEpiItemDevolucao.qtdDevolvida" id="qtdDevolvida" required="true"  onkeypress="return(somenteNumeros(event,''));" cssStyle="width: 25px;" maxLength="3"  />
		<@ww.textarea label="Observação" name="solicitacaoEpiItemDevolucao.observacao" maxLength="100"/>
		<@ww.hidden name="solicitacaoEpi.id" />
		<@ww.hidden name="solicitacaoEpiItem.id" />
		<@ww.hidden name="solicitacaoEpiItemDevolucao.id" />
		<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos}" class="btnGravar"></button>
		<button onclick="window.location='prepareEntrega.action?solicitacaoEpi.id=${solicitacaoEpi.id}'" class="btnVoltar"></button>
	</div>
</body>
</html>