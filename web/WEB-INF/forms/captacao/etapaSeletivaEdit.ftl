<html>
<head>
<@ww.head/>
<#if etapaSeletiva.id?exists>
	<title>Editar Etapa Seletiva</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Etapa Seletiva</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

<#assign validarCampos="return validaFormulario('form', new Array('ordem','nome'), null)"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield label="Ordem" name="etapaSeletiva.ordem" id="ordem" required="true" size="3" maxLength="3" onkeypress="return(somenteNumeros(event,''));" cssStyle="text-align:right;"/>
		<@ww.textfield label="Nome" name="etapaSeletiva.nome" id="nome" required="true" cssClass="inputNome" maxLength="100"/>
		<@ww.hidden name="etapaSeletiva.id" />
		<@ww.hidden name="etapaSeletiva.ordemAntiga" />
		<@ww.hidden name="etapaSeletiva.empresa.id" />

	<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}">
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V">
		</button>
	</div>
</body>
</html>