<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Matriz comparativa de competências de Colaborador X Cargo/Faixa</title>
	<#assign validarCampos="return validaFormulario('form', new Array('faixa', '@competenciasCheck', 'dataIni', 'dataFim'), new Array('dataIni', 'dataFim'))"/>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CompetenciaDWR.js"/>'></script>
	<#include "../ftl/mascarasImports.ftl" />
	
	<script type='text/javascript'>
		function populaCompetencia()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			CompetenciaDWR.getCompetenciasColaboradorByFaixaSalarialAndPeriodo(createListCompetencia, $('#faixa').val(), $('#dataIni').val(), $('#dataFim').val());
		}

		function createListCompetencia(data)
		{
			addChecks('competenciasCheck',data);
		}
	</script>
	
	<#if dataIni?exists>
		<#assign valueDataIni = dataIni?date/>
	<#else>
		<#assign valueDataIni = ""/>
	</#if>

	<#if dataFim?exists>
		<#assign valueDataFim = dataFim?date/>
	<#else>
		<#assign valueDataFim = ""/>
	</#if>
	
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	<@ww.form name="form" action="imprimirMatrizCompetenciasColaborador.action" onsubmit="${validarCampos}"  method="POST">
		<div>Período da competência do colaborador*:</div>
		<@ww.datepicker name="dataIni" id="dataIni" liClass="liLeft" value="${valueDataIni}"  cssClass="mascaraData validaDataIni" onchange="populaCompetencia();"/>
		<@ww.label value="a" liClass="liLeft"/>
		<@ww.datepicker name="dataFim" id="dataFim" value="${valueDataFim}" cssClass="mascaraData validaDataFim" onchange="populaCompetencia();"/>
		
		<@ww.select label="Cargo/Faixa Salarial" name="faixaSalarial.id" id="faixa" list="faixaSalarials" listKey="id" listValue="descricao" required="true" headerKey="" headerValue="Selecione..." cssStyle="width: 502px;" onchange="populaCompetencia();" />
		<@frt.checkListBox  label="Competências da Faixa Salarial *" name="competenciasCheck" id="competenciasCheck" list="competenciasCheckList" height="250" filtro="true"/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio"></button>
	</div>
</body>
</html>