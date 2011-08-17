<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Relatório de Competências dos Colaboradores</title>
	<#assign validarCampos="return validaFormulario('form', new Array('faixa', '@competenciasCheck'))"/>	

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CompetenciaDWR.js"/>'></script>
	
	<script type='text/javascript'>
		function populaCompetencia(faixaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			CompetenciaDWR.getByFaixa(createListCompetencia, faixaId);
		}

		function createListCompetencia(data)
		{
			addChecks('competenciasCheck',data);
		}
	</script>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	<@ww.form name="form" action="imprimirMatrizCompetenciasColaborador.action" onsubmit="${validarCampos}"  method="POST">
		<@ww.select label="Cargo/Faixa Salarial" name="faixaSalarial.id" id="faixa" list="faixaSalarials" listKey="id" listValue="descricao" required="true" headerKey="" headerValue="Selecione..." cssStyle="width: 502px;" onchange="populaCompetencia(this.value);" />
		<@frt.checkListBox  label="Competências da Faixa Salarial *" name="competenciasCheck" id="competenciasCheck" list="competenciasCheckList" height="250"/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio"></button>
	</div>
</body>
</html>