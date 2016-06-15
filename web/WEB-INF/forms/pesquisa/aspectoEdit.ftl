<html>
<head>
<@ww.head/>
<#if aspecto.id?exists>
	<title>Editar Aspecto</title>

	<#if avaliacao?exists && avaliacao.id?exists>		
		<#assign formAction="updateAvaliacao.action"/>
	<#else>
		<#assign formAction="update.action"/>
	</#if>

	<#assign buttonLabel="<u>A</u>tualizar"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Aspecto</title>
	
	<#if avaliacao?exists && avaliacao.id?exists>		
		<#assign formAction="insertAvaliacao.action"/>
	<#else>
		<#assign formAction="insert.action"/>
	</#if>
	
	<#assign buttonLabel="<u>I</u>nserir"/>
	<#assign accessKey="I"/>
</#if>
<#assign validarCampos="return validaFormulario('form', new Array('nome'), null)"/>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield label="Nome" name="aspecto.nome" id="nome" cssClass="inputNome" maxLength="100" required="true"/>
		<@ww.hidden name="aspecto.id" />
		<@ww.hidden name="questionario.id" />
		<@ww.hidden name="avaliacao.id" />
		<@ww.hidden name="pergunta.id" />
		<@ww.hidden name="modeloAvaliacao" />
		<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}"></button>
		
		<#if avaliacao?exists && avaliacao.id?exists>
			<button class="btnCancelar" onclick="javascript: executeLink('listAvaliacao.action?avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}');"></button>		
		<#else>
			<#if pergunta?exists && pergunta.id?exists>
				<button class="btnCancelar" onclick="javascript: executeLink('list.action?pergunta.id=${pergunta.id}&questionario.id=${questionario.id}');"></button>
			<#else>
				<button class="btnCancelar" onclick="javascript: executeLink('list.action?questionario.id=${questionario.id}');"></button>
			</#if>
		</#if>
	</div>
</body>
</html>