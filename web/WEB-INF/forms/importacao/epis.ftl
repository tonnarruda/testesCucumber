<html>
<head>
<@ww.head/>

<title>Importação de EPIs</title>
	<#assign validarCampos="return validaFormulario('form', new Array('arquivo'), null)"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<fieldset class="fieldsetPadrao" style="width:900px;">
		<legend>Formato válido do Arquivo de Importação</legend>
		<span style="font-size:12px;">Código EPI|#|Nome do EPI|#|Nome do Fabricante|#|Status|#|É Fardamento|#|Data do Histórico EPI|#|Data de Vencimento|#|Número do CA|#| Percentual de Atenuação do Risco|#|Período Recomendado de Uso|#|Código da Categoria|#|Nome da Categoria</span>
	</fieldset>
	
	<br/>

	<@ww.form name="form" action="importarEPIs.action" onsubmit="${validarCampos}" method="POST" enctype="multipart/form-data">
		<@ww.file label="Arquivo TXT" name="arquivo" id="arquivo"/>
		<@ww.token/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnImportar"></button>
	</div>
</body>
</html>