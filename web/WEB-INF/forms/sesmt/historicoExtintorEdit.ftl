<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');
	</style>
	
	<#include "../ftl/mascarasImports.ftl" />
	
	<@ww.head/>
	
	<#if historicoExtintor.id?exists>
		<title>Edição de Histórico do Extintor ${extintor.descricao}</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
	<#else>
		<title>Inserir Histórico para o Extintor ${extintor.descricao}</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
	</#if>
	<#assign validarCampos="return validaFormulario('form', new Array('dataHist','estabelecimento','localizacao'), new Array('dataHist'))"/>

	<#if historicoExtintor.data?exists>
		<#assign data = historicoExtintor.data>
	<#else>
		<#assign data = "">
	</#if>
	
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>
	<script type="text/javascript">
		var localizacoes = [${localizacoes}];

		$(function() {
			$("#localizacao").autocomplete(localizacoes);
		});
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.datepicker label="A partir de" name="historicoExtintor.data" id="dataHist" required="true" value="${data}" cssClass="mascaraData"/>
		<@ww.select label="Estabelecimento" name="historicoExtintor.estabelecimento.id" id="estabelecimento" required="true" list="estabelecimentos" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..."/>
		<@ww.textfield label="Localização" name="historicoExtintor.localizacao" id="localizacao" required="true" maxLength="50"/>
		
		<@ww.hidden name="historicoExtintor.id" />
		<@ww.hidden name="historicoExtintor.extintor.id" />
		<@ww.hidden name="extintor.id" />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}"></button>
		<button onclick="window.location='../extintor/prepareUpdate.action?extintor.id=${extintor.id}'" class="btnCancelar" accesskey="V"></button>
	</div>
</body>
</html>