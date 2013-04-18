<html>
	<head>
		<@ww.head/>
		<title>Contra Cheque</title>
		
		<#include "../ftl/mascarasImports.ftl" />
	
		<#assign validarCampos="return validaFormulario('form', new Array('codEmpresa','codEmpregado','mesAno'), new Array('mesAno') )"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="contraCheque.action" onsubmit="${validarCampos}" method="POST" target="_blank">
		
			<@ww.textfield label="Cod Empresa" id="codEmpresa" name="codEmpresa" maxLength="4" size="4"/>
			<@ww.textfield label="Cod Empregado" id="codEmpregado" name="codEmpregado" maxLength="6" size="6"/>
			<@ww.textfield label="Mês/Ano" id="mesAno" name="mesAno" maxLength="7" size="7" cssClass="mascaraMesAnoData"/>
			
			<div class="buttonGroup">
				<button onclick="${validarCampos};" class="btnDownload"></button>
			</div>
		</@ww.form>
	</body>
</html>
