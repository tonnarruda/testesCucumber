<html>
	<head>
		<@ww.head/>
		<title>Recibo de Pagamento - 13º Salário</title>
		
		<#include "../ftl/mascarasImports.ftl" />
		<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
		<#assign validarCampos="return validaFormulario('form', new Array('dataCalculo'), new Array('dataCalculo'), false, '${urlImgs}')"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		
		<#if colaborador.id?exists && empresaSistema.acIntegra && colaborador.codigoAC?exists>
			<@ww.form name="form" action="reciboDeDecimoTerceiro.action" onsubmit="${validarCampos}" method="POST">
				<@ww.select label="Data" name="dataCalculo" id="dataCalculo" list="dataCalculos"/>
				<div class="buttonGroup">
					<button onclick="${validarCampos};" class="btnDownload"></button>
				</div>
			</@ww.form>
		</#if>
	</body>
</html>
