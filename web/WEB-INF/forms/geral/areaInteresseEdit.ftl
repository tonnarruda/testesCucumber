<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
<#if areaInteresse.id?exists>
	<title>Editar Área de Interesse</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Área de Interesse</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

<#assign validarCampos="return validaFormulario('form', new Array('nome','@areasCheck'), null)"/>
</head>
<body>
<@ww.actionerror />
<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
	<@ww.textfield label="Nome" name="areaInteresse.nome" id="nome" required="true" cssClass="inputNome" maxLength="100"/>

	<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais *" list="areasCheckList"/>

	<@ww.textarea  label="Observações" name="areaInteresse.observacao" cssClass="inputNome" />

	<@ww.hidden label="Id" name="areaInteresse.id" />

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