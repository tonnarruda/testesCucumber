<html>
	<head>
		<@ww.head/>
		<title>Recibo de 13º Salário</title>
		
		<#include "../ftl/mascarasImports.ftl" />
	
		<#assign validarCampos="return validaFormulario('form', new Array('mesAno'), new Array('mesAno') )"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		
		<#if colaborador.id?exists && empresaSistema.acIntegra>
			<@ww.form name="form" action="reciboDeDecimoTerceiro.action" onsubmit="${validarCampos}" method="POST">
				<@ww.select label="Data" name="dataCalculo" list="dataCalculos"/>
				<div class="buttonGroup">
					<button onclick="${validarCampos};" class="btnDownload"></button>
				</div>
			</@ww.form>
		</#if>
	</body>
</html>
