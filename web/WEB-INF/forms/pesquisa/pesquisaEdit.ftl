<html>
<head>
<@ww.head/>

	<#assign class="btnAvancar"/>
<#if pesquisa.id?exists>
	<title>Editar Pesquisa</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>

	<#if pesquisa.questionario.liberado>
		<#assign class="btnGravar"/>
		<#assign formAction="gravar.action"/>
		<#assign accessKey="G"/>
	</#if>

<#else>
	<title>Inserir Pesquisa</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="A"/>
</#if>

<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('titulo','dataInicio','dataFim','anonima'), new Array('dataInicio','dataFim'))"/>

<#include "../ftl/mascarasImports.ftl" />

</head>
<body>
	<#if pesquisa?exists && pesquisa.id?exists && pesquisa.questionario?exists && pesquisa.questionario.dataInicio?exists>
		<#assign dataInicio=pesquisa.questionario.dataInicio?date />
	<#else>
		<#assign dataInicio="" />
	</#if>
	<#if pesquisa?exists && pesquisa.id?exists && pesquisa.questionario?exists && pesquisa.questionario.dataFim?exists>
		<#assign dataFim=pesquisa.questionario.dataFim?date />
	<#else>
		<#assign dataFim="" />
	</#if>

	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="validaPeriodo();" validate="true" method="POST">
		<@ww.textfield label="Título" name="pesquisa.questionario.titulo" id="titulo" cssClass="inputNome" maxLength="100" required="true" />
		Previsão do Período de Aplicação:<br>
		<@ww.datepicker label="Início" value="${dataInicio}" name="pesquisa.questionario.dataInicio" id="dataInicio" required="true" cssClass="mascaraData validaDataIni" liClass="liLeft"/>
		<@ww.datepicker label="Fim" value="${dataFim}" name="pesquisa.questionario.dataFim" id="dataFim" required="true" cssClass="mascaraData validaDataFim"/>

		<@ww.textarea label="Cabeçalho" name="pesquisa.questionario.cabecalho" cssStyle="width:445px;height:300"/>

		<#assign anonimoDisabled = "" />
		<#assign anonimoTitle = "" />
		<#if temResposta>
			<#assign anonimoDisabled = "true" />
			<#assign anonimoTitle = "Pesquisa já possui resposta(s). Não é possível editar esta configuração." />
			<@ww.hidden name="pesquisa.questionario.anonimo" />
		</#if>
		
		<@ww.select label="Pesquisa Anônima" disabled="${anonimoDisabled}" title="${anonimoTitle}" name="pesquisa.questionario.anonimo" id="anonima" list=r"#{true:'Sim',false:'Não'}" required="true" headerKey="" headerValue=""/>

		<@ww.hidden name="pesquisa.id" />
	    <@ww.hidden name="pesquisa.questionario.id" />
	    <@ww.hidden name="pesquisa.questionario.liberado" />
	    <@ww.hidden name="pesquisa.questionario.aplicarPorAspecto" />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="${class}" accesskey="${accessKey}">
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V">
		</button>
	</div>

</body>
</html>