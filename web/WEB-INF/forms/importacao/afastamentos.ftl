<html>
<head>
<@ww.head/>

<#assign validarCampos="return imprimir();"/>
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
		<ul>
		<legend>Formato válido do Arquivo de Importação</legend>
		<br/>
		Cód. Empregado;Nome Completo;Nome Comercial;Doença(Motivo Afastamento);Data Inicial;Data Final;Médico;Descrição do Tipo
		</ul>
	</fieldset>
	<br/>

	<@ww.form name="form" action="importarAfastamentos.action" validate="true" onsubmit="${validarCampos}" method="POST" enctype="multipart/form-data">
		<@ww.file label="Arquivo CSV" name="arquivo" id="arquivo"/>
		<@ww.token/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnImportar" accesskey="I">
		</button>
	</div>
</body>
</html>