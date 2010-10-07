<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
<#if conhecimento.id?exists>
	<title>Editar Conhecimento</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Conhecimento</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

<#assign validarCampos="return validaFormulario('form', new Array('nome','@areasCheck'), null)"/>
</head>
<body>
<@ww.actionerror />
<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">

	<@ww.textfield label="Nome" name="conhecimento.nome" id="nome" required="true" cssClass="inputNome" maxLength="100"/>

	<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais *" list="areasCheckList"/>

	<@ww.hidden label="Id" name="conhecimento.id" />

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