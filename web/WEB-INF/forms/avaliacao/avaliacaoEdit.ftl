<html>
	<head>
		<@ww.head/>
		
		<#assign btnClass="btnAvancar"/>
		
		<#if avaliacao.id?exists>
			<title>Editar Modelo de Avaliação</title>
			<#assign btnClass="btnGravar"/>
			<#assign formAction="update.action"/>
			<#assign edicao=true/>
		<#else>
			<title>Inserir Modelo de Avaliação</title>
			<#assign formAction="insert.action"/>
			<#assign edicao=false/>
		</#if>
	
	<script type='text/javascript'>

		$(function() {
		
		
			<#if edicao>
				$('#tipoModeloAvaliacao').attr('disabled', 'disabled').css('background', '#F6F6F6');
			</#if>
			
			<#if avaliacao.tipoModeloAvaliacao != 'S'>
				$('#tipoModeloAvaliacao').change(function() {
					if ($(this).val() == 'A')
					{
						$('#wwgrp_periodoExperiencia').show();
						$('#wwgrp_periodoExperiencia select').removeAttr('disabled');
						$('#wwgrp_avaliarCompetenciasCargo').hide();
					}
					else
					{
						$('#wwgrp_periodoExperiencia').hide();
						$('#wwgrp_periodoExperiencia select').attr("disabled", true);
						$('#wwgrp_avaliarCompetenciasCargo').show();
					}
				});
				
				$('#tipoModeloAvaliacao').change();
			</#if>

		});
		
		function enviaForm()
		{
			if($('#tipoModeloAvaliacao').val() == 'A')
				return validaFormulario('form', new Array('titulo','ativo', 'periodoExperiencia'));
			else 		
			{
				$('#periodoExperiencia').val('');
				return validaFormulario('form', new Array('titulo','ativo'), new Array('percentualAprovacao'));
			}
		}
	</script>	
	</head>
	<body>
		<@ww.actionmessage />
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="enviaForm();" method="POST">
		
			<@ww.textfield label="Título" name="avaliacao.titulo" id="titulo" cssClass="inputNome" maxLength="100" required="true" />

			<@ww.textarea label="Observação" name="avaliacao.cabecalho"/>

			<#if avaliacao.tipoModeloAvaliacao == 'S'>
				<@ww.textfield label="Percentual mínimo para aprovação" name="avaliacao.percentualAprovacao" id="percentualAprovacao" cssStyle="width:40px; text-align:right;" maxLength="5" onkeypress="return somenteNumeros(event,',');"/>
			</#if>
			
			<@ww.select label="Ativa" name="avaliacao.ativo" id="ativo" list=r"#{true:'Sim',false:'Não'}"/>

			<#if avaliacao.tipoModeloAvaliacao != 'S' && avaliacao.tipoModeloAvaliacao != 'L'>
				<@ww.select label="Tipo de Avaliação" name="avaliacao.tipoModeloAvaliacao" id="tipoModeloAvaliacao" list=r"#{'D':'Avaliação de Desempenho','A':'Acompanhamento do Período de Experiência'}"/>
				<@ww.select label="Períodos de Acompanhamento de Experiência" name="avaliacao.periodoExperiencia.id" id="periodoExperiencia" listKey="id" listValue="diasDescricaoComInativo" list="periodoExperiencias"  headerKey="" headerValue="Selecione..." required="true" />
				<@ww.checkbox label="Avaliar também as competências exigidas pelo cargo" id="avaliarCompetenciasCargo" name="avaliacao.avaliarCompetenciasCargo" labelPosition="left"/>
				<@ww.checkbox label="Exibir respostas das perguntas de forma compacta" id="respostasCompactas" name="avaliacao.respostasCompactas" labelPosition="left"/>
			</#if>			
		
			<@ww.hidden name="telaInicial" />
			<@ww.hidden name="avaliacao.id" />
			<@ww.hidden name="avaliacao.tipoModeloAvaliacao" />
			<@ww.hidden name="avaliacao.empresa.id" />
			<@ww.hidden name="modeloAvaliacao" />
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="enviaForm();" class="${btnClass}"></button>
			
			<#if telaInicial?exists>
				<button onclick="javascript: executeLink('list.action?modeloAvaliacao=${modeloAvaliacao}');" class="btnVoltar"></button>
			<#else>
				<button onclick="javascript: executeLink('../../index.action');" class="btnVoltar"></button>
			</#if>
		</div>
	</body>
</html>
