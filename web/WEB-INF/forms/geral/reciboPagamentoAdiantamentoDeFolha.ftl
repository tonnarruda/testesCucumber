<html>
	<head>
		<@ww.head/>
		<title>Recibo de Pagamento - Adiantamento de Folha</title>
		
		<#include "../ftl/mascarasImports.ftl" />
		<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
		<#assign validarCampos="return validaFormulario('form', new Array('mesAno'), new Array('mesAno'), false, '${urlImgs}')"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		
		<#if colaborador.id?exists && empresaSistema.acIntegra && colaborador.codigoAC?exists>
			<@ww.form name="form" action="reciboPagamentoAdiantamentoDeFolha.action" onsubmit="${validarCampos}" method="POST">
				<@ww.textfield label="Mês/Ano" id="mesAno" name="mesAno" maxLength="7" size="7" cssClass="mascaraMesAnoData"/>
				<div class="buttonGroup">
					<button onclick="${validarCampos};" class="btnDownload"></button>
				</div>
			</@ww.form>
		</#if>
	</body>
</html>
