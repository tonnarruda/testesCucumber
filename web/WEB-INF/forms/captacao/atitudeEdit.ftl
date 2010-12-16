<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
<#if atitude.id?exists>
	<title>Editar Atitude</title>
	<#assign formAction="update.action"/>
<#else>
	<title>Inserir Atitude</title>
	<#assign formAction="insert.action"/>
</#if>

<#assign validarCampos="return validaFormulario('form', new Array('nome'), null)"/>
</head>
<body>
<@ww.actionerror />
<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">

	<@ww.textfield label="Nome" name="atitude.nome" id="nome" required="true" cssClass="inputNome" maxLength="100"/>
	<@ww.textarea label="Observação" name="atitude.observacao" id="observacao" cssStyle="width:445px;"/>
	<@ww.hidden label="Id" name="atitude.id" />

<@ww.token/>
</@ww.form>


<div class="buttonGroup">
	<button onclick="${validarCampos};" class="btnGravar" ></button>
	<button onclick="window.location='list.action'" class="btnCancelar"></button>
</div>
</body>
</html>