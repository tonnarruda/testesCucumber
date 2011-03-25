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

		jQuery(function($) {
		
			<#if avaliacao.tipoModeloAvaliacao != 'S'>
				$('#tipoModeloAvaliacao').change(function() {
					if($(this).val() == 'A')
						$('#wwgrp_periodoExperiencia').show();
					else
						$('#wwgrp_periodoExperiencia').hide();
				});
				
				<#if avaliacao.tipoModeloAvaliacao != 'A'>
					$('#wwgrp_periodoExperiencia').hide();
				</#if>
			</#if>

		});
		
		function enviaForm()
		{
			if(jQuery('#tipoModeloAvaliacao').val() == 'A')
				return validaFormulario('form', new Array('titulo','ativo', 'periodoExperiencia'));
			else 		
			{
				jQuery('#periodoExperiencia').val('');
				return validaFormulario('form', new Array('titulo','ativo'));
			}
		}
	</script>	
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="enviaForm();" method="POST">
		
			<@ww.textfield label="Título" name="avaliacao.titulo" id="titulo" cssClass="inputNome" maxLength="100" required="true" />

			<@ww.textarea label="Cabeçalho" name="avaliacao.cabecalho"/>
			
			<@ww.select label="Ativa" name="avaliacao.ativo" id="ativo" list=r"#{true:'Sim',false:'Não'}"/>
			
			<#if avaliacao.tipoModeloAvaliacao != 'S'>
				<@ww.select label="Tipo de Avaliação" name="avaliacao.tipoModeloAvaliacao" 
				id="tipoModeloAvaliacao" list=r"#{'D':'Avaliação de Desempenho','A':'Acompanhamento do Período de Experiência'}"/>
			</#if>
			
			<@ww.select label="Períodos de Acompanhamento de Experiência" name="avaliacao.periodoExperiencia.id" id="periodoExperiencia" listKey="id" listValue="dias" list="periodoExperiencias"  headerKey="" headerValue="Selecione..." required="true" />
		
			<@ww.hidden name="avaliacao.id" />
			<@ww.hidden name="avaliacao.tipoModeloAvaliacao" />
			<@ww.hidden name="avaliacao.empresa.id" />
			<@ww.hidden name="modeloAvaliacao" />
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="enviaForm();" class="${btnClass}"></button>
			<button onclick="window.location='list.action?modeloAvaliacao=${modeloAvaliacao}'" class="btnVoltar"></button>
		</div>
	</body>
</html>
