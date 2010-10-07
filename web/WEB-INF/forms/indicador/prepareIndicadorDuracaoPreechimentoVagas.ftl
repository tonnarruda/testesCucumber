<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>

<#assign validarCampos="return imprimir();"/>
<title>Recrutamento e Seleção - Duração para Preenchimento de Vaga</title>

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

		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList"/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio" accesskey="I">
		</button>
	</div>
</body>
</html>