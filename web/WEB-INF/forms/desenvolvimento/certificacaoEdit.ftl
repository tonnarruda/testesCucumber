<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
	<head>
	<@ww.head/>
		<#if certificacao.id?exists>
			<title>Editar Certificação</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Certificação</title>
			<#assign formAction="insert.action"/>
		</#if>
		<#assign validarCampos="return validaFormulario('form', new Array('nome'), null)"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.textfield label="Nome" id="nome" name="certificacao.nome"  cssStyle="width:500px" maxLength="100" required="true"/>
	        <@frt.checkListBox label="Cursos" name="cursosCheck" list="cursosCheckList" />
			
			<@ww.hidden name="certificacao.id" />
			<@ww.token/>
		</@ww.form>
		
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnCancelar"></button>
		</div>
	</body>
</html>