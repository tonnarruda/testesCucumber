<html>
<head>
<@ww.head/>
<#if colaboradorIdioma.id?exists>
	<title>Editar ColaboradorIdioma</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir ColaboradorIdioma</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="n"/>
</#if>

</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" validate="true" method="POST">

	<#if colaboradors?exists>
		<@ww.select label="Colaborador" name="colaborador.id" list="colaboradors" listKey="id" listValue="id" headerValue="" headerKey=""/>
	</#if>
		<@ww.textfield label="Idioma" name="idioma" />
		<@ww.textfield label="Nivel" name="nivel" />
		<@ww.hidden label="Id" name="id" />
		<@ww.token/>
	</@ww.form>


	<!-- com.fortes.rh.model.geral.Colaborador -->
	<#if colaboradorIdioma.colaborador?exists>
	<ul>
	<li><a href="../colaborador/load.action?colaborador.id=${colaboradorIdioma.colaborador.id}">${colaboradorIdioma.colaborador}</a></li>
	</ul>
	</#if>
	
	<div class="buttonGroup">
		<button onclick="document.form.submit();" class="btnGravar" accesskey="${accessKey}">
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V">
		</button>
	</div>
</body>
</html>