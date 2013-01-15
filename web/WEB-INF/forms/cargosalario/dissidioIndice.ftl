<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Reajuste Coletivo/Dissídio para Índices</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formataValores.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ReajusteDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript'>
		function populaFaixas()
		{
			var cargosIds = getArrayCheckeds(document.forms[0],'cargosCheck');
			if (cargosIds.length == 0)
				cargosIds = [-1];
			ReajusteDWR.getByCargosDesabilitandoPorIndice(createListFaixas, cargosIds);
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
		<@frt.checkListBox name="indicesCheck" id="indicesCheck" label="Índices*" list="indicesCheckList" />
		<@ww.select id="dissidioPor" label="Reajuste por" name="dissidioPor" list=r"#{'1':'Porcentagem sobre o valor atual(%)', '2':'Quantia adicionada ao valor atual(R$)'}" liClass="liLeft" required="true"/>
		<@ww.textfield label="" name="valorDissidio" id="valorDissidio" cssClass="currency" cssStyle="width:85px; text-align:right;" maxLength="12"/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="return validaFormulario('form', ['tabelaReajuste', '@indicesCheck','valorDissidio'], ['valorDissidio']);" class="btnGravar"></button>
	</div>
</body>
</html>