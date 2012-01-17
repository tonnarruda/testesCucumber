<html>
<head>
<@ww.head/>
	<title>Entrevista de Desligamento</title>

</head>
<body>
<#assign validarCampos="return validaFormulario('form', new Array('entrevista'), null)"/>
<@ww.actionerror />

	<#if colaborador?exists && colaborador.nome?exists>
		<b>Colaborador: ${colaborador.nome}</b> <br/>
		<b>Estabelecimento: ${colaborador.estabelecimento.nome}</b> <br/>
		<b>Cargo: ${colaborador.faixaSalarial.descricao}</b> <br/>
		<b>Telefone : ${colaborador.contato.foneContatoFormatado}</b> <br/><br/>
	</#if>

	<@ww.form name="form" action="../colaboradorResposta/prepareResponderEntrevista.action" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.select label="Modelo de Entrevista" name="questionario.id" id="entrevista" list="entrevistas" listKey="questionario.id" listValue="questionario.titulo" headerKey="" headerValue="Selecione..."/>
		<@ww.hidden name="colaborador.id" />
		<@ww.hidden name="voltarPara" value="../../geral/colaborador/list.action"/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnAvancar">
		</button>
		<button onclick="window.location='../../geral/colaborador/list.action'" class="btnCancelar">
		</button>
	</div></body>
</html>