<html>
	<head>
		<@ww.head/>
		<#if nivelCompetencia.id?exists>
			<title>Editar Nível de Competência</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Nível de Competência</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('ordem','descricao'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="nivelCompetencia.id" />
			<@ww.hidden name="nivelCompetencia.empresa.id" />
			<@ww.token/>
			
			<@ww.textfield label="Ordem" name="nivelCompetencia.ordem" id="ordem" size="4"  maxLength="4" required="true" onkeypress="return(somenteNumeros(event,''));" cssStyle="width:40px; text-align:right;"/>
			<@ww.textfield label="Descrição" name="nivelCompetencia.descricao" id="descricao" maxLength="15" cssStyle="width:110px;" required="true"/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
