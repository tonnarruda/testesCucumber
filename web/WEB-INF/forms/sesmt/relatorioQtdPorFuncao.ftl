<html>
<head>
	<@ww.head/>

	<#include "../ftl/mascarasImports.ftl" />
	
	<title>Relatório - Distribuição de Colaboradores por Função</title>
	<#assign formAction="gerarRelatorioQtdPorFuncao.action"/>
	
	<#assign date = "" />
    <#if data?exists>
		<#assign date = data?date/>
	</#if>

</head>
<body>
	<@ww.actionerror />
	
	<#assign validarCampos="return validaFormulario('form', new Array('data', 'estabelecimento'), new Array('data'))"/>
	
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
		<@ww.datepicker label="Data" id="data" name="data" required="true" cssClass="mascaraData" value="${date}" />
		<@ww.select label="Estabelecimento" id="estabelecimento" name="estabelecimento.id" required="true" list="estabelecimentos" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="" cssStyle="width:240px;"/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos}" class="btnRelatorio"></button>
	</div>
</body>
</html>