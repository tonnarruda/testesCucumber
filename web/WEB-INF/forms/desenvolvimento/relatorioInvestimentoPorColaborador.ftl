<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>

<@ww.head/>

	<title>Investimentos de T&D por Colaborador</title>

	<#include "../ftl/mascarasImports.ftl" />
	<#assign validarCampos="return validaFormulario('form', new Array('inicio','fim','colaborador'), new Array('inicio','fim'))"/>
	
	<#if dataIni?exists>
		<#assign inicio=dataIni?date />
	<#else>
		<#assign inicio="" />
	</#if>
	<#if dataFim?exists>
		<#assign fim=dataFim?date />
	<#else>
		<#assign fim="" />
	</#if>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" action="imprimirRelatorioInvestimentoPorColaborador.action" onsubmit="${validarCampos}" method="POST">

		<@ww.datepicker label="Turma iniciada entre" required="true" value="${inicio}" name="dataIni" id="inicio" cssClass="mascaraData validaDataIni" after="e" liClass="liLeft"/>
		<@ww.datepicker label="" value="${fim}" name="dataFim" id="fim" cssClass="mascaraData validaDataFim"/>

		<@ww.select id="realizada" label="Turmas Realizadas" name="realizada" list=r"#{'T':'Todas','S':'Sim','N':'Não'}" />
		
		<@ww.select label="Colaborador" name="colaborador.id" id="colaborador" required="true" list="colaboradors" listKey="id" listValue="nomeCpfMatricula" headerKey="" headerValue="Selecione..." cssStyle="width:600px;"/>
		
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio"></button>
	</div>
</body>
</html>