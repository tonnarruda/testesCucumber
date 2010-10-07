<html>
<head>
<@ww.head/>
<#if colaboradorPresenca.id?exists>
	<title>Editar ColaboradorPresenca</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir ColaboradorPresenca</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" validate="true" method="POST">

	<#if colaboradorTurmas?exists>
		<@ww.select label="ColaboradorTurma" name="colaboradorTurma.id" list="colaboradorTurmas" listKey="id" listValue="id" headerValue="" headerKey=""/>
	</#if>

	<#if diaTurmas?exists>
		<@ww.select label="DiaTurma" name="diaTurma.id" list="diaTurmas" listKey="id" listValue="id" headerValue="" headerKey=""/>
	</#if>
		<@ww.checkbox label="Presenca" name="presenca" />
		<@ww.hidden label="Id" name="id" />
	</@ww.form>


	<!-- com.fortes.rh.model.desenvolvimento.ColaboradorTurma -->
	<#if colaboradorPresenca.colaboradorTurma?exists>
	<ul>
	<li><a href="../colaboradorTurma/load.action?colaboradorTurma.id=${colaboradorPresenca.colaboradorTurma.id}">${colaboradorPresenca.colaboradorTurma}</a></li>
	</ul>
	</#if>

	<!-- com.fortes.rh.model.desenvolvimento.DiaTurma -->
	<#if colaboradorPresenca.diaTurma?exists>
	<ul>
	<li><a href="../diaTurma/load.action?diaTurma.id=${colaboradorPresenca.diaTurma.id}">${colaboradorPresenca.diaTurma}</a></li>
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