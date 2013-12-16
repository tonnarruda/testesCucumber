<html>
	<head>
		<@ww.head/>
		<#if epcPcmat.id?exists>
			<title>Editar EPC</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir EPC</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('epc','descricao'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="epcPcmat.id" />
			<@ww.hidden name="epcPcmat.pcmat.id" />
			<@ww.token/>
			
			<@ww.select label="EPC" name="epcPcmat.epc.id" id="epc" list="Epcs" listKey="id" listValue="descricao" headerValue="" headerKey="-1" required="true"/>
			<@ww.textarea label="Descricao" name="epcPcmat.descricao" id="descricao" required="true"/>
			
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action?pcmat.id=${epcPcmat.pcmat.id}'" class="btnVoltar"></button>
		</div>
	</body>
</html>
