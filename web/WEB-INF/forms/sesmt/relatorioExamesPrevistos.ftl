<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<title>Exames Previstos</title>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>

	<#include "../ftl/mascarasImports.ftl" />

	<@ww.head/>

	<#if data?exists>
		<#assign dateIni = data?date/>
	<#else>
		<#assign dateIni = ""/>
	</#if>

<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
</head>

<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="relatorioExamesPrevistos.action" onsubmit="enviaForm();" method="POST" id="formBusca">

		Data:*
		<@ww.datepicker name="data" id="data" value="${dateIni}" required="true" cssClass="mascaraData"/>

		<@frt.checkListBox name="examesCheck" label="Cargos" list="examesCheckList" />
		<@frt.checkListBox name="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" />
		<@frt.checkListBox name="areasCheck" label="Áreas Organizacionais" list="areasCheckList" />
		<@frt.checkListBox name="colaboradoresCheck" label="Colaboradores" list="colaboradoresCheckList" />
		
		<@ww.checkbox label="Agrupar por Área Organizacional" id="agruparPorArea" name="agruparPorArea" labelPosition="left"/>
		<@ww.checkbox label="Não imprimir afastado(s)" id="imprimirAfastados" name="imprimirAfastados" labelPosition="left"/>

		<div class="buttonGroup">
			<input type="button" value="" onclick="validaFormulario('form',new Array('data'),new Array('data'));" class="btnRelatorio" />
		</div>

	</@ww.form>

</body>
</html>