<html>
	<head>
		<@ww.head/>
		<title>Complemento de Folha com Encargos</title>
		
		<#include "../ftl/mascarasImports.ftl" />
	
		<#assign validarCampos="return validaFormulario('form', new Array('mesAno'), new Array('mesAno') )"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		
		<#if colaborador.id?exists && empresaSistema.acIntegra>
			<@ww.form name="form" action="reciboPagamentoComplementar.action" onsubmit="${validarCampos}" method="POST">
				<@ww.textfield label="Mês/Ano" id="mesAno" name="mesAno" maxLength="7" size="7" cssClass="mascaraMesAnoData"/>
				<div class="buttonGroup">
					<button onclick="${validarCampos};" class="btnDownload"></button>
				</div>
			</@ww.form>
		</#if>
	</body>
</html>
