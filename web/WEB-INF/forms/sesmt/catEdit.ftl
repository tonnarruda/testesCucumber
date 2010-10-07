<html>
<head>
<@ww.head/>
<#if cat.id?exists>
	<title>Editar Comunicação de Acidente de Trabalho</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
	<#assign edicao=true>
<#else>
	<title>Inserir Comunicação de Acidente de Trabalho</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

	<#if cat?exists && cat.data?exists>
		<#assign data = cat.data/>
	<#else>
		<#assign data = "" />
	</#if>

	<#include "../ftl/mascarasImports.ftl" />
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#if !edicao?exists>
		<@ww.form name="formFiltro" action="filtrarColaboradores.action" method="POST">
		<li>
			<@ww.div cssClass="divInfo" cssStyle="width: 500px;">
				<ul>
					<@ww.textfield label="Nome" name="colaborador.nome" id="nomeColaborador" cssStyle="width: 300px;"/>
					<@ww.textfield label="Matrícula" name="colaborador.matricula" id="matriculaBusca" liClass="liLeft" cssStyle="width: 60px;"/>
					<@ww.textfield label="CPF" name="colaborador.pessoal.cpf" id="cpfColaborador" cssClass="mascaraCpf"/>

					<div class="buttonGroup">
						<button onclick="validaFormulario('formFiltro', null, null);" class="btnPesquisar grayBGE"></button>
						<button onclick="document.formFiltro.action='list.action';document.formFiltro.submit();" class="btnVoltar grayBGE"></button>
					</div>
				</ul>
			</@ww.div>
		</li>
		</@ww.form>
	</#if>

	<#assign validarCampos="return validaFormulario('form', new Array('data','numero'), new Array('data'))"/>

	<#if (colaboradors?exists && colaboradors?size > 0) || (edicao?exists)>

		<@ww.form name="form" action="${formAction}" method="POST" onsubmit="${validarCampos}" validate="true">

			<#if cat.colaborador?exists>
				<b>Colaborador: ${cat.colaborador.nome}</b>
				<p></p>
				<@ww.hidden name="cat.colaborador.id" />
			</#if>

			<#if (colaboradors?exists && colaboradors?size > 0)>
					<@ww.select label="Colaborador" name="cat.colaborador.id" id="colaborador" required="true" list="colaboradors" listKey="id" listValue="nomeCpfMatricula"/>
			</#if>

			<@ww.datepicker label="Data de Emissão" required="true" id="data" name="cat.data" value="${data}" cssClass="mascaraData"/>
			<@ww.textfield label="Número" required="true" id = "numero" name="cat.numeroCat" cssStyle="width:80px;" maxLength="20"/>
			<@ww.textarea label="Observação" name="cat.observacao" cssClass="inputNome"/>
			<@ww.checkbox label="Gerou Afastamento (informação utilizada no PCMSO)" id="afastamento" name="cat.gerouAfastamento" labelPosition="left" />
			
			<@ww.hidden label="Id" name="cat.id" />
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}"> </button>
			<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V"> </button>
		</div>
	</#if>

</body>
</html>