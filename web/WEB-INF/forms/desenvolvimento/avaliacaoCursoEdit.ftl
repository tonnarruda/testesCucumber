<html>
<head>
<@ww.head/>
<#if avaliacaoCurso.id?exists>
	<title>Editar Avaliação do Aluno</title>
	<#assign formAction="update.action"/>
<#else>
	<title>Inserir Avaliação do Aluno</title>
	<#assign formAction="insert.action"/>
</#if>
<#assign validarCampos="return validaFormulario('form', new Array('titulo','tipo'), null)"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield label="Título" required="true" name="avaliacaoCurso.titulo" id="titulo" maxlength="100" size="80"/>
		<@ww.select label="Tipo" required="true" name="avaliacaoCurso.tipo" list="tipos" id="tipo"  headerKey="" headerValue="Selecione" />
		<@ww.textfield label="Mínimo para Aprovação" name="avaliacaoCurso.minimoAprovacao" onkeypress="return(somenteNumeros(event,'{,}'));" cssStyle="text-align:right; width:50px;" maxlength="6"/>

		<@ww.hidden name="avaliacaoCurso.id" />
		<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar">
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar">
		</button>
	</div>
</body>
</html>