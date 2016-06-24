<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />

<html>
<head>
<@ww.head/>
<#if risco.id?exists>
	<title>Editar Risco</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Risco</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

	<#assign validarCampos="return validaFormulario('form', new Array('descricao','grupoRisco'), null)"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield label="Nome" name="risco.descricao" id="descricao" cssClass="inputNome" maxLength="100" required="true"/>
		<@ww.select label="Tipo de Risco" name="risco.grupoRisco" id="grupoRisco" list="grupoRiscos" cssStyle="width: 220px;" headerKey="-1" headerValue="[Selecione...]" required="true"/>
		<@frt.checkListBox label="Equipamentos Obrigatórios (EPIs)" name="episCheck" list="episCheckList" filtro="true"/>
		
		<@ww.hidden label="Id" name="risco.id" />
		<@ww.token />
	</@ww.form>

	<div class="buttonGroup">
		<button  onclick="${validarCampos}" class="btnGravar" accesskey="${accessKey}"></button>
		<button onclick="javascript: executeLink('list.action');" class="btnCancelar" accesskey="V"></button>
	</div>
</body>
</html>