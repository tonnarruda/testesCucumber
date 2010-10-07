<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<title>Atendimentos Médicos</title>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
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

	<@ww.form name="form" action="relatorioAtendimentosMedicos.action" onsubmit="enviaForm();" method="POST" id="formBusca">

		
		Período:*<br>
		<@ww.datepicker name="inicio" id="dataIni" value="${dateIni}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="fim" id="dataFim" value="${dateFim}" cssClass="mascaraData validaDataFim" />

		<@ww.select label="Médico" name="solicitacaoExame.medicoCoordenador.id" id="medico" list="medicoCoordenadors" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." />		

		<@frt.checkListBox label="Motivo do Atendimento" name="motivosCheck" id="motivosCheck" list="motivosCheckList" />
		
		<@ww.checkbox label="Agrupar por motivo" id="agruparPorMotivo" name="agruparPorMotivo" labelPosition="left"/>
		<@ww.checkbox label="Ordenar por Nome" id="ordenarPorNome" name="ordenarPorNome" labelPosition="left"/>
		
		<div class="buttonGroup">
			<input type="button" value="" onclick="validaFormularioEPeriodo('form',new Array('dataIni','dataFim'),new Array('dataIni','dataFim'));" class="btnRelatorio" />
		</div>

	</@ww.form>

</body>
</html>