<html>
<head>
<@ww.head/>
	<#include "../ftl/mascarasImports.ftl" />
	<title>EPIs com CA a Vencer</title>

	<#if venc?exists>
		<#assign data = venc?date/>
	<#else>
		<#assign data = ""/>
	</#if>

</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />

<#assign validarCampos="return validaFormulario('form', new Array('data'), null)"/>
<@ww.form name="form" action="imprimirVencimentoCa.action" onsubmit="${validarCampos}" method="POST" >
	<@ww.datepicker id="data" name="venc" value="${data}" cssClass="mascaraData" label="Data" required="true"/>
</@ww.form>

<div class="buttonGroup">
	<button onclick="${validarCampos}" class="btnRelatorio">
	</button>
</div>

</body>
</html>