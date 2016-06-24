<html>
	<head>
		<@ww.head/>
		<#if areaVivenciaPcmat.id?exists>
			<title>Editar Área de Vivência</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Área de Vivência</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('areaVivencia','descricao'))"/>
	</head>
	<body>
		<@ww.actionerror /> 
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="areaVivenciaPcmat.id" />
			<@ww.hidden name="areaVivenciaPcmat.pcmat.id" />
			<@ww.hidden name="ultimoPcmatId" />
			
			<@ww.select label="Área de Vivência" name="areaVivenciaPcmat.areaVivencia.id" id="areaVivencia" list="areasVivencia" listKey="id" listValue="nome" headerValue="" headerKey="-1" required="true"/>
			<@ww.textarea label="Descrição" name="areaVivenciaPcmat.descricao" id="descricao"/>
			
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<#if ultimoPcmatId == areaVivenciaPcmat.pcmat.id>
				<button onclick="${validarCampos};" class="btnGravar"></button>
			</#if>
			<button onclick="javascript: executeLink('list.action?pcmat.id=${areaVivenciaPcmat.pcmat.id}&ultimoPcmatId=${ultimoPcmatId}');" class="btnVoltar"></button>
		</div>
	</body>
</html>
