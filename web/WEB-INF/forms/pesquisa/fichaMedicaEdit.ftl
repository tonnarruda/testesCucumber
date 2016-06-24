<html>
<head>
<@ww.head/>

	<#assign class="btnAvancar"/>
<#if fichaMedica.id?exists>
	<title>Editar Modelo de Ficha Médica</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>

	<#if 0 < quantidadeDeResposta>
		<#assign class="btnGravar"/>
		<#assign formAction="gravar.action"/>
		<#assign accessKey="G"/>
	</#if>

	<#if fichaMedica.questionario.liberado>
		<#assign class="btnGravar"/>
		<#assign formAction="gravar.action"/>
		<#assign accessKey="G"/>
	</#if>

<#else>
	<title>Inserir Modelo de Ficha Médica</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="A"/>
</#if>

<#assign validarCampos="return validaFormulario('form', new Array('titulo','liberado'), null)"/>

</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" id="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield label="Título" name="fichaMedica.questionario.titulo" id="titulo" cssClass="inputNome" maxLength="100" required="true" />

		<@ww.textarea label="Observação" name="fichaMedica.questionario.cabecalho"/>
		<@ww.textarea label="Rodapé" name="fichaMedica.rodape" />
		<small>Evite texto longo no rodapé, pois apenas as quatro primeiras linhas serão exibidas na impressão da ficha.</small>
		
		<br /><br />

		<@ww.select label="Ativa" name="fichaMedica.ativa" id="liberado" list=r"#{true:'Sim',false:'Não'}"/>

		<@ww.hidden name="fichaMedica.id" />
	    <@ww.hidden name="fichaMedica.questionario.id" />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="${class}" accesskey="${accessKey}">
		</button>
		<button onclick="javascript: executeLink('list.action');" class="btnCancelar" accesskey="V">
		</button>
	</div>

</body>
</html>