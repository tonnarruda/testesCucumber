<html>
	<head>
		<@ww.head/>
		<#if periodoExperiencia.id?exists>
			<title>Editar Período de Acompanhamento de Experiência</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Período de Acompanhamento de Experiência</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('dias'))"/>
	</head>
		<@ww.actionerror />
	<body>
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="periodoExperiencia.id" />
			
			<@ww.textfield label="Qtd. de Dias" id="dias" name="periodoExperiencia.dias" maxLength="4" required="true" onkeypress="return(somenteNumeros(event,''));" cssStyle="width:40px; text-align:right;"/>
			<@ww.textfield label="Descrição" id="descricao" name="periodoExperiencia.descricao" maxLength="40" cssStyle="width:290px;;"/>
			<@ww.select label="Ativo" id= "ativo" name="periodoExperiencia.ativo" list=r"#{true:'Sim',false:'Não'}"/>
			
			<@ww.hidden name="periodoExperiencia.empresa.id" />
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
