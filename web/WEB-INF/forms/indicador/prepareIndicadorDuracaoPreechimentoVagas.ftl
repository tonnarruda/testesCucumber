<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>

<#assign validarCampos="return imprimir();"/>
<title>Duração para Preenchimento de Vagas</title>

	<#include "../ftl/mascarasImports.ftl" />
	<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('dataDe','dataAte'), new Array('dataDe','dataAte'))"/>

	<#if dataDe?exists>
		<#assign dataDeTmp = dataDe?date/>
	<#else>
		<#assign dataDeTmp = ""/>
	</#if>
	<#if dataAte?exists>
		<#assign dataAteTmp = dataAte?date/>
	<#else>
		<#assign dataAteTmp = ""/>
	</#if>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="list.action" onsubmit="${validarCampos}" validate="true" method="POST">
		<div>Período de encerramento da solicitação*:</div>
		<@ww.datepicker name="dataDe" id="dataDe" value="${dataDeTmp}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
		<@ww.label value="a" liClass="liLeft"/>
		<@ww.datepicker name="dataAte" id="dataAte" value="${dataAteTmp}" cssClass="mascaraData validaDataFim"/>
		<@ww.checkbox label="Considerar contratações futuras" id="considerarContratacaoFutura" name="considerarContratacaoFutura" labelPosition="left"/>
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" filtro="true"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnRelatorio" onclick="$('form[name=form]').attr('action', 'list.action');${validarCampos};" accesskey="I"></button>
		<button class="btnRelatorioExportar" onclick="$('form[name=form]').attr('action', 'listXLS.action');${validarCampos};"></button>
	</div>
</body>
</html>