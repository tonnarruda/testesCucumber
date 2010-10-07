<!--
  Autor: Bruno Bachiega
  Data: 16/06/2006
  Requisito: RFA0026
 -->
<html>
<head>
<@ww.head/>
<#if dependente.id?exists>
	<title>Editar Dependente</title>
	<#assign formAction="update.action">
<#else>
	<title>Inserir Dependente</title>
	<#assign formAction="insert.action">
</#if>
<#if dataNascimento?exists>
	<#assign dataNasc = dataNascimento/>
<#else>
	<#assign dataNasc = ""/>
</#if>

	<#include "../ftl/mascarasImports.ftl" />
</head>
	<body>

	<@ww.form name="form" action="${formAction}" validate="true" method="POST" enctype="multipart/form-data">
		<@ww.textfield  label="Nome" name="nome" required="true" cssClass="inputNome" maxLength="100"/>
		<@ww.datepicker label="Data de Nascimento" name="dataNascimento" value="${dataNasc}" required="true" cssClass="mascaraData"/>
		<@ww.hidden name="colaborador.id" />
		<@ww.hidden name="id" />
	<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="document.form.submit();" class="btnGravar">
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V">
		</button>
	</div>

</body>
</html>