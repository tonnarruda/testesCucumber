<!--
 * autor: Moésio Medeiros
 * Data: 07/06/2006
 * Requisito: RFA013 - Cadastro de candidato
-->
<html>
<head>
<@ww.head/>
	<#if candidato.id?exists>
		<title>Editar Curriculum</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
	<#else>
		<title>Cadastre seu curriculum para ver os anúncios</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
	</#if>

<#if candidato?exists && candidato.pessoal.dataNascimento?exists>
	<#assign data = candidato.pessoal.dataNascimento/>
<#else>
	<#assign data = ""/>
</#if>

	<#include "../ftl/mascarasImports.ftl" />
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/fortes.css"/>');
</style>

</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" validate="true" method="POST">
		<@ww.textfield label="CPF" name="candidato.pessoal.cpf" cssClass="mascaraCpf" />
		<@ww.password label="Senha" name="candidato.senha" cssStyle="width: 80px;" liClass="liLeft"/>
		<@ww.password label="Confirma senha" name="confirmaSenha" cssStyle="width: 80px;"/>
		<@ww.textfield label="Nome" name="candidato.nome" maxLength="60" cssStyle="width:445px;"/>
		<@ww.textfield label="Logradouro" name="candidato.endereco.logradouro" maxLength="40" cssClass="medium" id="ende"/>
		<@ww.textfield label="Bairro" name="candidato.endereco.bairro" cssClass="medium"  maxLength="85" id="bairroNome"/>
		<@ww.textfield label="Cidade" name="candidato.endereco.cidade" cssClass="medium" id="cidade"/>
		<@ww.select label="Estado" name="candidato.endereco.uf" list="estados" id="uf"/>
		<@ww.textfield label="CEP" name="candidato.endereco.cep" cssClass="mascaraCep" id="cep" />
		<@ww.textfield label="Telefone fixo" name="candidato.contato.foneFixo" cssClass="medium"/>
		<@ww.textfield label="Telefone celular" name="candidato.contato.foneCelular" cssClass="medium"/>
		<@ww.textfield label="Email" name="candidato.contato.email" maxLength="200" cssStyle="width:445px;" />
		<@ww.select label="Sexo" name="candidato.pessoal.sexo" list="sexos" />
		<@ww.datepicker label="Data de nascimento" name="candidato.pessoal.dataNascimento" cssClass="mascaraData" value="${data}"/>
		<@ww.select label="Escolaridade" name="candidato.pessoal.escolaridade" list="escolaridades" headerKey="" headerValue=""/>
		<@ww.select label="Estado Civil" name="candidato.pessoal.estadoCivil" list="estadosCivis" />
		<@ww.textfield label="Cônjuge" name="candidato.pessoal.conjuge" />
		<@ww.select label="Cônjuge trabalha" name="candidato.pessoal.conjugeTrabalha" list=r"#{'true':'Sim','false':'Não'}"/>

		<@ww.textfield label="Dependentes" name="candidato.qtdDependentes" size="02"/>
		<@ww.select label="Disponível" name="candidato.disponivel" list=r"#{'true':'Sim','false':'Não'}"/>

		<@ww.textarea label="Observação" name="candidato.observacao" cssStyle="width:445px;" />
		<#if candidato?exists && candidato.id?exists>
			<@ww.hidden label="Id" name="candidato.id" />
		</#if>
		
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="document.form.submit();javascript:validarCamposCpf();" class="btnGravar" accesskey="${accessKey}">
		</button>
		<#if candidato?exists && candidato.id?exists>
		<button onclick="window.location='../formacao/list.action'" class="btnDadosComplementares" accesskey="c">
		</button>
		</#if>
		<#if !SESSION_CPF?exists>
			<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V">
			</button>
		</#if>
	</div>
</body>
</html>