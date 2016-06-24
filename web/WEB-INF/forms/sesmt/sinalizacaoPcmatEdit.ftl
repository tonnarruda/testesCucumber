<html>
	<head>
		<@ww.head/>
		<#if sinalizacaoPcmat.id?exists>
			<title>Editar Sinalização</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Sinalização</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('descricao'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="sinalizacaoPcmat.id" />
			<@ww.hidden name="sinalizacaoPcmat.pcmat.id" />
			<@ww.hidden name="ultimoPcmatId" />
			<@ww.token/>
			
			<@ww.textfield label="Descrição" name="sinalizacaoPcmat.descricao" id="descricao" required="true" maxLenght="200" cssStyle="width:500px"/>
		</@ww.form>
	
		<div class="buttonGroup">
			<#if ultimoPcmatId == sinalizacaoPcmat.pcmat.id>
				<button onclick="${validarCampos};" class="btnGravar"></button>
			</#if>
			
			<button onclick="javascript: executeLink('list.action?pcmat.id=${sinalizacaoPcmat.pcmat.id}&ultimoPcmatId=${ultimoPcmatId}');" class="btnVoltar"></button>
		</div>
	</body>
</html>
