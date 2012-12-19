<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Reajuste Coletivo/Dissídio para Faixas Salariais</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formataValores.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/FaixaSalarialDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript'>
		function populaFaixas()
		{
			var cargosIds = getArrayCheckeds(document.forms[0],'cargosCheck');
			if (cargosIds.length == 0)
				cargosIds = [-1];
			FaixaSalarialDWR.getByCargos(createListFaixas, cargosIds);
		}
		
		function createListFaixas(data)
		{
			addChecksByCollection('faixasCheck', data, 'descricao');
		}
	</script>
</head>

<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" action="aplicarDissidio.action" method="post">
		<@ww.select label="Tabela de Reajuste" name="tabelaReajusteColaborador.id" id="tabelaReajuste" list="tabelaReajusteColaboradors" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="" required="true" cssStyle="width:500px;"/>
		<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos*" list="cargosCheckList" onClick="populaFaixas();"/>
		<@frt.checkListBox name="faixasCheck" id="faixasCheck" label="Faixas Salariais*" list="faixasCheckList" />
		<@ww.select id="optFiltro" label="Reajuste Por" name="dissidioPor" list=r"#{'1':'Porcentagem sobre o salario atual(%)', '2':'Valor adicionado ao salário(R$)'}" liClass="liLeft" required="true"/>
		<@ww.textfield label="" name="valorDissidio" id="valorDissidio" cssClass="currency" cssStyle="width:85px; text-align:right;" maxLength="12"/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="validaFormulario('form', ['@cargosCheck','@faixasCheck','valorDissidio'], ['valorDissidio']);" class="btnGravar"></button>
	</div>
</body>
</html>