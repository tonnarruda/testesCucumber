<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Reajuste Coletivo/Dissídio para Faixas Salariais</title>

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formataValores.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ReajusteDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript'>
	
		$(function() {
			$('#tooltipHelp').qtip({
				content: 'Algumas faixas salariais podem estar desabilitadas por possuírem realinhamentos pendentes, seus valores forem por índice ou não possuírem históricos cadastrados.'
			});
		});
	
		function populaFaixas()
		{
			var cargosIds = getArrayCheckeds(document.forms[0],'cargosCheck');
			if (cargosIds.length == 0)
				cargosIds = [-1];
			ReajusteDWR.getFaixasByCargosDesabilitandoPorIndice(createListFaixas, cargosIds);
		}
		
		function createListFaixas(data)
		{
			addChecksByCollection('faixasCheck', data);
		}
	</script>
</head>

<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" id="form" action="insertColetivo.action" method="POST">
		<@ww.select label="Tabela de Reajuste" name="tabelaReajusteColaborador.id" id="tabelaReajuste" list="tabelaReajusteColaboradors" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="" required="true" cssStyle="width:500px;"/>
		<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos*" list="cargosCheckList" onClick="populaFaixas();" filtro="true" selectAtivoInativo="true"/>
		Faixas Salariais*:
		<img id="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16"  />
		<@frt.checkListBox name="faixasCheck" id="faixasCheck" list="faixasCheckList" filtro="true"/>
		<@ww.select id="dissidioPor" label="Reajuste por" name="dissidioPor" list=r"#{'1':'Porcentagem sobre o salario atual(%)', '2':'Valor adicionado ao salário(R$)'}" liClass="liLeft" required="true"/>
		<@ww.textfield label="" name="valorDissidio" id="valorDissidio" cssClass="currency" cssStyle="width:85px; text-align:right;" maxLength="12"/>
		
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="return validaFormulario('form', ['tabelaReajuste', '@cargosCheck','@faixasCheck','valorDissidio'], ['valorDissidio']);" class="btnGravar"></button>
	</div>
</body>
</html>