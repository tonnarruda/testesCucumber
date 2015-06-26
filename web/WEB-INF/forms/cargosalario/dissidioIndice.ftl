<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Reajuste Coletivo/Dissídio para Índices</title>

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formataValores.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ReajusteDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript'>
		$(function() {
			<#if !empresaSistema.acIntegra>
				ReajusteDWR.getIndicesDesabilitandoPendentes(createListIndices);
			</#if>
			$('#tooltipHelp').qtip({
				content: 'Alguns índices podem estar desabilitados por possuírem realinhamentos pendentes ou não possuírem históricos cadastrados.'
			});
		});
			
		function createListIndices(data)
		{
			addChecksByCollection('indicesCheck', data);
		}
	</script>
</head>

<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<#if !empresaSistema.acIntegra>
		<@ww.form name="form" id="form" action="insertColetivo.action" method="POST">
			<@ww.select label="Tabela de Reajuste" name="tabelaReajusteColaborador.id" id="tabelaReajuste" list="tabelaReajusteColaboradors" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="" required="true" cssStyle="width:500px;"/>
			Indices:*
			<img id="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16"  />
			<@frt.checkListBox name="indicesCheck" id="indicesCheck" list="indicesCheckList" filtro="true"/>
			<@ww.select id="dissidioPor" label="Reajuste por" name="dissidioPor" list=r"#{'1':'Porcentagem sobre o valor atual(%)', '2':'Quantia adicionada ao valor atual(R$)'}" liClass="liLeft" required="true"/>
			<@ww.textfield label="" name="valorDissidio" id="valorDissidio" cssClass="currency" cssStyle="width:85px; text-align:right;" maxLength="12"/>
		</@ww.form>
		
		<div class="buttonGroup">
			<button onclick="return validaFormulario('form', ['tabelaReajuste', '@indicesCheck','valorDissidio'], ['valorDissidio']);" class="btnGravar"></button>
		</div>
	</#if>
</body>
</html>