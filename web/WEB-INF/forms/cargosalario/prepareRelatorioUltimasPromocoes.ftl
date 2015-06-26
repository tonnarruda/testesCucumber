<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Relatório de Colaboradores sem Reajuste Salarial</title>

	<#include "../ftl/mascarasImports.ftl" />
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

	<#if dataBase?exists>
		<#assign valueData = dataBase?date/>
	<#else>
		<#assign valueData = ""/>
	</#if>

	<#assign validarCampos="return validaFormulario('form', new Array('dataBase'), new Array('dataBase'), false)"/>
</head>

<body>
	<@ww.actionmessage />

	<@ww.form name="form" action="imprimirRelatorioUltimasPromocoes.action" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.datepicker label="Data de Referência" name="dataBase" id="dataBase" value="${valueData}" cssClass="mascaraData" required="true"/>
		
		Não recebeu reajuste salarial a 
		<@ww.textfield theme="simple" name="mesesSemReajuste" id="mesesSemReajuste" cssStyle="width:40px; text-align:right;" maxLength="5" onkeypress = "return(somenteNumeros(event,','));"/> 
		meses
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio"></button>
	</div>
</body>
</html>