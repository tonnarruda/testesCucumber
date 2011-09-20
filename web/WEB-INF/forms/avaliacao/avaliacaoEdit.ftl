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
	
	<#assign validarCampos="return validaFormulario('form', new Array('titulo','ativo', 'periodoExperiencia'))"/>
	
		
	<script type='text/javascript'>

		$(function() {
		
			<#if avaliacao.tipoModeloAvaliacao != 'S'>
				$('#tipoModeloAvaliacao').change(function() {
					if ($(this).val() == 'A')
					{
						$('#wwgrp_periodoExperiencia').show();
						$('#wwgrp_periodoExperiencia select').removeAttr('disabled');
					}
					else
					{
						$('#wwgrp_periodoExperiencia').hide();
						$('#wwgrp_periodoExperiencia select').attr("disabled", true);
					}
				});
				
				<#if avaliacao.tipoModeloAvaliacao != 'A'>
					$('#wwgrp_periodoExperiencia').hide();
					$('#wwgrp_periodoExperiencia select').attr("disabled", true);
				</#if>
			</#if>

		});
		
		function enviaForm()
		{
			if($('#tipoModeloAvaliacao').val() == 'A')
				return validaFormulario('form', new Array('titulo','ativo', 'periodoExperiencia'));
			else 		
			{
				$('#periodoExperiencia').val('');
				return validaFormulario('form', new Array('titulo','ativo'));
			}
		}
	</script>	
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="enviaForm();" method="POST">
		
			<@ww.textfield label="Título" name="avaliacao.titulo" id="titulo" cssClass="inputNome" maxLength="100" required="true" />

			<@ww.textarea label="Observação" name="avaliacao.cabecalho"/>

			<@ww.checkbox label="Exibir Resultado ao Colaborador quando for AutoAvaliação" id="exibeResultadoAutoavaliacao" name="avaliacao.exibeResultadoAutoavaliacao" labelPosition="left"/>
			
			<@ww.select label="Ativa" name="avaliacao.ativo" id="ativo" list=r"#{true:'Sim',false:'Não'}"/>

			<#if avaliacao.tipoModeloAvaliacao != 'S'>
				<@ww.select label="Tipo de Avaliação" name="avaliacao.tipoModeloAvaliacao" id="tipoModeloAvaliacao" list=r"#{'D':'Avaliação de Desempenho','A':'Acompanhamento do Período de Experiência'}"/>
				<@ww.select label="Períodos de Acompanhamento de Experiência" name="avaliacao.periodoExperiencia.id" id="periodoExperiencia" listKey="id" listValue="diasComDescricao" list="periodoExperiencias"  headerKey="" headerValue="Selecione..." required="true" />
			</#if>			
		
			<@ww.hidden name="telaInicial" />
			<@ww.hidden name="avaliacao.id" />
			<@ww.hidden name="avaliacao.tipoModeloAvaliacao" />
			<@ww.hidden name="avaliacao.empresa.id" />
			<@ww.hidden name="modeloAvaliacao" />
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="enviaForm();" class="${btnClass}"></button>
			
			<#if telaInicial?exists>
				<button onclick="window.location='list.action?modeloAvaliacao=${modeloAvaliacao}'" class="btnVoltar"></button>
			<#else>
				<button onclick="window.location='../../index.action'" class="btnVoltar"></button>
			</#if>
		</div>
	</body>
</html>
