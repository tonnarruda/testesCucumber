<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Relatório de Colaboradores Sem Reajuste</title>

	<#include "../ftl/mascarasImports.ftl" />
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

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
		<@ww.datepicker label="Desde o dia" name="dataBase" id="dataBase" value="${valueData}" cssClass="mascaraData"/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio"></button>
	</div>
</body>
</html>