<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Ficha de Investigação de Acidente(CAT)</title>

	<#include "../ftl/mascarasImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#assign validarCampos="return validaFormularioEPeriodo('form', null, new Array('inicio','fim'))"/>

	<#if inicio?exists >
		<#assign inicio = inicio?date/>
	<#else>
		<#assign inicio = ""/>
	</#if>
	<#if fim?exists>
		<#assign fim = fim?date/>
	<#else>
		<#assign fim = ""/>
	</#if>

</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" id="form" action="relatorioCats.action" method="POST">
		Período:<br>
		<@ww.datepicker name="inicio" id="inicio" value="${inicio}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="fim" id="fim" value="${fim}" cssClass="mascaraData validaDataFim" />

		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimento" list="estabelecimentosCheckList" filtro="true"/>

		<@ww.textfield label="Colaborador" name="nomeBusca" id="nomeBusca" cssStyle="width: 260px;"/>

		<div class="buttonGroup">
			<button class="btnRelatorio" onclick="${validarCampos}">
			</button>
		</div>
	</@ww.form>
</body>
</html>