<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
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
	
		<#assign validarCampos="return validaFormulario('form', new Array('descricao'), null)"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true"  method="POST">
			<@ww.hidden name="nivelCompetencia.id" id = "nivelCompetenciaId"/>
			<@ww.hidden name="nivelCompetencia.empresa.id" />
			<@ww.token/>
			<@ww.textfield label="Descrição" name="nivelCompetencia.descricao" id="descricao" maxLength="60" cssStyle="width:300px;" required="true"/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
