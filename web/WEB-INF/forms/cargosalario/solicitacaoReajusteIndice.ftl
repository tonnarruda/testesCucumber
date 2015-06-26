<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ReajusteDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
	<#if reajusteIndice?exists && reajusteIndice.id?exists>
		<title>Editar Solicitação de Realinhamento de Cargos e Salários por Índice</title>
		<#assign edicao = true/>
		<#assign formAction = "update.action"/>
	<#else>
		<title>Solicitação de Realinhamento de Cargos e Salários por Índice</title>
		<#assign edicao = false/>
		<#assign formAction = "insert.action"/>
	</#if>
	
	<script type='text/javascript'>
		$(function() {
			<#if !edicao && !empresaSistema.acIntegra>
				ReajusteDWR.getOptionsIndicesDesabilitandoPendentes(createListIndices);
			</#if>
		});
		
		function createListIndices(data)
		{
			addOptionsByCollection('indiceId', data, 'Selecione...');
		}
	</script>
</head>

<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<#if !empresaSistema.acIntegra>
		<@ww.form name="form" id="form" action="${formAction}" method="POST">
			<#if edicao>
				<@ww.hidden name="tabelaReajusteColaborador.id" value="${reajusteIndice.tabelaReajusteColaborador.id}"/>
				<@ww.hidden name="reajusteIndice.id"/>
				<@ww.hidden name="reajusteIndice.valorAtual"/>
	
				Planejamento de Realinhamento:<br />
				<strong>${reajusteIndice.tabelaReajusteColaborador.nome}</strong><br /><br />
				
				Índice:<br />
				<strong>${reajusteIndice.indice.nome}</strong><br /><br />
				
				Valor Atual:<br />
				<strong>R$ ${reajusteIndice.valorAtual}</strong><br /><br />
				
				Valor Proposto:<br />
				<strong>R$ ${reajusteIndice.valorProposto}</strong><br /><br />
		
				<@ww.select id="dissidioPor" label="Reajuste por" name="dissidioPor" list=r"#{'1':'Porcentagem sobre o valor atual(%)', '2':'Quantia adicionada ao valor atual(R$)'}" liClass="liLeft" required="true"/>
				<@ww.textfield label="" name="valorDissidio" id="valorDissidio" cssClass="currency" cssStyle="width:85px; text-align:right;" maxLength="12"/>
			<#else>
				<@ww.select label="Planejamento de Realinhamento" name="tabelaReajusteColaborador.id" id ="tabelaReajuste" required="true" list="tabelaReajusteColaboradors" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="-1" cssStyle="width:500px;"/>
				<@ww.select label="Índice" name="indice.id" id="indiceId" required="true" list="indices" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="-1" cssStyle="width:500px;"/>
				<@ww.select id="dissidioPor" label="Reajuste por" name="dissidioPor" list=r"#{'1':'Porcentagem sobre o valor atual(%)', '2':'Quantia adicionada ao valor atual(R$)'}" liClass="liLeft" required="true"/>
				<@ww.textfield label="" name="valorDissidio" id="valorDissidio" cssClass="currency" cssStyle="width:85px; text-align:right;" maxLength="12"/>
			</#if>
		</@ww.form>
	
		<div class="buttonGroup">
			<#if edicao>
				<button onclick="return validaFormulario('form', ['valorDissidio'], ['valorDissidio']);" class="btnGravar"></button>
			<#else>
				<button onclick="return validaFormulario('form', ['tabelaReajuste','indiceId','valorDissidio'], ['valorDissidio']);" class="btnGravar"></button>
			</#if>
		</div>
	</#if>
</body>
</html>