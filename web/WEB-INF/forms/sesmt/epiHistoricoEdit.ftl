<html>
<head>
<@ww.head/>

<#assign accessKey="G"/>
<#if epiHistorico.id?exists>
	<title>
	Editar Histórico de EPI
	</title>
	<#assign formAction="update.action"/>
<#else>
	<title>
	Inserir Histórico de EPI
	</title>
	<#assign formAction="insert.action"/>
</#if>

	<#include "../ftl/mascarasImports.ftl" />
</head>
<body>
<@ww.actionerror />

	<#-- indicando se epi é fardamento (alguns campos ficam não obrigatórios) -->
	<#if epiEhFardamento>
		<#assign campoObrigatorio = "false" />
	<#else>
		<#assign campoObrigatorio = "true" />
	</#if>

<#if epiHistorico?exists && epiHistorico.id?exists>

	<#if epiHistorico.vencimentoCA?exists>
		<#assign vencimentoCAV = epiHistorico.vencimentoCA />
	<#else>
		<#assign vencimentoCAV = "" />
	</#if>
	<#assign aPartirV = epiHistorico.data?date/>
<#else>
	<#assign vencimentoCAV = ""/>
	<#assign aPartirV = ""/>
</#if>

<#if campoObrigatorio == "true">
	<#assign validarCampos="return validaFormulario('form', new Array('CA','vencimentoCA','validadeUso', 'data'), new Array('vencimentoCA', 'data'));"/>
<#else>
	<#assign validarCampos="return validaFormulario('form', new Array('validadeUso', 'data'), new Array('vencimentoCA', 'data'));"/>
</#if>

<@ww.form name="form" action="${formAction}" method="POST">
	<@ww.datepicker label="A partir de" name="epiHistorico.data" required="true" id="data" value="${aPartirV}" cssClass="mascaraData"/>
	<@ww.textfield label="Número do CA" name="epiHistorico.CA" id="CA" required="${campoObrigatorio}" maxLength="20" />
	<@ww.datepicker label="Vencimento do CA" id="vencimentoCA" name="epiHistorico.vencimentoCA" required="${campoObrigatorio}" value="${vencimentoCAV}" cssClass="mascaraData"/>
	<@ww.textfield label="Percentual de Atenuação do Risco" id="atenuacao" name="epiHistorico.atenuacao" onkeypress="return(somenteNumeros(event,''));" cssStyle="text-align: right; width: 30px;" maxLength="3"/>
	<@ww.textfield label="Período Recomendado de uso (em dias)" id="validadeUso" name="epiHistorico.validadeUso" onkeypress="return(somenteNumeros(event,''));" required="true" cssStyle="text-align: right; width: 40px;" maxLength="4"/>
	<@ww.hidden name="epiHistorico.id" />
	<@ww.hidden name="epiHistorico.epi.id" />
	<@ww.hidden name="epi.id" />
	<@ww.token/>

</@ww.form>
<div class="buttonGroup">
	<button onclick="${validarCampos}" class="btnGravar" accesskey="${accessKey}">
	</button>
	<button onclick="window.location='../epi/prepareUpdate.action?epi.id=${epi.id}'" class="btnCancelar" accesskey="C">
	</button>
</div>
</body>
</html>