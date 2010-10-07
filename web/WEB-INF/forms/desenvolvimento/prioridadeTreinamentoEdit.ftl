<html>
<head>
<@ww.head/>
<#if prioridadeTreinamento.id?exists>
	<title>Editar Prioridade de Treinamento</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Prioridade de Treinamento</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

<#assign validarCampos="return validaFormulario('form', new Array('desc','sigla','peso'), null)"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield required="true" label="Descrição" name="prioridadeTreinamento.descricao" id="desc" cssClass="inputNome" maxLength="100"/>
		<@ww.textfield required="true" label="Sigla" name="prioridadeTreinamento.sigla" id="sigla" size="5" maxLength="4" liClass="liLeft"/>
		<#if prioridadeTreinamento.id?exists>
			<@ww.textfield required="true" label="Peso" name="prioridadeTreinamento.numero" id="peso" onkeypress="return(somenteNumeros(event,''));" size="3" maxLength="2"/>
		<#else>
			<@ww.textfield required="true" label="Peso" name="prioridadeTreinamento.numero" id="peso" onkeypress="return(somenteNumeros(event,''));" size="3" cssStyle="text-align: right;" maxLength="2" value=""/>
		</#if>
		<@ww.hidden label="Id" name="prioridadeTreinamento.id" />

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