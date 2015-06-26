<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<title>Funções por Exames</title>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<#include "../ftl/mascarasImports.ftl" />

	<@ww.head/>

	<#if data?exists>
		<#assign dateIni = data?date/>
	<#else>
		<#assign dateIni = ""/>
	</#if>
</head>

<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="relatorioFuncoesExames.action" onsubmit="enviaForm();" method="POST" id="formBusca">

		Data:*
		<@ww.datepicker name="data" id="data" value="${dateIni}" required="true" cssClass="mascaraData"/>
		<@frt.checkListBox name="funcoesCheck" label="Funções" list="funcoesCheckList" filtro="true"/>

		<div class="buttonGroup">
			<input type="button" value="" onclick="validaFormulario('form',new Array('data'),new Array('data'));" class="btnRelatorio" />
		</div>

	</@ww.form>

</body>
</html>