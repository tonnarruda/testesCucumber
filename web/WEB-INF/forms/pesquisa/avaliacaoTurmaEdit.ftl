<html>
<head>
<@ww.head/>

	<#assign class="btnAvancar"/>
<#if avaliacaoTurma.id?exists>
	<title>Editar Modelo de Avaliação de Curso</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>

	<#if 0 < quantidadeDeResposta>
		<#assign class="btnGravar"/>
		<#assign formAction="gravar.action"/>
		<#assign accessKey="G"/>
	</#if>

	<#if avaliacaoTurma.questionario.liberado>
		<#assign class="btnGravar"/>
		<#assign formAction="gravar.action"/>
		<#assign accessKey="G"/>
	</#if>

<#else>
	<title>Inserir Modelo de Avaliação de Curso</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="A"/>
</#if>

<#assign validarCampos="return validaFormulario('form', new Array('titulo','liberado'), null)"/>

</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" id="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield label="Título" name="avaliacaoTurma.questionario.titulo" id="titulo" cssClass="inputNome" maxLength="100" required="true" />
		<@ww.textarea label="Observação" name="avaliacaoTurma.questionario.cabecalho" cssStyle="width:445px;height:60px"/>	
		<@ww.select label="Ativa" name="avaliacaoTurma.ativa" id="liberado" list=r"#{true:'Sim',false:'Não'}"/>

		<@ww.hidden name="avaliacaoTurma.id" />
	    <@ww.hidden name="avaliacaoTurma.questionario.id" />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="${class}" accesskey="${accessKey}">
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V">
		</button>
	</div>

</body>
</html>