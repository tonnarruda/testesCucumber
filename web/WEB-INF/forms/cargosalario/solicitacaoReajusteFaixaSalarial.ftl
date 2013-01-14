<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ReajusteDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript'>
		$(function() {
			$('#cargoId').change(function() {
				var cargoId = $(this).val();
				ReajusteDWR.getByCargoDesabilitandoPorIndice(createListFaixas, cargoId);
			});
		});
		
		function createListFaixas(data)
		{
			addOptionsByCollection('faixaSalarialId', data);
		}
	</script>
	
	<#if reajusteFaixaSalarial?exists && reajusteFaixaSalarial.id?exists>
		<title>Editar Solicitação de Realinhamento de Cargos & Salários para Faixa Salarial</title>
		<#assign edicao = true/>
		<#assign formAction = "update.action"/>
	<#else>
		<title>Solicitação de Realinhamento de Cargos & Salários para Faixa Salarial</title>
		<#assign edicao = false/>
		<#assign formAction = "insert.action"/>
	</#if>
</head>

<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" id="form" action="${formAction}" method="POST">
		<#if edicao>
			<@ww.hidden name="tabelaReajusteColaborador.id" value="${reajusteFaixaSalarial.tabelaReajusteColaborador.id}"/>
			<@ww.hidden name="reajusteFaixaSalarial.id"/>
			<@ww.hidden name="reajusteFaixaSalarial.valorAtual"/>
			
			Planejamento de Realinhamento:<br />
			<strong>${reajusteFaixaSalarial.tabelaReajusteColaborador.nome}</strong><br /><br />
			
			Faixa Salarial:<br />
			<strong>${reajusteFaixaSalarial.faixaSalarial.descricao}</strong><br /><br />

			<@ww.select id="dissidioPor" label="Reajuste por" name="dissidioPor" list=r"#{'2':'Quantia adicionada ao valor atual(R$)', '1':'Porcentagem sobre o valor atual(%)'}" liClass="liLeft" required="true"/>
			<@ww.textfield label="" name="valorDissidio" id="valorDissidio" cssClass="currency" cssStyle="width:85px; text-align:right;" maxLength="12" value="${reajusteFaixaSalarial.valorProposto}"/>
		<#else>
			<@ww.select label="Planejamento de Realinhamento" name="tabelaReajusteColaborador.id" id ="tabelaReajuste" required="true" list="tabelaReajusteColaboradors" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="-1" cssStyle="width:500px;"/>
			<@ww.select label="Cargo" name="cargo.id" id="cargoId" required="true" list="cargos" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="-1" cssStyle="width:500px;"/>
			<@ww.select label="Faixa Salarial" name="faixaSalarial.id" id="faixaSalarialId" required="true" headerValue="Selecione..." headerKey="" cssStyle="width:500px;"/>
			<@ww.select id="dissidioPor" label="Reajuste por" name="dissidioPor" list=r"#{'1':'Porcentagem sobre o valor atual(%)', '2':'Quantia adicionada ao valor atual(R$)'}" liClass="liLeft" required="true"/>
			<@ww.textfield label="" name="valorDissidio" id="valorDissidio" cssClass="currency" cssStyle="width:85px; text-align:right;" maxLength="12"/>
		</#if>

	</@ww.form>
	
	<div class="buttonGroup">
		<#if edicao>
			<button onclick="return validaFormulario('form', ['valorDissidio'], ['valorDissidio']);" class="btnGravar"></button>
		<#else>
			<button onclick="return validaFormulario('form', ['tabelaReajuste','cargoId','faixaSalarialId','valorDissidio'], ['valorDissidio']);" class="btnGravar"></button>
		</#if>
	</div>
</body>
</html>