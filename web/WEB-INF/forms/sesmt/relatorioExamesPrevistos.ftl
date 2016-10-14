<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<title>Exames Previstos</title>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<#include "../ftl/mascarasImports.ftl" />

	<@ww.head/>

	<#if inicio?exists>
		<#assign dateIni = inicio?date/>
	<#else>
		<#assign dateIni = ""/>
	</#if>
	<#if fim?exists>
		<#assign dateFim = fim?date/>
	<#else>
		<#assign dateFim = ""/>
	</#if>

<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
</head>

<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="relatorioExamesPrevistos.action" onsubmit="enviaForm();" method="POST" id="formBusca">

		Período:*<br>
		<@ww.datepicker name="inicio" id="dataIni" value="${dateIni}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="fim" id="dataFim" value="${dateFim}" cssClass="mascaraData validaDataFim" />

		<@frt.checkListBox name="examesCheck" label="Exames" list="examesCheckList" filtro="true"/>
		<@frt.checkListBox name="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" filtro="true" />
		<@frt.checkListBox name="areasCheck" label="Áreas Organizacionais" list="areasCheckList" filtro="true" selectAtivoInativo="true" />
		<@frt.checkListBox name="colaboradoresCheck" label="Colaboradores" list="colaboradoresCheckList" filtro="true" />
		
		<@ww.select label="Agrupar por" name="agruparPor" list=r"#{'N':'-','A':'Área Organizacional','E':'Estabelecimento'}"/>
		<@ww.checkbox label="Não imprimir afastado(s)" id="imprimirAfastados" name="imprimirAfastados" labelPosition="left"/>
		<@ww.checkbox label="Imprimir desligados" id="imprimirDesligados" name="imprimirDesligados" labelPosition="left"/>
		<@ww.checkbox label="Exibir exames solicitados e não realizados" id="exibirExamesNaoRealizados" name="exibirExamesNaoRealizados" labelPosition="left"/>

		<div class="buttonGroup">
			<input type="button" value="" onclick="validaFormularioEPeriodo('form',new Array('dataIni','dataFim'),new Array('dataIni','dataFim'));" class="btnRelatorio" />
		</div>

	</@ww.form>

</body>
</html>