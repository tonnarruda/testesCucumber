<html>
	<head>
		<@ww.head/>
		<title>Declaração de Rendimentos</title>
		
		<#include "../ftl/mascarasImports.ftl" />
	
		<#assign validarCampos="return validaFormulario('form', new Array('ano'), new Array('ano') )"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		
		<#if colaborador.id?exists && empresaSistema.acIntegra>
			<@ww.form name="form" action="declaracaoRendimentos.action" onsubmit="${validarCampos}" method="POST">
				<@ww.textfield label="Ano" id="ano" name="anoDosRendimentos" maxLength="4" size="4" cssClass="somenteNumerosFour"/>
				<div class="buttonGroup">
					<button onclick="${validarCampos};" class="btnDownload"></button>
				</div>
			</@ww.form>
		</#if>
	</body>
</html>
