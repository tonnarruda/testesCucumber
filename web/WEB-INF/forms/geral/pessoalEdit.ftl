<!--
  Autor: Bruno Bachiega
  Data: 6/06/2006
  Requisito: RFA0026
 -->
<html>
<head>
<@ww.head/>
<#if pessoal.id?exists>
	<title>Editar Pessoal</title>
	<#assign formAction="update.action"/>
	<#assign buttonLabel="Atualizar"/>
<#else>
	<title>Inserir Pessoal</title>
	<#assign formAction="insert.action"/>
	<#assign buttonLabel="Inserir"/>
</#if>

<#if dataNascimento?exists>
	<#assign data = dataNascimento>
<#else>
	<#assign data = ""/>
</#if>

	<#include "../ftl/mascarasImports.ftl" />
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" validate="true" method="POST">
		<@ww.textfield label="Cpf" name="cpf" />
		<@ww.textfield label="Sexo" name="sexo" />
		<@ww.datepicker label="DataNascimento" name="dataNascimento" value="${data}" cssClass="mascaraData"/>
		<@ww.textfield label="Escolaridade" name="escolaridade" />
		<@ww.textfield label="EstadoCivil" name="estadoCivil" />
		<@ww.textfield label="Conjugue" name="conjugue" />
		<@ww.textfield label="ConjugueTrabalha" name="conjugueTrabalha" />

	</@ww.form>


	<div class="buttonGroup">
		<button onclick="document.form.submit();" class="btnGravar" >
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V">
		</button>
	</div>
</body>
</html>