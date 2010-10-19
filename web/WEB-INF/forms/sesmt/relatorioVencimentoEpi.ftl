<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<#include "../ftl/mascarasImports.ftl" />
	<title>EPIs com Prazo a Vencer</title>

	<#if venc?exists>
		<#assign data = venc?date/>
	<#else>
		<#assign data = ""/>
	</#if>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#assign validarCampos="return validaFormulario('form', new Array('data'), new Array('data'))"/>
	<@ww.form name="form" action="relatorioVencimentoEpi.action" onsubmit="${validarCampos}" method="POST" >
		<@ww.datepicker id="data" name="venc" value="${data}" cssClass="mascaraData" label="Data" required="true"/>
		<@ww.select label="Agrupar por" id="agruparPor" name="agruparPor" list=r"#{'E':'Epi','C':'Colaborador'}" />
		<@frt.checkListBox name="tipoEPICheck" id="tipoEPICheck" label="Categorias de EPI" list="tipoEPICheckList" />
		<@ww.checkbox label="Exibir Vencimento do CA" id="exibirVencimentoCA" name="exibirVencimentoCA" labelPosition="left"/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos}" class="btnRelatorio"></button>
	</div>
</body>
</html>