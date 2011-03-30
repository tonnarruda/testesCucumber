<html>
<head>
<@ww.head/>

	<#assign class="btnAvancar"/>
<#if entrevista.id?exists>
	<title>Editar Modelo de Entrevista de Desligamento</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>

	<#if 0 < quantidadeDeResposta>
		<#assign class="btnGravar"/>
		<#assign formAction="gravar.action"/>
		<#assign accessKey="G"/>
	</#if>

	<#if entrevista.questionario.liberado>
		<#assign class="btnGravar"/>
		<#assign formAction="gravar.action"/>
		<#assign accessKey="G"/>
	</#if>

<#else>
	<title>Inserir Modelo de Entrevista de Desligamento</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="A"/>
</#if>

<#assign validarCampos="return validaFormulario('form', new Array('titulo','liberado'), null)"/>

</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" id="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield label="Título" name="entrevista.questionario.titulo" id="titulo" cssClass="inputNome" maxLength="100" required="true" />
		<@ww.select label="Ativa" name="entrevista.ativa" id="liberado" list=r"#{true:'Sim',false:'Não'}"/>
		<@ww.textarea label="Observação" name="entrevista.questionario.cabecalho" cssStyle="width:445px;height:60px"/>

		<@ww.hidden name="entrevista.id" />
	    <@ww.hidden name="entrevista.questionario.id" />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="${class}" accesskey="${accessKey}">
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V">
		</button>
	</div>

</body>
</html>