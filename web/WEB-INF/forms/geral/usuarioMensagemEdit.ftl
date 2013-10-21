<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
<#if usuarioMensagem.id?exists>
	<title>Editar Usuario Mensagem</title>
	<#assign formAction="update.action"/>
	<#assign class="btnGravar">
	<#assign accessKey="G"/>
<#else>
	<title>Enviar Mensagem</title>
	<#assign formAction="insert.action"/>
	<#assign class="btnGravar">
	<#assign accessKey="G"/>
</#if>

</head>
<body>

<@ww.actionerror />
<@ww.actionmessage />
<#assign validarCampos="return validaFormulario('form', new Array('@usuariosCheck', 'mensagem'))"/>

<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
	<@frt.checkListBox name="usuariosCheck" id="usuariosCheck" label="Destinatários" list="usuariosCheckList"/>
	<@ww.textarea label="Mensagem" name="usuarioMensagem.mensagem.texto" id="mensagem" cssStyle="width:500px;" theme="css_xhtml"/>

	<@ww.hidden name="usuarioMensagem.empresa.id" value="${empresaEmp.id}" />
	<@ww.hidden name="usuarioMensagem.mensagem.remetente" value="${usuarioRem.nome}" />

	<@ww.token/>
</@ww.form>

<div class="buttonGroup">
	<button onclick="${validarCampos};" class="btnEnviar" accesskey="G">
	</button>
</div>

</body>
</html>