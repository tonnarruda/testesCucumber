<html>
<head>
<@ww.head/>
<#if mensagem.id?exists>
	<title>Editar Mensagem</title>
	<#assign formAction="update.action"/>
	<#assign buttonLabel="<u>A</u>tualizar"/>
	<#assign accessKey="A"/>
<#else>
	<title>Novo Mensagem</title>
	<#assign formAction="insert.action"/>
	<#assign buttonLabel="<u>I</u>nserir"/>
	<#assign accessKey="I"/>
</#if>

</head>
<body>
<@ww.actionerror />
<@ww.form name="form" action="${formAction}" validate="true" method="POST">
	<@ww.hidden label="Id" name="id" />
	<@ww.token/>
</@ww.form>

<hr class="divider"/>
<div class="buttonGroup">
<button onclick="document.form.submit();" class="button" accesskey="${accessKey}">
${buttonLabel}
</button>
<button onclick="window.location='list.action'" class="button" accesskey="V">
<u>V</u>oltar
</button>
</div>
</body>
</html>