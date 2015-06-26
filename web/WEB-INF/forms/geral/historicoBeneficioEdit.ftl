<html>
<head>
<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/formataValores.js?version=${versao}"/>"></script>
<@ww.head/>
<#if historicoBeneficio.id?exists>
	<title>Editar Histórico do Benefício - ${beneficio.nome}</title>
	<#assign formAction="update.action"/>
	<#assign buttonLabel="<u>A</u>tualizar"/>
	<#assign accessKey="A"/>
<#else>
	<title>Novo Histórico do Benefício - ${beneficio.nome}</title>
	<#assign formAction="insert.action"/>
	<#assign buttonLabel="<u>I</u>nserir"/>
	<#assign accessKey="I"/>
</#if>
<#assign validarCampos="return validaFormulario('form', new Array('dataHist','valor','perColab','perDir','perInd'), new Array('dataHist'))"/>

<#if historicoBeneficio?exists && historicoBeneficio.data?exists>
	<#assign data = historicoBeneficio.data/>
<#else>
	<#assign data = ""/>
</#if>

	<#include "../ftl/mascarasImports.ftl" />
</head>
<body>
<@ww.actionerror />
<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
	<@ww.hidden name="beneficio.id" />
	<@ww.datepicker label="A partir de" name="historicoBeneficio.data" value="${data}" id="dataHist"  cssClass="mascaraData" required="true"/>
	<@ww.textfield label="Valor" name="historicoBeneficio.valor" id="valor" required="true" cssClass="currency" cssStyle="width:85px; text-align:right;" maxLength="12"/>
	<@ww.textfield label="Percentual pago pelo Colaborador" onkeypress = "return(somenteNumeros(event,'{,}'));" name="historicoBeneficio.paraColaborador" id="perColab" required="true" cssStyle="text-align:right; width:50px;"/>
	<@ww.textfield label="Percentual pago pelo Dependente Direto" onkeypress = "return(somenteNumeros(event,'{,}'));" name="historicoBeneficio.paraDependenteDireto" id="perDir" required="true" cssStyle="text-align:right; width:50px;" />
	<@ww.textfield label="Percentual pago pelo Dependente Indireto" onkeypress = "return(somenteNumeros(event,'{,}'));" name="historicoBeneficio.paraDependenteIndireto" id="perInd" required="true" cssStyle="text-align:right; width:50px;"/>
	<@ww.hidden label="Id" name="historicoBeneficio.id" />
</@ww.form>

<div class="buttonGroup">
	<button onclick="${validarCampos};"  class="btnGravar" accesskey="${accessKey}">
	</button>
	<button onclick="window.location='../beneficio/prepareUpdate.action?beneficio.id=${beneficio.id}'" class="btnVoltar" accesskey="V">
	</button>
</div>
</body>
</html>