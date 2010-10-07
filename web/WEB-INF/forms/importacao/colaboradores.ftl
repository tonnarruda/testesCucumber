<html>
<head>
<@ww.head/>

<#assign validarCampos="return imprimir();"/>
<title>Importação de Colaboradores (CSV)</title>

	
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
	
	<fieldset class="fieldsetPadrao" style="width:960px;">
		<ul>
		<legend>Formato válido do Arquivo de Importação</legend>
		<br/>
		Matrícula;CPF;Endereço;Número;Complemento Endereço;Cidade(ID);Estado(ID);Bairro;Cep;ddd;telefone;Celular;Grau Instrução(COD. DICIONÁRIO)
		</ul>
	</fieldset>
	<br/>
	
	<@ww.form name="form" action="importarColaboradorDadosPessoais.action" validate="true" onsubmit="${validarCampos}" method="POST" enctype="multipart/form-data">
		<@ww.file label="Arquivo CSV" name="arquivo" id="arquivo"/>
		<@ww.token/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnImportar" accesskey="I">
		</button>
	</div>
</body>
</html>