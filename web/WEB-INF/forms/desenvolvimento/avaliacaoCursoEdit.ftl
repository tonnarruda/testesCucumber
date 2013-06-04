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

<script type="text/javascript">
	$(function() {
		$('#tipo').change(function() {
			$('#wwgrp_minimoAprovacao').toggle( $('#tipo').val() != 'a' );
			$('#wwgrp_avaliacao').toggle( $('#tipo').val() == 'a' );
		});
		
		$('#tipo').change();
	});
	
	function gravar()
	{
		if ( $('#tipo').val() == 'a' ) {
			$('#minimoAprovacao').val('');
			return validaFormulario('form', new Array('titulo','tipo','avaliacao'), null);
		} else {
			$('#avaliacao').val('');
			return validaFormulario('form', new Array('titulo','tipo'), null);
		}
	}
</script>

</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" validate="true" method="POST">
		<@ww.textfield label="Título" required="true" name="avaliacaoCurso.titulo" id="titulo" maxlength="100" size="80"/>
		<@ww.select label="Tipo" required="true" name="avaliacaoCurso.tipo" list="tipos" id="tipo"  headerKey="" headerValue="Selecione" />
		<@ww.textfield label="Mínimo para Aprovação" name="avaliacaoCurso.minimoAprovacao" id="minimoAprovacao" onkeypress="return(somenteNumeros(event,'{,}'));" cssStyle="text-align:right; width:50px;" maxlength="6"/>
		<@ww.select label="Modelo da Avaliação" required="true" name="avaliacaoCurso.avaliacao.id" id="avaliacao" list="avaliacoes" listKey="id" listValue="titulo" headerKey="" headerValue="Selecione..."/>

		<@ww.hidden name="avaliacaoCurso.id" />
		<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button type="button" onclick="gravar();" class="btnGravar"></button>
		<button onclick="window.location='list.action'" class="btnCancelar"></button>
	</div>
</body>
</html>