<html>
<head>
<@ww.head/>

<title>Importação de Afastamentos</title>
	<#assign validarCampos="return validaFormulario('form', new Array('arquivo'), null)"/>

	<#if dataDe?exists>
		<#assign dataDeTmp = dataDe?date/>
	<#else>
		<#assign dataDeTmp = ""/>
	</#if>
	<#if dataAte?exists>
		<#assign dataAteTmp = dataAte?date/>
	<#else>
		<#assign dataAteTmp = ""/>
	</#if>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<fieldset class="fieldsetPadrao" style="width:860px;">
		<legend>Formato válido do Arquivo de Importação</legend>
		<span style="font-size:12px;">Cód. Empregado;Nome Completo;Nome Comercial;Doença(Motivo Afastamento);Data Inicial;Data Final;Médico;CRM;CID;Observação</span>
	</fieldset>
	<br/>

	<@ww.form name="form" action="carregarAfastamentos.action" validate="true" onsubmit="${validarCampos}" method="POST" enctype="multipart/form-data">
		<@ww.file label="Arquivo CSV" name="arquivo" id="arquivo"/>
		<@ww.token/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnCarregar"></button>
	</div>
</body>
</html>