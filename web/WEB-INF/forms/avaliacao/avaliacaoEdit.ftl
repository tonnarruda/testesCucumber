<html>
	<head>
		<@ww.head/>
		
		<#assign btnClass="btnAvancar"/>
		
		<#if avaliacao.id?exists>
			<title>Editar Modelo de Avaliação</title>
			<#assign btnClass="btnGravar"/>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Modelo de Avaliação</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('titulo','ativo'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
		
			<@ww.textfield label="Título" name="avaliacao.titulo" id="titulo" cssClass="inputNome" maxLength="100" required="true" />

			<@ww.textarea label="Cabeçalho" name="avaliacao.cabecalho"/>
			
			<@ww.select label="Ativa" name="avaliacao.ativo" id="ativo" list=r"#{true:'Sim',false:'Não'}"/>
		
			<@ww.hidden name="avaliacao.id" />
			<@ww.hidden name="avaliacao.empresa.id" />
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="${btnClass}"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
