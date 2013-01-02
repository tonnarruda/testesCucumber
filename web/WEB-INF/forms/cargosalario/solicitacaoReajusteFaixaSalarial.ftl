<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>
	
	<title>Solicitação de Realinhamento de Cargos & Salários para Faixa Salarial</title>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/FaixaSalarialDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript'>
		$(function() {
			$('#cargoId').change(function() {
				var cargoId = $(this).val();
				FaixaSalarialDWR.getByCargoDesabilitandoPorIndice(createListFaixas, cargoId);
			});
		});
		
		function createListFaixas(data)
		{
			addOptionsByCollection('faixaSalarialId', data);
		}
	</script>
</head>

<body>
	<@ww.form name="form" id="form" action="prepareInsert.action" method="POST">
		<@ww.select label="Planejamento de Realinhamento" name="tabelaReajusteColaborador.id" id ="tabelaReajuste" required="true" list="tabelaReajusteColaboradors" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="-1" cssStyle="width:500px;"/>
		<@ww.select label="Cargo" name="cargo.id" id="cargoId" required="true" list="cargos" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="-1" cssStyle="width:500px;"/>
		<@ww.select label="Faixa Salarial" name="faixaSalarial.id" id="faixaSalarialId" required="true" headerValue="Selecione..." headerKey="" cssStyle="width:500px;"/>
		<@ww.textfield label="Valor" name="valorDissidio" id="valorDissidio" required="true" cssClass="currency" cssStyle="width:85px; text-align:right;" maxLength="12"/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="return validaFormulario('form', ['tabelaReajuste','cargoId','faixaSalarialId','valorDissidio'], ['valorDissidio']);" class="btnGravar"></button>
	</div>
</body>
</html>