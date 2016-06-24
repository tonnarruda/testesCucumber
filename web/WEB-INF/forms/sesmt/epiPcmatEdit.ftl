<html>
	<head>
		<@ww.head/>
		<#if epiPcmat.id?exists>
			<title>Editar EPI</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir EPI</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('epi', 'atividades'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="epiPcmat.id" />
			<@ww.hidden name="epiPcmat.pcmat.id" />
			<@ww.hidden name="ultimoPcmatId" />
			<@ww.token/>
			
			<@ww.select label="EPI" name="epiPcmat.epi.id" id="epi" list="Epis" listKey="id" listValue="nome" headerValue="" headerKey="-1" required="true"/>
			<@ww.textarea label="Atividades" name="epiPcmat.atividades" id="atividades" required="true"/>
			
		</@ww.form>
	
		<div class="buttonGroup">
			<#if ultimoPcmatId == epiPcmat.pcmat.id>
				<button onclick="${validarCampos};" class="btnGravar"></button>
			</#if>
			<button onclick="javascript: executeLink('list.action?pcmat.id=${epiPcmat.pcmat.id}&ultimoPcmatId=${ultimoPcmatId}');" class="btnVoltar"></button>
		</div>
	</body>
</html>
